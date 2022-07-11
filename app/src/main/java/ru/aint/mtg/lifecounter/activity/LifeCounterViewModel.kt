package ru.aint.mtg.lifecounter.activity

import androidx.lifecycle.ViewModel

class LifeCounterViewModel: ViewModel() {

    // MARK: - Data types

    enum class Change {
        plusOne,
        minusOne
    }

    // MARK: - State

    private var playerTotal: Int = 20
    private var opponentTotal: Int = 20

    // MARK: - Callbacks

    var onTotalsChanged: ((Int, Int) -> Unit)? = null

    // MARK: - Public interface

    fun changePlayerTotal(change: Change) {
        when (change) {
            Change.plusOne -> playerTotal = min(playerTotal + 1, 999)
            Change.minusOne -> playerTotal = max(playerTotal - 1, 0)
        }
        notifyOnChange()
    }

    fun changeOpponentTotal(change: Change) {
        when (change) {
            Change.plusOne -> opponentTotal = min(opponentTotal + 1, 999)
            Change.minusOne -> opponentTotal = max(opponentTotal - 1, 0)
        }
        notifyOnChange()
    }

    fun reset() {
        playerTotal = 20
        opponentTotal = 20
        notifyOnChange()
    }

    fun getTotals(): Pair<Int, Int> {
        return Pair(playerTotal, opponentTotal)
    }

    // MARK: - Private

    private fun notifyOnChange() {
        onTotalsChanged?.invoke(playerTotal, opponentTotal)
    }

    private fun max(a: Int, b: Int): Int {
        return if (a > b) {
            a
        } else {
            b
        }
    }

    private fun min(a: Int, b: Int): Int {
        return if (a < b) {
            a
        } else {
            b
        }
    }
}