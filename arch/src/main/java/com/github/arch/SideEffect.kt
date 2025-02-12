package com.github.arch

import kotlinx.coroutines.flow.Flow

/**
 * It is a function which takes a stream of actions and returns a stream of actions. Actions in, actions out
 * (concept borrowed from redux-observable.js.or - so called epics).
 *
 * - actions: Input action. Every SideEffect should be responsible to handle a single Action
 * (i.e using filter or ofType operator)
 * - state: [GetState] to get the latest state of the state machine
 */
typealias SideEffect<S, A> = (actions: Flow<A>, getState: GetState<S>) -> Flow<A>

/**
 * The GetState is basically just a deferred way to get a state of a [reduxStore] at any given point in time.
 * So you have to call this method to get the state.
 */
typealias GetState<S> = () -> S

fun <S> getState(state: S): GetState<S> {
    return object : GetState<S> {
        override fun invoke(): S = state
    }
}
