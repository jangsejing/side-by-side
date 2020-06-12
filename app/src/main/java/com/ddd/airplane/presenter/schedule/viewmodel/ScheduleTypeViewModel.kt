package com.ddd.airplane.presenter.schedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd.airplane.data.response.ScheduleData

class ScheduleTypeViewModel {

    private val _typeList = MutableLiveData<List<ScheduleData.Type>>()
    val typeList: LiveData<List<ScheduleData.Type>> = _typeList

    private val _tabList = MutableLiveData<List<String>>()
    val tabList: LiveData<List<String>> = _tabList

    fun setData(list: List<ScheduleData.Type>) = apply {
        _typeList.value = list

        addTab(list)
    }

    private fun addTab(list: List<ScheduleData.Type>) {
        // tab 추가list
        val tabList = ArrayList<String>()
        list.forEach {
            tabList.add(it.name)
        }
        _tabList.value = tabList
    }
}
