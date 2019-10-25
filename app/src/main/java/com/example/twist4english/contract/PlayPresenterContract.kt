package com.example.twist4english.contract

interface PlayPresenterContract {
    fun retry(result: Pair<String, Float>)
    fun nextTangueTwister(score: Float)
}