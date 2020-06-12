package com.ddd.airplane.signup

import com.ddd.airplane.base.BaseTest
import com.ddd.airplane.presenter.signup.viewmodel.SignUpViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock

class SignUpTest : BaseTest() {

    @Mock
    private val viewModel = SignUpViewModel(applicationMock)

    @Test
    fun `이메일 휴효성 검사 - 정상 1`() {
        assertTrue(viewModel.isValidEmail("test@test.com"))
    }

    @Test
    fun `이메일 휴효성 검사 - 정상 2`() {
        assertTrue(viewModel.isValidEmail("test@test.co.kr"))
    }

    @Test
    fun `이메일 휴효성 검사 - 실패`() {
        assertFalse(viewModel.isValidEmail("test@test"))
    }

    @Test
    fun `비밀번호 휴효성 검사 - 정상`() {
        assertTrue(viewModel.isValidPassword("qqqqqq"))
    }

    @Test
    fun `비밀번호 휴효성 검사 - 실패`() {
        assertFalse(viewModel.isValidPassword("qq"))
    }

    @Test
    fun `회원가입 결과 - 정상`() = runBlockingTest {
        // given
        viewModel.doSignUp("test@test.co.kr", "qqqqqq", "test")

        // when
        viewModel.setSignUpResult(true)

        // then
        assertTrue(viewModel.isSucceed.value!!)
    }

    @Test
    fun `회원가입 결과 - 실패`() = runBlockingTest {
        // given
        viewModel.doSignUp("test@test.co.kr", "qqqqqq", "test")

        // when
        viewModel.setSignUpResult(false)

        // then
        assertFalse(viewModel.isSucceed.value!!)
    }
}