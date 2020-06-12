package com.ddd.airplane.presenter.mypage.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.common.interfaces.OnResponseListener
import com.ddd.airplane.common.manager.MemberManager
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.data.response.SubscribeData
import com.ddd.airplane.data.response.chat.ProgramData
import com.ddd.airplane.repository.network.SubscribeRepository
import com.ddd.airplane.repository.network.retrofit.RetrofitManager
import com.ddd.airplane.repository.network.retrofit.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MyPageViewModel(application: Application) : BaseViewModel(application) {

    // 닉네임
    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    // 구독 리스트
    private val _subscribeList = MutableLiveData<List<ProgramData>>()
    val subscribeList: LiveData<List<ProgramData>> = _subscribeList

    // 리스트 초기화 여부
    private val _isClear = MutableLiveData<Boolean>()
    val isClear: LiveData<Boolean> = _isClear

    private var pageNum = 1 // 현재 페이지
    private var isLast = false // 마지막 페이지

    init {
        setProfile()
    }

    /**
     * 프로필 정보 세팅
     */
    private fun setProfile() {
        viewModelScope.launch(uiDispatchers) {
            MemberManager.getAccount {
                it?.run {
                    _nickName.postValue(nickName)
                }
            }
        }
    }

    /**
     * 구독방송 리스트 조회
     */
    fun getSubscribe(isRefresh: Boolean = false) {

        _isClear.value = isRefresh

        // 페이지 정보 초기화
        if (isRefresh) {
            isLast = false
            pageNum = 1
        } else {
            pageNum += 1
        }

        // 마지막 페이지 일경우 데이터 조회하지 않음
        if (isLast) {
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            SubscribeRepository
                .setOnNetworkStatusListener(
                    this@MyPageViewModel.showProgress(true)
                )
                .setOnErrorListener {

                }
                .getSubscribe(pageNum).let { response ->
                    pageNum = response?.pageInfo?.pageNum ?: 1

                    val resList = response?.items ?: arrayListOf()
                    val curList = subscribeList.value ?: arrayListOf()

                    // 현재 리스트가 비어있지 않는데, response 리스트가 비어있을 경우 페이지가 끝난걸로 판단
                    if (curList.isNotEmpty() && resList.isEmpty()) {
                        isLast = true
                    }

                    _subscribeList.postValue(response?.items)
                }
        }
    }

//    /**
//     * Paged Config
//     */
//    private val pageListConfig = PagedList.Config.Builder()
//        .setPrefetchDistance(5)
//        .setInitialLoadSizeHint(20)
//        .setPageSize(20)
//        .setEnablePlaceholders(false)
//        .build()
//
//    /**
//     * Data Factory
//     */
//    private val dataSourceFactory = object : DataSource.Factory<Int, ProgramData>() {
//        override fun create(): DataSource<Int, ProgramData> {
//
//            return object : PageKeyedDataSource<Int, ProgramData>() {
//
//                override fun loadInitial(
//                    params: LoadInitialParams<Int>,
//                    callback: LoadInitialCallback<Int, ProgramData>
//                ) {
//                    viewModelScope.launch {
//                        Timber.d(">> loadInitial : $params")
//                        getSubscribe(1) { list, pageNum ->
//                            callback.onResult(list, null, pageNum)
//                        }
//                    }
//                }
//
//                override fun loadBefore(
//                    params: LoadParams<Int>,
//                    callback: LoadCallback<Int, ProgramData>
//                ) {
//                    viewModelScope.launch {
//                        Timber.d(">> loadBefore : $params")
//                        getSubscribe(params.key) { list, pageNum ->
//                            callback.onResult(list, pageNum.plus(1))
//                        }
//                    }
//                }
//
//                override fun loadAfter(
//                    params: LoadParams<Int>,
//                    callback: LoadCallback<Int, ProgramData>
//                ) {
//                    Timber.d(">> loadAfter : $params")
//                    getSubscribe(params.key) { list, pageNum ->
//                        callback.onResult(list, pageNum.minus(1))
//                    }
//                }
//            }
//
//        }
//    }
//
//    /**
//     * Live Paged List
//     */
//    val pagedList = LivePagedListBuilder(dataSourceFactory, pageListConfig)
//        .setBoundaryCallback(object : PagedList.BoundaryCallback<ProgramData>() {
//
//            override fun onItemAtFrontLoaded(itemAtFront: ProgramData) {
//                super.onItemAtFrontLoaded(itemAtFront)
//                Timber.d(">> onItemAtFrontLoaded")
//            }
//
//            override fun onItemAtEndLoaded(itemAtEnd: ProgramData) {
//                super.onItemAtEndLoaded(itemAtEnd)
//                Timber.d(">> onItemAtEndLoaded")
//            }
//
//            override fun onZeroItemsLoaded() {
//                super.onZeroItemsLoaded()
//                Timber.d(">> onZeroItemsLoaded")
//            }
//        })
//        .build()
//
//    /**
//     * Diff Callback
//     */
//    val diffCallback: DiffUtil.ItemCallback<ProgramData> =
//        object : DiffUtil.ItemCallback<ProgramData>() {
//            override fun areItemsTheSame(
//                oldItem: ProgramData,
//                newItem: ProgramData
//            ): Boolean {
//                return oldItem.roomId == newItem.roomId
//            }
//
//            override fun areContentsTheSame(
//                oldItem: ProgramData,
//                newItem: ProgramData
//            ): Boolean {
//                return oldItem.roomId == newItem.roomId
//            }
//        }
}
