package com.example.twist4english

import com.example.twist4english.contract.PlayPresenerContract
import java.util.*

typealias PlayResult = Pair<Pair<String, Float>, Pair<String, Float>>
object PlayModel {
    fun judgeResult(result: PlayResult, presenerContract: PlayPresenerContract) {
        if (isValidResult(result)) {
            presenerContract.nextTangueTwister(result.second.second)
        } else {
            presenerContract.retry(result.second)
        }
    }

    private fun isValidResult(result: PlayResult): Boolean {
        val (expectedResult, actualResult) = result
        val expectedSpeech = expectedResult.first.toLowerCase(Locale.getDefault())
        val actualSpeech  = actualResult.first.toLowerCase(Locale.getDefault())
        return expectedSpeech == actualSpeech && expectedResult.second <= actualResult.second
    }
}