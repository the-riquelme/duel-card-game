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

        private fun positionMonsterOnBoard(): Boolean {
            println("\n--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Informe o índice da carta de tipo MONSTRO na sua MÃO que deseja posicionar no TABULEIRO: ")
            val index = readlnOrNull() ?: ""

            if (index == "voltar") {
                return false
            }

            println("\n--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Seu monstro deve ser posicionado em MODO de DEFESA(1) ou ATAQUE(2): ")
            val mode = readlnOrNull() ?: ""

            if (mode == "voltar") {
                return false
            }

            val indexInt = index.toIntOrNull() ?: -1
            val modeInt = mode.toIntOrNull() ?: -1
            if (!playerOfMoment.addMonsterToBoard(indexInt, modeInt)) {
                println("!!! Escolha inválida! Tente novamente.")
                return positionMonsterOnBoard()
            }

            print("\n=> Carta posicionada com sucesso!\n")
            return true
        }

        private fun increaseEquipmentCard(): Boolean {
            println("\n--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Informe o índice da carta de tipo EQUIPAMENTO na sua MÃO que deseja adicionar a um MONSTRO: ")
            val indexEquip = readlnOrNull() ?: ""

            if (indexEquip == "voltar") {
                return false
            }

            println("\n--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Informe o índice da carta de tipo MONSTRO no seu TABULEIRO que receberá o EQUIPAMENTO: ")
            val indexMonster = readlnOrNull() ?: ""

            if (indexMonster == "voltar") {
                return false
            }

            val indexEquipInt = indexEquip.toIntOrNull() ?: -1
            val indexMonsterInt = indexMonster.toIntOrNull() ?: -1

            if (!playerOfMoment.addEquipmentCard(indexEquipInt, indexMonsterInt)) {
                println("!!! Escolha inválida! Tente novamente.")
                return increaseEquipmentCard()
            }

            print("\n=> Carta de Equipamento adicionada com sucesso!\n")
            return true
        }

        private fun discardCard() {
            println("\n--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Informe o índice da CARTA na sua MÃO que deseja DESCARTAR: ")
            val index = readlnOrNull() ?: ""

            if (index == "voltar") {
                return
            }

            val indexInt = index.toIntOrNull() ?: -1
            if (!playerOfMoment.discardCard(indexInt)) {
                println("!!! Escolha inválida! Tente novamente.")
                return discardCard()
            }

            print("\n=> Carta DESCARTADA com sucesso!\n")
        }

        private fun attackOpponent() {

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
                }

                if (!equipAdded && playerOfMoment.existEquipCardsOnHand() && playerOfMoment.existMonsterCardsOnBoard()) {
                    println("   b) Equipar um monstro com uma carta de equipamento")
                }

                if (playerOfMoment.handCardsSize > 0) {
                    println("   c) Descartar uma carta da mão")
                }

                if (playerOfMoment.existMonsterCardsOnBoard()) {
                    if (round > 1 && playerOfMoment.existsMonsterToAttack()) {
                        println("   d) Realizar um ataque contra o oponente")
                    }

                    println("   e) Alterar o estado de um monstro (ataque/defesa)")
                }

                print("\n=> Escolha sua ação de acordo com sua letra correspondente: ")
                val choice = readlnOrNull() ?: ""

                when (choice) {
                    "a" -> {
                        if (!monsterAdded && playerOfMoment.existMonsterCardsOnHand() && playerOfMoment.boardCardsSize < 5) {
                            monsterAdded = positionMonsterOnBoard()
                        } else {
                            println("Escolha inválida.")
                        }
                    }
                    "b" -> {
                        if (!equipAdded && playerOfMoment.existEquipCardsOnHand() && playerOfMoment.existMonsterCardsOnBoard()) {
                            equipAdded = increaseEquipmentCard()
                        } else {
                            println("Escolha inválida.")
                        }
                    }
                    "c" -> {
                        if (playerOfMoment.handCardsSize > 0) {
                            discardCard()
                        } else {
                            println("Escolha inválida.")
                        }
                    }
                    "d" -> {
                        if (round > 1 && playerOfMoment.existMonsterCardsOnBoard() && playerOfMoment.existsMonsterToAttack()) {
//                            attackOpponent()
                        } else {
                            println("Escolha inválida.")
                        }
                    }
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