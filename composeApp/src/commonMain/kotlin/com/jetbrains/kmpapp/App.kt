package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jetbrains.kmpapp.di.koinConfig
import com.jetbrains.kmpapp.screens.detail.DetailScreen
import com.jetbrains.kmpapp.screens.list.ListScreen
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication


@Serializable
data class DetailDestination(val objectId: Int)

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App() {
    KoinApplication(application = koinConfig()) {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
        ) {
            Surface {
                val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<DetailDestination>()
                ListDetailPaneScaffold(
                    modifier = Modifier.fillMaxSize(),
                    directive = scaffoldNavigator.scaffoldDirective,
                    value = scaffoldNavigator.scaffoldValue,
                    listPane = {
                        AnimatedPane(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            ListScreen(navigateToDetails = { objectId ->
                                scaffoldNavigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    content = DetailDestination(objectId = objectId),
                                )
                            })
                        }
                    },
                    detailPane = {
                        AnimatedPane(modifier = Modifier.fillMaxSize()) {
                            scaffoldNavigator.currentDestination?.content?.let { detailDestination ->
                                DetailScreen(
                                    objectId = detailDestination.objectId,
                                    navigateBack = {
                                        scaffoldNavigator.navigateBack()
                                    }
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}
