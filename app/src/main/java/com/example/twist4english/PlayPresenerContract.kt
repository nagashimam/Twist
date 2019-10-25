package com.example.twist4english

interface PlayPresenerContract {
    fun retry(result: Pair<String, Float>)
    fun nextTangueTwister(score: Float)
}