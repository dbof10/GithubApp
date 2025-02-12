package com.github.list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.common.theme.AppTheme.dimensions
import com.github.common.theme.GithubTheme
import com.github.common.ui.OnBottomReached
import com.github.list.presentation.model.UserUiModel
import com.github.list.presentation.redux.ListResultsAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navigator: ListNavigator
) {
    val viewModel: ListViewModel = hiltViewModel<ListViewModel>().apply {
        setNavigator(navigator)
    }
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val state by viewModel.stateflow.collectAsState()

    listState.OnBottomReached {
        viewModel.dispatch(ListResultsAction.LoadMore)
    }
    LaunchedEffect(Unit) {
        viewModel.dispatch(ListResultsAction.StartLoadResult)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {},
                title = {
                    Text(
                        text = "Github"
                    )
                },
                modifier = Modifier.background(Color.Transparent)
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = state.error?.message.orEmpty(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            UsersList(state.users, listState, {
                viewModel.dispatch(ListResultsAction.UserDetailClicked(it))
            }, modifier = Modifier.padding(padding))
        }
    }
}

@Composable
private fun UsersList(
    models: List<UserUiModel>,
    lazyListState: LazyListState,
    onItemClicked: (UserUiModel) -> Unit,
    modifier: Modifier,
) {


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        state = lazyListState
    ) {
        items(models.size, key = { index -> models[index].id }) { index ->
            UserCardItem(
                model = models[index],
                onClicked = onItemClicked,
                modifier = Modifier
                    .padding(
                        horizontal = dimensions.spacingMedium,
                        vertical = dimensions.spacingSmall,
                    )
            )
        }
    }

}

@Suppress("MaxLineLength")
@Preview(showSystemUi = true)
@Composable
private fun UserListScreenPreview() {
    GithubTheme {
    }
}
