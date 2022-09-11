package cu.osmel.tvplus.ui.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cu.osmel.tvplus.ui.componets.topbar.ActionItem
import cu.osmel.tvplus.ui.navegation.AppView

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 6/4/22
 */
class MainViewModel : ViewModel() {
    private val _currentScreen = MutableLiveData<AppView>(AppView.HomeView)
    val currentScreen: LiveData<AppView> = _currentScreen

    fun setCurrentScreen(screen: AppView) {
        _currentScreen.value = screen
    }

    private val _clickCount = MutableLiveData(0)
    val clickCount: LiveData<Int> = _clickCount

    fun updateClick(value: Int) {
        _clickCount.value = value
    }

    private val _appBarActions = MutableLiveData<List<ActionItem>>()
    val appBarActions: LiveData<List<ActionItem>> = _appBarActions

    fun setCurrentAppBarActions(actions: List<ActionItem>) {
        _appBarActions.value = actions
    }
}