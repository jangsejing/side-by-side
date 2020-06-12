package com.ddd.airplane.presenter.search.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseFragment
import com.ddd.airplane.common.base.BaseRecyclerViewAdapter
import com.ddd.airplane.common.manager.ChatRoomManager
import com.ddd.airplane.common.utils.DeviceUtils
import com.ddd.airplane.common.views.decoration.DividerItemSpace
import com.ddd.airplane.data.response.chat.ProgramData
import com.ddd.airplane.databinding.SearchFragmentBinding
import com.ddd.airplane.databinding.SearchItemBinding
import com.ddd.airplane.presenter.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * 검색
 * @author jess
 */
class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.search_fragment
    override val viewModelClass = SearchViewModel::class.java

    override fun onResume() {
        super.onResume()
        et_search.requestFocus()
        DeviceUtils.showKeyboard(et_search)
    }

    override fun onPause() {
        et_search.clearFocus()
        DeviceUtils.hideKeyboard(et_search)
        super.onPause()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel.run {

        }
    }

    override fun initLayout() {
        cl_delete.setOnClickListener(this)

        // 검색
        rv_search.run {
            adapter = object : BaseRecyclerViewAdapter<ProgramData, SearchItemBinding>(
                R.layout.search_item
            ) {

            }.apply {
                setOnItemClickListener { view, data ->
                    CoroutineScope(Dispatchers.IO).launch {
                        ChatRoomManager.joinChatRoom(context, data)
                    }
                }
            }

            setOnBoundListener {
                viewModel.getSearchList(et_search.text.toString())
            }
        }

        // 많이 참여한 방송
        rv_many.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemSpace(
                    LinearLayout.HORIZONTAL,
                    context.resources.getDimensionPixelSize(R.dimen.dp12)
                )
            )
        }

        et_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextChanged(s)
            }

        })

        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.getSearchList(et_search.text.toString(), true)
                    return true
                }
                return false
            }
        })
    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }


    /**
     * 많이 참여한 방송 RecyclerView Paged List 설정
     */
    private fun setManyPagedList() {

//        /**
//         * PagedList Adapter
//         */
//        val pagedAdapter = object : BasePagedListAdapter<ProgramData>(
//            R.layout.thumbnail_general_item,
//            viewModel.diffCallback
//        ) {
//
//        }.apply {
//            setOnItemClickListener { view, data ->
//                context?.showToast(data?.roomId.toString())
//            }
//        }
//
//        // adapter
//        rv_search.adapter = pagedAdapter
//
//        viewModel.searchPagedList?.observe(viewLifecycleOwner, Observer {
//            pagedAdapter.submitList(it)
//        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_delete -> {
                et_search.text = null
            }
        }
    }
}
