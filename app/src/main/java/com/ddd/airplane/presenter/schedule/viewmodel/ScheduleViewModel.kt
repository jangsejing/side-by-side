package com.ddd.airplane.presenter.schedule.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.data.response.ScheduleData

class ScheduleViewModel(application: Application) : BaseViewModel(application) {

    private val _scheduleList = MutableLiveData<ArrayList<ScheduleData>>()
    val scheduleList: LiveData<ArrayList<ScheduleData>> = _scheduleList

    private val _position = MutableLiveData<Int>()
    val position: LiveData<Int> = _position

    init {
        getCategory()
    }

    /**
     * 스케줄 카테고리
     */
    private fun getCategory() {

        // 방송사
        val broadCast = ArrayList<ScheduleData.Type>().apply {
            add(ScheduleData.Type("SBS"))
            add(ScheduleData.Type("KBS"))
            add(ScheduleData.Type("MBC"))
            add(ScheduleData.Type("tvN"))
            add(ScheduleData.Type("JTBC"))
            add(ScheduleData.Type("YTN"))
            add(ScheduleData.Type("Mnet"))
            add(ScheduleData.Type("연합뉴스TV"))
        }

        // 종류
        val typeList = ArrayList<ScheduleData.Type>().apply {
            add(ScheduleData.Type("종편/뉴스"))
            add(ScheduleData.Type("연예/오락"))
            add(ScheduleData.Type("영화/시리즈"))
            add(ScheduleData.Type("스포츠"))
            add(ScheduleData.Type("교육/정보"))
            add(ScheduleData.Type("홈쇼핑"))
            add(ScheduleData.Type("애니/키즈"))
        }

        val list = ArrayList<ScheduleData>()
        list.add(ScheduleData("방송별", broadCast))
        list.add(ScheduleData("주제별", typeList))
        _scheduleList.value = list

    }

    /**
     * 현재 보고있는 페이지 Position
     */
    fun setCurrentPage(position: Int) {
        _position.postValue(position)
    }
}
