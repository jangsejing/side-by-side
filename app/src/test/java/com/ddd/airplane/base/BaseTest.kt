package com.ddd.airplane.base

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

open class BaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule() // LiveData 테스트를 위한 객체

    @ExperimentalCoroutinesApi
    val testDispatcher = TestCoroutineDispatcher()

    @Mock
    val applicationMock = Mockito.mock(Application::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

}