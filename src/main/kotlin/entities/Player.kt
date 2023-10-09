package entities

import tools.CardsReader
import java.util.*

class Player(
    private val name: String,
    private val handCards: MutableList<Card>
) {
    private val boardCards: MutableList<Card> = mutableListOf()
    private var healthPoints: Int = 10000 // valor padrao

    fun showHandCards() {
        println("\n-> Cartas na Mão:\n")
        val cardLine = "+" + "-".repeat(100) + "+"

        for (i in 0..<handCards.size) {
            val handCard = if (i < handCards.size) handCards[i] else null

            println(cardLine)

            if (handCard != null) {
                println("| Index: $i   Nome: ${handCard.nameValue.padEnd(20)}")
                println("| Desc: ${handCard.descriptionValue}")

                if (handCard.equipmentList.isNotEmpty()) {
                    println("| Equip: ${handCard.equipmentList.joinToString(", ") { it.nameValue }}")
                }

                println("| Tipo: ${handCard.typeValue.uppercase(Locale.getDefault())}   ATK: ${handCard.attackValue}   DEF: ${handCard.defenseValue}")

            }
        }

        println(cardLine)
    }

    val playerName: String
        get() = name

    var points: Int
        get() = healthPoints
        set(value) {}

}