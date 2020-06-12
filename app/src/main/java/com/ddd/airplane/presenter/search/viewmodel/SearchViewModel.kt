package com.ddd.airplane.presenter.search.viewmodel

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
import com.ddd.airplane.common.utils.tryCatch
import com.ddd.airplane.data.response.chat.ProgramData
import com.ddd.airplane.repository.network.GeneralRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var searchFor: String? = null

    // 검색결과 리스트
    private val _searchList = MutableLiveData<List<ProgramData>>()
    val searchList: LiveData<List<ProgramData>> = _searchList

    // 검색결과 여부
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    // 리스츠 초기화 여부
    private val _isClear = MutableLiveData<Boolean>()
    val isClear: LiveData<Boolean> = _isClear

    // 페이징 처리 값
    private var pageNum = 1

    fun onTextChanged(s: CharSequence?) {
        tryCatch {

            val searchText = s.toString().trim()

            // 빈값 처리
            if (searchText.isEmpty()) {
                _isEmpty.value = true

                // 리스트 처리
                _searchList.value = mutableListOf()
                _isClear.value = true
                return
            }
            _isEmpty.value = false

            // 같은 값 처리
            if (searchText == searchFor) {
                return
            }
            searchFor = searchText

            CoroutineScope(Dispatchers.Main).launch {
                delay(300)  // debounce timeOut
                if (searchText != searchFor) {
                    return@launch
                }
                Timber.d(">> $searchFor")
                getSearchList(searchText, true)
            }
        }
    }

    /**
     * 검색 Request
     */
    fun getSearchList(
        text: String?,
        isClear: Boolean = false
    ) {

        if (text.isNullOrEmpty()) {
            return
        }

        if (isClear) {
            _isClear.value = isClear
        }

        viewModelScope.launch {
            GeneralRepository
                .setOnNetworkStatusListener(this@SearchViewModel)
                .setOnErrorListener {

                }
                .getSearch(searchFor!!, pageNum)
                ?.let { response ->
                    pageNum = response.pageInfo?.pageNum ?: 1
                    _searchList.value = response.items?.toMutableList() ?: mutableListOf()
                }
        }
    }

//    /**
//     * 많이 참여한 방송 Request
//     */
//    private fun getManyList(
//        position: Int = 1,
//        listener: ((List<ProgramData>, Int) -> Unit)? = null
//    ) {
//        viewModelScope.launch(ioDispatchers) {
//
//        }
//    }

    /**
     * Paged Config
     */
    private val pageListConfig = PagedList.Config.Builder()
        .setPrefetchDistance(5)
        .setInitialLoadSizeHint(10)
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    /**
     * 많이 참여한 방송 DataSourceFactory
     */
    private val manyDataSourceFactory = object : DataSource.Factory<Int, ProgramData>() {

        override fun create(): DataSource<Int, ProgramData> {

            return object : PageKeyedDataSource<Int, ProgramData>() {

                override fun loadInitial(
                    params: LoadInitialParams<Int>,
                    callback: LoadInitialCallback<Int, ProgramData>
                ) {
                    Timber.d(">> loadInitial : $params")
                }

                override fun loadBefore(
                    params: LoadParams<Int>,
                    callback: LoadCallback<Int, ProgramData>
                ) {
                    Timber.d(">> loadBefore : $params")

                }

                override fun loadAfter(
                    params: LoadParams<Int>,
                    callback: LoadCallback<Int, ProgramData>
                ) {

                }
            }
        }
    }


    /**
     * Diff Callback
     */
    val diffCallback: DiffUtil.ItemCallback<ProgramData> =
        object : DiffUtil.ItemCallback<ProgramData>() {
            override fun areItemsTheSame(
                oldItem: ProgramData,
                newItem: ProgramData
            ): Boolean {
                return oldItem.roomId == newItem.roomId
            }

            override fun areContentsTheSame(
                oldItem: ProgramData,
                newItem: ProgramData
            ): Boolean {
                return oldItem.roomId == newItem.roomId
            }
        }

    /**
     * 많이 참여한 방송
     */
    var manyPagedList = LivePagedListBuilder(manyDataSourceFactory, pageListConfig).build()
}