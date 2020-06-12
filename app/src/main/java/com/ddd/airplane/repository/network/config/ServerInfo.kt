package com.ddd.airplane.repository.network.config

/**
 * @author jess
 */
object ServerInfo {

    const val image = "http://15.164.213.75"

    public enum class DOMAIN(val domain: String) {
        DEV("http://15.164.213.75"),
        REAL("http://15.164.213.75")
    }
}