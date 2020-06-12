package com.ddd.airplane.presenter.chat.room.viewmodel

import com.ddd.airplane.R
import com.ddd.airplane.data.response.chat.ChatMessageData
import timber.log.Timber

class ChatMsgViewModel {

    var messageId: Long? = 0
    var senderId: String = ""
    var content: String = ""
    var textColor: Int? = R.color.brand_white

    fun setChatMsg(model: ChatMessageData.MessageData?) {
        model?.let { msg ->
            messageId = msg.messageId
            senderId = msg.senderId.toString()
            content = msg.content.toString()

            Timber.d(">> ${model.messageId?.rem(11)?.toInt()}")
            textColor = when (model.messageId?.rem(11)?.toInt()) {
                0 -> {
                    R.color.text_grey
                }
                1 -> {
                    R.color.text_baby
                }
                2 -> {
                    R.color.text_sky
                }
                3 -> {
                    R.color.text_yellow
                }
                4 -> {
                    R.color.text_pink
                }
                5 -> {
                    R.color.text_blue
                }
                6 -> {
                    R.color.text_navy
                }
                7 -> {
                    R.color.text_orange
                }
                8 -> {
                    R.color.text_green
                }
                9 -> {
                    R.color.text_purple
                }
                10 -> {
                    R.color.text_deep
                }

                else -> {
                    R.color.text_grey
                }
            }

            Timber.d(">> textColor $textColor")
        }
    }
}