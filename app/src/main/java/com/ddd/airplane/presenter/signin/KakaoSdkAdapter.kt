package com.ddd.airplane.presenter.signin

import com.ddd.airplane.AirPlaneApplication
import com.kakao.auth.*

class KakaoSdkAdapter : KakaoAdapter() {
    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_ACCOUNT)
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSaveFormData(): Boolean {
                return true
            }

            override fun isSecureMode(): Boolean {
                return true
            }
        }
    }

    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig {
            AirPlaneApplication.instance?.getGlobalApplicationContext()
        }
    }
}