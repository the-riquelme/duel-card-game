package entities

import tools.CardsReader

class CardGame {

    companion object {
        private val cards: MutableList<Card>
        private var round: Int = 1
        private val players: MutableList<Player>
        private var playerOfMoment: Player

        init {
            println("#".repeat(25) + " -> Bem-vindo ao Duel Card Game <- " + "#".repeat(25) + "\n")
            cards = CardsReader.getCards()
            players = playersEntry()
            playerOfMoment = players.first()
        }

        private fun playersEntry(): MutableList<Player> {
            val players = mutableListOf<Player>()

            for (i in 1..2) {
                print("=> Informe o nome do jogador $i: ")
                val name = readlnOrNull() ?: ""
                val handCards: MutableList<Card> = CardsReader.pickFiveRandomCards(cards)
                players.add(Player(name, handCards))
            }

            return players
        }

        fun start() {
            playerOfMoment.showHandCards()
        }
    }

}