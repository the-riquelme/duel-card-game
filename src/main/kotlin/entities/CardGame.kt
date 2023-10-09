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

        private fun positionMonsterOnBoard() {
            print("\n=> Informe o índice do carta do tipo Monstro que deseja posicionar no tabuleiro ou digite 'voltar' pra retornar ao menu: ")
            val index = readlnOrNull() ?: ""

            if (index == "voltar") {
                return chooseAction()
            }

            println("\n-> Se monstro deve ser posicionado em modo de defesa(1) oud e ataque(2): ")
            val mode = readlnOrNull() ?: ""

            val indexInt = index.toIntOrNull() ?: -1;
            val modeInt = mode.toIntOrNull() ?: -1;
            if (!playerOfMoment.addMonsterToBoard(indexInt, modeInt)) {
                println("Escolha inválida! Tente novamente.")
                return positionMonsterOnBoard()
            }

            print("\n=> Carta posicionada com sucesso!")
        }

        private fun chooseAction() {
            var gameIsRunning = true
            var monsterAdded = false
            var equipAdded = false

            while (gameIsRunning) {
                println("\n" + "#".repeat(25) + " -> Vez do Jogador: ${playerOfMoment.playerName} <- " + "#".repeat(25) + "\n")
                playerOfMoment.showBoardCards()
                playerOfMoment.showHandCards()

                println("-> Possíveis ações na rodada:")

                if (!monsterAdded && playerOfMoment.existMonsterCardsOnHand() && playerOfMoment.boardCardsSize < 5) {
                    println("   a) Posicionar um novo monstro no tabuleiro")
                    monsterAdded = true
                }

                if (!equipAdded && playerOfMoment.existEquipCardsOnHand() && playerOfMoment.existMonsterCardsOnBoard()) {
                    println("   b) Equipar um monstro com uma carta de equipamento")
                    equipAdded = true
                }

                if (playerOfMoment.handCardsSize >= 0) {
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

                when (choice) {
                    "a" -> positionMonsterOnBoard()
                    else -> {
                        println("Escolha inválida.")
                        gameIsRunning = false
                    }
                }
            }

        }

        fun start() {
            chooseAction()
        }
    }

}