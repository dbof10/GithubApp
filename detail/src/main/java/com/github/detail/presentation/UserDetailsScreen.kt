package com.github.detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.github.common.theme.AppDimensions.spacingMedium
import com.github.common.theme.AppTheme.typography
import com.github.detail.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(navigator: DetailNavigator) {

    val viewModel = hiltViewModel<DetailViewModel>()
    val state = viewModel.store.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.load()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = null,
                        modifier = Modifier
                            .padding(spacingMedium)
                            .clickable {
                            navigator.back()
                        })
                },
                title = {
                    Text(
                        text = "User Details"
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
                    text = state.error.message.orEmpty(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            state.content?.let {
                UserDetailsContent(state.content, Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun UserDetailsContent(model: UserDetailUiModel, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Section
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = model.avatarUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(model.name, style = typography.t1)
                    model.location?.let {
                        Text(it, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Follower & Following Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_follower), // Replace with actual icon
                    contentDescription = "Followers",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
                Text(model.follower, style = typography.b1)
                Text("Follower", style = typography.b1)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_following), // Replace with actual icon
                    contentDescription = "Following",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
                Text(model.following, style = typography.b1)
                Text("Following", style = typography.b1)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        model.blog?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Blog", style = typography.t2)
                Text(
                    it,
                    color = Color.Blue,
                )
            }
        }

    }
}
