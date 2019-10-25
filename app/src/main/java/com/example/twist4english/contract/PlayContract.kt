package com.example.twist4english.contract

interface PlayContract {
    fun start()
    fun end()
    fun showMessage(msg : String)
    fun getExpectedResult(): Pair<String, Float>
    fun showNextTongueTwister()
    fun proceedToScoreActivity()
}