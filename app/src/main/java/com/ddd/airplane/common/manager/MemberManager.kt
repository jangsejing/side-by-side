package com.ddd.airplane.common.manager

import android.content.Context
import android.content.Intent
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.presenter.signin.view.SignInActivity
import com.ddd.airplane.repository.database.MemberRepository
import com.ddd.airplane.repository.database.member.MemberEntity
import com.ddd.airplane.repository.database.room.RoomManager
import com.ddd.airplane.repository.network.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

/**
 * 회원정보 관리
 */
object MemberManager {

    private val memberDao = RoomManager.instance.memberDao()

    // 로그인 리스너
    private var sigInInListener: ((Boolean) -> Unit)? = null

    /**
     * 로그인
     */
    fun signIn(context: Context?, sigInInListener: ((Boolean) -> Unit)? = null) {
        this.sigInInListener = sigInInListener
        Intent(context, SignInActivity::class.java).let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context?.startActivity(it)
        }
    }

    /**
     * 로그인 결과
     */
    fun signInResult(signIn: Boolean) {
        // listener 있으면 리턴 없으면 postValue
        sigInInListener?.invoke(signIn)
        sigInInListener = null
    }

    /**
     * 로그아웃
     */
    suspend fun signOut(listener: (() -> Unit)? = null) {
        TokenManager.removeToken()
        removeAccount()
        listener?.invoke()
    }

    /**
     * 계정정보 삭제
     *
     */
    private suspend fun removeAccount() {
        MemberRepository.deleteAll()
    }


    /**
     * 계정정보
     */
    suspend fun getAccount(listener: ((MemberEntity?) -> Unit)?) {
        listener?.invoke(MemberRepository.select())
    }

    /**
     * 계정정보 저장
     */
    suspend fun setAccount(
        status: OnNetworkStatusListener,
        scope: CoroutineScope,
        email: String
    ) = withContext(scope.coroutineContext) {

        var isSuccess = false

        UserRepository
            .setOnNetworkStatusListener(
                status.showProgress(true)
            )
            .setOnErrorListener {

            }.getAccounts(email)
            ?.let { response ->
                MemberRepository.insertMember(
                    MemberEntity(
                        response.email ?: "",
                        response.nickname ?: ""
                    )
                )
                isSuccess = true
            }

        isSuccess
    }
}