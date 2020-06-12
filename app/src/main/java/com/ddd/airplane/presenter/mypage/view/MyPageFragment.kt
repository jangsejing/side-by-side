package com.ddd.airplane.presenter.mypage.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseFragment
import com.ddd.airplane.common.base.BasePagedListAdapter
import com.ddd.airplane.common.base.BaseRecyclerViewAdapter
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.common.manager.ChatRoomManager
import com.ddd.airplane.common.views.decoration.DividerItemGrid
import com.ddd.airplane.data.response.chat.ProgramData
import com.ddd.airplane.databinding.ChatListItemBinding
import com.ddd.airplane.databinding.MypageFragmentBinding
import com.ddd.airplane.databinding.ThumbnailGridItemBinding
import com.ddd.airplane.presenter.mypage.viewmodel.MyPageViewModel
import kotlinx.android.synthetic.main.chat_list_fragment.*
import kotlinx.android.synthetic.main.mypage_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 마이페이지
 * @author jess
 */
class MyPageFragment : BaseFragment<MypageFragmentBinding, MyPageViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.mypage_fragment
    override val viewModelClass = MyPageViewModel::class.java


    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initLayout() {

        rv_subscribe.apply {

            setHasFixedSize(true)
            addItemDecoration(
                DividerItemGrid(
                    2,
                    context.resources.getDimensionPixelSize(R.dimen.dp16)
                )
            )

            adapter = object :
                BaseRecyclerViewAdapter<ProgramData, ThumbnailGridItemBinding>(R.layout.thumbnail_grid_item) {
            }.apply {
                setOnItemClickListener { view, data ->
                    CoroutineScope(Dispatchers.IO).launch {
                        ChatRoomManager.joinChatRoom(context, data)
                    }
                }
            }

            setOnBoundListener {
                viewModel.getSubscribe()
            }
        }

    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscribe(true)
    }

//    /**
//     * Paged List 설정
//     */
//    private fun setPagedList() {
//        /**
//         * PagedList Adapter
//         */
//        val pagedAdapter = object : BasePagedListAdapter<ProgramData>(
//            R.layout.thumbnail_grid_item,
//            viewModel.diffCallback
//        ) {
//
//        }.apply {
//            setOnItemClickListener { view, data ->
//                CoroutineScope(Dispatchers.IO).launch {
//                    ChatRoomManager.joinChatRoom(context, data)
//                }
//            }
//        }
//
//        // adapter
//        rv_subscribe.apply {
//            setHasFixedSize(true)
//            addItemDecoration(
//                DividerItemGrid(
//                    2,
//                    context.resources.getDimensionPixelSize(R.dimen.dp16)
//                )
//            )
//
//            adapter = pagedAdapter
//        }
//
//        viewModel.pagedList.observe(viewLifecycleOwner, Observer {
//            pagedAdapter.submitList(it)
//        })
//    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}
