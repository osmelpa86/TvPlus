package cu.osmel.tvplus.ui.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Composable
fun HomeView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel
) {
    viewModel.setCurrentScreen(AppView.HomeView)
    Scaffold {
        HomeViewContent()
    }
}

@Composable
fun HomeViewContent() {
    Column {
        Text(text = "Home view")
    }
}