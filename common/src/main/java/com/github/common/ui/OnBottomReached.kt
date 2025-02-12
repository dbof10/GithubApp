package com.github.common.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * https://manavtamboli.medium.com/infinite-list-paged-list-in-jetpack-compose-b10fc7e74768
 */

@Composable
fun LazyListState.OnBottomReached(
    remainingItems: Int = 5,
    loadMore: () -> Unit,
) {
    require(remainingItems >= 0) { "buffer cannot be negative, but was $remainingItems" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true

            // Check if last visible item is the last item in the list
            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - remainingItems
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}
