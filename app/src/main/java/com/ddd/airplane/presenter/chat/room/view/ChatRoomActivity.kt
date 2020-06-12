package com.ddd.airplane.presenter.chat.room.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.common.base.BaseRecyclerViewAdapter
import com.ddd.airplane.common.views.decoration.DividerItemSpace
import com.ddd.airplane.data.response.chat.ChatMessageData
import com.ddd.airplane.databinding.ChatMsgItemBinding
import com.ddd.airplane.databinding.ChatRoomActivityBinding
import com.ddd.airplane.presenter.chat.room.viewmodel.ChatMsgViewModel
import com.ddd.airplane.presenter.chat.room.viewmodel.ChatRoomViewModel
import com.petersamokhin.android.floatinghearts.HeartsRenderer
import com.petersamokhin.android.floatinghearts.HeartsView
import kotlinx.android.synthetic.main.chat_room_activity.*

/**
 * 채팅
 * @author jess
 */
class ChatRoomActivity : BaseActivity<ChatRoomActivityBinding, ChatRoomViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.chat_room_activity
    override val viewModelClass = ChatRoomViewModel::class.java
    //TODO 리스트에서 id 받아서 set
    private var roomId: Long = 0

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initLayout() {

        val views = arrayOf(tv_send_msg, iv_hold_info, tv_subscribe_room, tv_more, cl_like)
        views.forEach {
            it.setOnClickListener(this)
        }

        et_chat_msg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_chat_msg.text.isNotEmpty()) {
                    tv_send_msg.visibility = View.VISIBLE
                    iv_room_like.visibility = View.GONE
                } else {
                    tv_send_msg.visibility = View.GONE
                    iv_room_like.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        rv_chat.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemSpace(
                    DividerItemSpace.VERTICAL,
                    context.resources.getDimensionPixelSize(R.dimen.dp8)
                )
            )
            adapter = object :
                BaseRecyclerViewAdapter<ChatMessageData.MessageData, ChatMsgItemBinding>(R.layout.chat_msg_item) {

                override fun onBindData(
                    position: Int,
                    data: ChatMessageData.MessageData?,
                    dataBinding: ChatMsgItemBinding
                ) {
                    dataBinding.viewModel = ChatMsgViewModel().apply {
                        setChatMsg(data)
                    }
                }

            }

            addItemDecoration(
                DividerItemSpace(
                    DividerItemSpace.HORIZONTAL,
                    context.resources.getDimensionPixelSize(R.dimen.dp4)
                )
            )
        }

    }

    override fun onCreated(savedInstanceState: Bundle?) {
        if (intent.hasExtra("roomId")) {
            roomId = intent.getLongExtra("roomId", 0)
        }
        viewModel.getChatRoomInfo(roomId)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.disconnectChatClient()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_send_msg -> {
                viewModel.sendChatMessage(et_chat_msg.text.toString())
                et_chat_msg.text.clear()
//                DeviceUtils.hideKeyboard(v)
                scrollDown()
            }

            R.id.iv_hold_info -> {
                v.rotation = v.rotation + 180
                cl_info_second.visibility =
                    if (cl_info_second.visibility == View.GONE) View.VISIBLE else View.GONE
                cl_info_third.visibility =
                    if (cl_info_third.visibility == View.GONE) View.VISIBLE else View.GONE
                tv_live.visibility =
                    if (tv_live.visibility == View.GONE) View.VISIBLE else View.GONE
            }

            R.id.tv_subscribe_room -> {
                viewModel.doSubscribe()
            }

            R.id.tv_more -> {
                v.visibility = View.GONE
            }

            R.id.cl_like -> {
                initHeartView()
            }
        }
    }

    private fun initHeartView() {
        val drawable = getDrawable(R.drawable.ic_heart)

        drawable?.let {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)


            val model = HeartsView.Model(
                0,                         // Unique ID of this image, used for Rajawali materials caching
                bitmap                          // Bitmap image
            )

            val config = HeartsRenderer.Config(
                2f,
                0.08f,
                2f
            )
            heartsView.applyConfig(config)

            heartsView.emitHeart(model)
        }
    }

    /**
     * 스크롤 다운
     */
    private fun scrollDown() {
        if (rv_chat.childCount > 0) {
            rv_chat.postDelayed({
                val count = rv_chat.adapter?.itemCount ?: 0
                rv_chat.scrollToPosition(if (count > 0) count - 1 else 0)
            }, 100)
        }
    }
}
