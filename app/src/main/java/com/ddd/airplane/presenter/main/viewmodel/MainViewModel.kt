package com.ddd.airplane.presenter.main.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.common.manager.ChatRoomManager
import com.ddd.airplane.repository.database.RecentRepository
import com.ddd.airplane.repository.database.recent.RecentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : BaseViewModel(application) {

    // 닉네임
    private val _recent = MutableLiveData<RecentEntity>()
    val recent: LiveData<RecentEntity> = _recent

    // 최근 본 방송 roomId
    private var recentEntity: RecentEntity? = null

    fun onResume() {
        viewModelScope.launch {
            getRecent()
        }
    }

    fun onPause() {

    }

    /**
     * 최근 방송 검색
     */
    private suspend fun getRecent() {

        viewModelScope.launch {

            RecentRepository.selectTopLimit()?.let { recent ->

                // 닫았는지 여부 판단
                if (recent.isFloatingClose) {
                    return@launch
                }

                recentEntity = recent
                withContext(Dispatchers.Main) {
                    _recent.value = recent
                }
            }
        }
    }

    /**
     * 최근 본 방송 닫기
     */
    suspend fun setFloatingClose() {
        recentEntity?.let { recent ->
            viewModelScope.launch {
                recent.isFloatingClose = true
                RecentRepository.insertRecent(
                    recent
                )
                recentEntity = null
            }
        }
    }

    /**
     * 최근 본 방송 접속
     */
    fun joinChatRoom() {
        recentEntity?.let {
            ChatRoomManager.joinChatRoom(context, it.roomId)
        }
    }

}
