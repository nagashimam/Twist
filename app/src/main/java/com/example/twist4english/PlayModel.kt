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
        val formatter = { str: String ->
            str.toLowerCase(Locale.getDefault())
                .replace(",", "")
                .replace(".", "")
                .replace(";", "")
                .replace(":", "")
                .replace("  ", " ")
        }
        val expectedSpeech = formatter(expectedResult.first)
        val actualSpeech = formatter(actualResult.first)
        return expectedSpeech == actualSpeech && expectedResult.second <= actualResult.second
    }
}