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
        println("-> Cartas na Mão - $name:\n")
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

        if (handCards.isEmpty()) {
            println("| Nenhuma carta na mão" + "\n")
        } else {
            println(cardLine + "\n")
        }
    }

    fun showBoardCards() {
        println("-> Cartas no Tabuleiro - $name:\n")
        val cardLine = "+" + "-".repeat(100) + "+"

        for (i in 0..<boardCards.size) {
            val boardCard = if (i < boardCards.size) boardCards[i] else null

            println(cardLine)

            if (boardCard != null) {
                println("| Index: $i   Nome: ${boardCard.nameValue.padEnd(20)}")
                println("| Desc: ${boardCard.descriptionValue}")

                if (boardCard.equipmentList.isNotEmpty()) {
                    println("| Equip: ${boardCard.equipmentList.joinToString(", ") { it.nameValue }}")
                }

                println("| Modo: ${boardCard.modeValue.uppercase()}")
                println("| Tipo: ${boardCard.typeValue.uppercase(Locale.getDefault())}   ATK: ${boardCard.attackValue}   DEF: ${boardCard.defenseValue}")

            }
        }

        if (boardCards.isEmpty()) {
            println("| Nenhuma carta no tabuleiro" + "\n")
        } else {
            println(cardLine + "\n")
        }
    }

    fun existEquipCardsOnHand(): Boolean {
        return handCards.any { it.typeValue.lowercase() == "equipamento" }
    }

    fun existMonsterCardsOnHand(): Boolean {
        return handCards.any { it.typeValue.lowercase() == "monstro" }
    }

    fun existMonsterCardsOnBoard(): Boolean {
        return boardCards.any { it.typeValue.lowercase() == "monstro" }
    }

    fun addMonsterToBoard(index: Int, modeInt: Int): Boolean {
        if (index >= 0 && index < handCards.size) {
            val card = handCards[index]
            if (card.typeValue.lowercase() == "monstro") {
                val removedCard = handCards.removeAt(index)

                if (modeInt == 1) {
                    removedCard.modeValue = "defesa"
                } else if (modeInt == 2) {
                    removedCard.modeValue = "ataque"
                }

                boardCards.add(removedCard)

                return true
            }
        }

        return false
    }

    fun addEquipmentCard(indexEquip: Int, indexMonster: Int): Boolean {
        if ((indexEquip >= 0 && indexEquip < handCards.size) && (indexMonster >= 0 && indexMonster < boardCards.size)) {
            val equipCard: Card = handCards[indexEquip]
            val monsterCard: Card = boardCards[indexMonster]

            if (equipCard.typeValue.lowercase() == "equipamento" && monsterCard.typeValue.lowercase() == "monstro") {
                val removedCard = handCards.removeAt(indexEquip)
                monsterCard.addEquipment(removedCard)

                return true
            }
        }

        return false
    }

    val playerName: String
        get() = name

    var points: Int
        get() = healthPoints
        set(value) {}

    val handCardsSize: Int
        get() = handCards.size

    val boardCardsSize: Int
        get() = boardCards.size

}