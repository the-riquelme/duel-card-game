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

        private fun chooseAction() {
            println("\n" + "#".repeat(25) + " -> Vez do Jogador: ${playerOfMoment.playerName} <- " + "#".repeat(25) + "\n")
            playerOfMoment.showHandCards()

            println("-> Possíveis ações na rodada:")

            if (playerOfMoment.existMonsterCardsOnHand()) {
                println("   a) Posicionar um novo monstro no tabuleiro")
            }

            if (playerOfMoment.existEquipCardsOnHand() && playerOfMoment.existMonsterCardsOnBoard()) {
                println("   b) Equipar um monstro com uma carta de equipamento")
            }

            if (playerOfMoment.handCardsSize > 0) {
                println("   c) Descartar uma carta da mão")
            }

            if (round > 1) {
                println("   d) Realizar um ataque contra o oponente")
            }

            if (playerOfMoment.existMonsterCardsOnBoard()) {
                println("   e) Alterar o estado de um monstro (ataque/defesa)")
            }

            print("\n=> Escolha sua ação de acordo com sua letra correspondente: ")
            val choice = readlnOrNull() ?: ""
        }

        fun start() {
            chooseAction()
        }
    }

}