package com.ddd.airplane.presenter.chat.list.view

import android.os.Bundle
import android.view.View
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseFragment
import com.ddd.airplane.common.base.BaseRecyclerViewAdapter
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.data.response.chat.ProgramData
import com.ddd.airplane.databinding.ChatListFragmentBinding
import com.ddd.airplane.databinding.ChatListItemBinding
import com.ddd.airplane.presenter.chat.list.viewmodel.ChatListViewModel
import kotlinx.android.synthetic.main.chat_list_fragment.*

/**
 * 채팅 리스트
 * @author jess
 */
class ChatListFragment : BaseFragment<ChatListFragmentBinding, ChatListViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.chat_list_fragment

    override val viewModelClass = ChatListViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initLayout() {

        rv_chat.apply {
            setHasFixedSize(true)
            adapter = object :
                BaseRecyclerViewAdapter<ProgramData, ChatListItemBinding>(R.layout.chat_list_item) {
            }.apply {
                setOnItemClickListener { view, data ->
                    context.showToast(data?.roomId.toString())
                }
            }
        }

    }

    override fun onCreated(savedInstanceState: Bundle?) {
        viewModel.getChatList()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}
