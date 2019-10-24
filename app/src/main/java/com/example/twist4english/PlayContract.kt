package com.example.twist4english

interface PlayContract {
    fun start()
    fun end()
    fun showMessage(msg : String)
    fun getRequiredScore() : Int
    fun showNextTongueTwister()
    fun proceedToScoreActivity()
}