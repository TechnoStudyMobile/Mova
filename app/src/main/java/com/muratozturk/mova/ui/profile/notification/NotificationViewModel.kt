package com.muratozturk.mova.ui.profile.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.domain.model.NotificationUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val _notifComponent = MutableStateFlow<List<NotificationUI>>(listOf())
    val notifComponent : StateFlow<List<NotificationUI>> = _notifComponent.asStateFlow()

    fun getNotifUi() {
        viewModelScope.launch {
            _notifComponent.emit(getListNotification())
        }
    }

    private fun getListNotification(): List<NotificationUI> {
        return mutableListOf(
            NotificationUI(text = "General Notification"),
            NotificationUI(text = "New Arrival"),
            NotificationUI(text = "New Service Available"),
            NotificationUI(text = "New Realeses Movie"),
            NotificationUI(text = "App Updates"),
            NotificationUI(text = "Subscription")
        )
    }
}