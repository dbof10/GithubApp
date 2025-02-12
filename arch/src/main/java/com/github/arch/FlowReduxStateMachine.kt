package com.github.arch

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow

@FlowPreview
@ExperimentalCoroutinesApi
abstract class FlowReduxStateMachine<State : Any, Action : Any, Navigation : Any> :
    StateMachine<State, Action> {

    private val inputActions = Channel<Action>()

    private lateinit var outputState: Flow<State>

    val navigationFlow = Channel<Navigation>()

    private val activeFlowCounter = AtomicCounter(0)

    abstract val initialState: State

    protected open val initialAction: Action? = null // in some situations, we don't want to emit initialAction from store that why it is nullable
    protected abstract fun sideEffects(): List<SideEffect<State, Action>>

    protected abstract fun reducer(): Reducer<State, Action>

    fun initStore() {
        outputState = inputActions
            .receiveAsFlow()
            .onStart {
                initialAction?.let { emit(it) }
            }
            .reduxStore(
                initialStateSupplier = { initialState },
                sideEffects = sideEffects(),
                reducer = reducer(),
            )
            .distinctUntilChanged { old, new -> old === new } // distinct until not the same object reference.
            .onStart {
                if (activeFlowCounter.incrementAndGet() > 1) {
                    throw IllegalStateException(
                        "Can not collect state more than once at the same time. Make sure the" +
                            "previous collection is cancelled before starting a new one. " +
                            "Collecting state in parallel would lead to subtle bugs.",
                    )
                }
            }
            .onCompletion {
                activeFlowCounter.decrementAndGet()
            }
    }

    fun dispose() {
        inputActions.close()
        navigationFlow.close()
    }

    override val state: Flow<State>
        get() = outputState

    val navigation: Flow<Navigation> = navigationFlow.receiveAsFlow()

    override suspend fun dispatch(action: Action) {
        if (activeFlowCounter.get() <= 0) {
            throw IllegalStateException(
                "Cannot dispatch action $action because state Flow of this " +
                    "FlowReduxStateMachine is not collected yet. " +
                    "Start collecting the state Flow before dispatching any action.",
            )
        }
        inputActions.send(action)
    }

    protected inline fun <reified Action : Any> createNavigationSideEffect(
        noinline navigationTransformer: suspend (State, Action) -> Navigation?,
    ): SideEffect<State, Action> = { actions, getState ->
        actions.ofType(Action::class)
            .throttleDistinct(ReduxDuration.DURATION_LONG)
            .mapNotNull {
                navigationTransformer(getState(), it)
            }
            .onEach {
                navigationFlow.send(it)
            }
            .flatMapLatest { emptyFlow() }
    }
}
