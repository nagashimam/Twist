package com.twist4english.contract

interface PlayPresenterContract {
    fun retry(result: Pair<String, Float>)
    fun nextTongueTwister(score: Float)
    fun finish(scores: List<Float>)
}