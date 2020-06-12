package com.ddd.airplane.presenter.chat.list.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.data.response.chat.ProgramData

class ChatListViewModel(application: Application) : BaseViewModel(application) {

    // 채팅 리스트
    private val _chatList = MutableLiveData<ArrayList<ProgramData>>()
    val chatList: LiveData<ArrayList<ProgramData>> = _chatList

    /**
     * 채팅 리스트
     */
    fun getChatList() {
//
//        val list = ArrayList<ProgramData>().apply {
//
//            add(
//                ProgramData(
//                    roomUserCount = 140,
//                    subjectName = "남산의 부장",
//                    subjectDescription = "“각하,  제가 어떻게 하길 원하십니까”\n" +
//                            "1979년 10월 26일, 중앙정보부장 김규평(이병헌)이 대한민국 대통령을 암살한다.\n" +
//                            "이 사건의 40일전, 미국에서는 전 중앙정보부장 박용각(곽도원)이 \n" +
//                            "청문회를 통해 전 세계에 정권의 실체를 고발하며 파란을 일으킨다.\n" +
//                            "그를 막기 위해 중앙정보부장 김규평과 경호실장 곽상천(이희준)이 나서고,\n" +
//                            "대통령 주변에는 충성 세력과 반대 세력들이 뒤섞이기 시작하는데…",
//                    subjectSubscribeCount = 10
//                )
//            )
//
//            add(
//                ProgramData(
//                    roomUserCount = 423,
//                    subjectName = "히트맨",
//                    subjectDescription = "웹툰 작가가 되고 싶어 국정원을 탈출한 \n" +
//                            "비밀 프로젝트 방패연 출신 전설의 암살요원 '준'.\n" +
//                            "그러나 현실은 연재하는 작품마다 역대급 악플만 받을 뿐이다.\n" +
//                            "술김에 그리지 말아야 할 1급 기밀을 그려버리고\n" +
//                            "예상치 않게 웹툰은 하루아침에 초대박이 나지만,\n" +
//                            "그로 인해 '준'은 국정원과 테러리스트의 더블 타깃이 되는데...",
//                    subjectSubscribeCount = 23
//                )
//            )
//
//            add(
//                ProgramData(
//                    roomUserCount = 10,
//                    subjectName = "미스터 주- 사라진 VIP",
//                    subjectDescription = "동물의 말이 들리는 순간, \n" +
//                            "수사의 파트너가 바뀐다! \n" +
//                            "국가정보국 에이스 요원 태주.\n" +
//                            "특사로 파견된 VIP 경호 임무를 수행하던 중, \n" +
//                            "갑작스러운 사고로 VIP는 사라지고\n" +
//                            "설상가상 온갖 동물들의 말이 들리기 시작한다.\n" +
//                            "갑자기 이상한 행동을 하는 태주를 의심하는 민국장과 만식을 뒤로 하고,\n" +
//                            "태주는 군견 알리와 함께 VIP를 찾아 나서는데…",
//                    subjectSubscribeCount = 10
//                )
//            )
//        }
//        _chatList.value = list
    }

}
