package com.example.twist4english.contract

interface PlayPresenerContract {
    fun retry(result: Pair<String, Float>)
    fun nextTangueTwister(score: Float)
}