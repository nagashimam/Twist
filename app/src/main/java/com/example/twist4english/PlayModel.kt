package com.example.twist4english

import com.example.twist4english.contract.PlayPresenterContract
import java.util.*

typealias PlayResult = Pair<Pair<String, Float>, Pair<String, Float>>
object PlayModel {
    fun judgeResult(result: PlayResult, presenterContract: PlayPresenterContract) {
        if (isValidResult(result)) {
            presenterContract.nextTongueTwister(result.second.second)
        } else {
            presenterContract.retry(result.second)
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