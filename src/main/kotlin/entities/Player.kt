package entities

import tools.CardsReader

class Player(private val name: String) {
    private val handCards: MutableList<Card> = mutableListOf()
    private val boardCards: MutableList<Card> = mutableListOf()
    private var healthPoints: Int = 10000 // valor padrao

    val playerName: String
        get() = name

    var points: Int
        get() = healthPoints
        set(value) {}

}