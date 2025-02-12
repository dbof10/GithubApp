package com.github.list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.common.theme.AppDimensions.spacingMedium
import com.github.common.theme.AppTheme.typography
import com.github.common.theme.GithubTheme
import com.github.list.presentation.model.UserUiModel


@Composable
fun UserCardItem(model: UserUiModel,
                 onClicked: (model: UserUiModel) -> Unit,
                 modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacingMedium)
            .clickable {
                onClicked(model)
            }
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(spacingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = model.avatarUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(spacingMedium))

            Column {
                Text(
                    text = model.login,
                    style = typography.t1
                )

                Text(text = model.id.toString())
                Text(
                    text = model.htmlUrl
                )
            }
        }
    }
}


@Preview
@Composable
private fun UserCardItemPreview() {
    GithubTheme {
        UserCardItem(
            model = UserUiModel(
                id = 1,
                login = "discere",
                avatarUrl = "https://www.google.com/#q=referrentur",
                url = "http://www.bing.com/search?q=elit",
                htmlUrl = "https://www.google.com/#q=prodesset",
                followersUrl = "https://duckduckgo.com/?q=imperdiet",
                followingUrl = "http://www.bing.com/search?q=hinc",
                gistsUrl = "https://duckduckgo.com/?q=malorum",
                starredUrl = "https://duckduckgo.com/?q=eu",
                reposUrl = "https://www.google.com/#q=sodales",
            ),
            onClicked = {}
        )
    }
}
