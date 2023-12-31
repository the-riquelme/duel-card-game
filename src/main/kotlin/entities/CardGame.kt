package entities

import tools.CardsReader

class CardGame {

    companion object {
        private val cards: MutableList<Card>
        private var round: Int = 2
        private val players: MutableList<Player>
        private var playerOfMoment: Player

        // Inicialização estática - é executada quando a classe é carregada.

        init {
            println("#".repeat(25) + " -> Bem-vindo ao Duel Card Game <- " + "#".repeat(25) + "\n")
            cards = CardsReader.getCards()
            players = playersEntry()
            playerOfMoment = players.first()
        }

        // Inicialização do jogo, carregando as cartas e os jogadores.
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

        // Função para entrada de jogadores.
        private fun returnOtherPlayer(): Player {
            return if (playerOfMoment == players.first()) {
                players.last()
            } else {
                players.first()
            }
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
            if ((modeInt < 1 || modeInt > 2) || !playerOfMoment.addMonsterToBoard(indexInt, modeInt)) {
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
            playerOfMoment.showBoardMonsterCardsOnAttack()

            println("--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Informe o índice de um desses MONSTROS no seu TABULEIRO: ")
            val index = readlnOrNull() ?: ""

            if (index == "voltar") {
                return
            }

            val indexInt = index.toIntOrNull() ?: -1
            val monsterCardAttack = playerOfMoment.findMonsterCardToAttack(indexInt)
            if (monsterCardAttack == null) {
                println("!!! Escolha inválida! Tente novamente.")
                return attackOpponent()
            }

            val otherPlayer: Player = returnOtherPlayer()
            if (otherPlayer.existMonsterCardsOnBoard()) {
                otherPlayer.showBoardMonsterCardsAsOponent()

                println("--> Digite 'voltar' pra retornar ao menu <--")
                print("=> Informe o índice de um dos MONSTROS no TABULEIRO do seu oponente: ")
                val indexOponent = readlnOrNull() ?: ""

                if (indexOponent == "voltar") {
                    return
                }

                val indexOponentInt = indexOponent.toIntOrNull() ?: -1
                val monsterCardReceiveAttack = otherPlayer.receivesAttackOnMonsterCard(monsterCardAttack.attackValue, indexOponentInt, playerOfMoment)
                if (monsterCardReceiveAttack == null) {
                    println("!!! Escolha inválida! Tente novamente.")
                    return attackOpponent()
                }
            } else {
                otherPlayer.receivesDirectAttack(monsterCardAttack.attackValue)
                print("\n=> OPONENTE (${otherPlayer.playerName}) recebeu ATAQUE direto de -${monsterCardAttack.attackValue} PONTOS!\n")
            }

            otherPlayer.showBoardMonsterCardsAsOponent()
            monsterCardAttack.stateChangeAllowed = false
        }

        private fun changeMonsterMode() {
            println("\n--> Digite 'voltar' pra retornar ao menu <--")
            print("=> Informe o índice de um MONSTRO no TABULEIRO cujo deseja MODIFICAR o seu MODO: ")
            val index = readlnOrNull() ?: ""

            if (index == "voltar") {
                return
            }

            val indexInt = index.toIntOrNull() ?: -1
            if (!playerOfMoment.changeMonsterCardMode(indexInt)) {
                println("!!! Escolha inválida! Tente novamente.")
                return changeMonsterMode()
            }

            print("\n=> MODO da carta MONSTRO alterado com sucesso!\n")
        }

        private fun giveCardsToPlayers() {
            for (player in players) {
                player.giveCardsToHand(CardsReader.pickRandomCard(cards))
            }
        }

        private fun checkGameEnd(): Boolean {
            if (cards.size == 0) {
                val player1 = players.first()
                val player2 = players.last()
                if (player1.points > player2.points) {
                    println("\n" + "#".repeat(25) + " -> JOGO TEMINOU! ${player1.playerName} WIN!! <- " + "#".repeat(25) + "\n")
                } else if (player1.points < player2.points) {
                    println("\n" + "#".repeat(25) + " -> JOGO TEMINOU! ${player2.playerName} WIN!! <- " + "#".repeat(25) + "\n")
                } else {
                    println("\n" + "#".repeat(25) + " -> JOGO TEMINOU! EMPATE!! <- " + "#".repeat(25) + "\n")
                }

                return false
            }

            for (player in players) {
                if (player.points <= 0) {
                    val playerWin = if (player == players.first()) {
                        players.first()
                    } else {
                        players.last()
                    }

                    println("\n" + "#".repeat(25) + " -> JOGO TEMINOU! ${playerWin.playerName} WIN!! <- " + "#".repeat(25) + "\n")

                    return false
                }
            }

            return true
        }

        private fun allowsMonstersToTrackStatus() {
            for (player in players) {
                player.allowsMonstersToTrackStatus()
            }
        }

        private fun chooseAction() {
            var gameIsRunning = true
            var monsterAdded = false
            var equipAdded = false

            while (gameIsRunning) {
                println("\n" + "#".repeat(25) + " -> RODADA $round - Vez do Jogador: ${playerOfMoment.playerName} <- " + "#".repeat(25) + "\n")
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

                    if (playerOfMoment.existsMonsterToChangeMode()) {
                        println("   e) Alterar o estado de um monstro (ataque/defesa)")
                    }
                }

                println("   f) Terminar seu turno na rodada $round")

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
                            attackOpponent()
                        } else {
                            println("Escolha inválida.")
                        }
                    }
                    "e" -> {
                        if (playerOfMoment.existMonsterCardsOnBoard() && playerOfMoment.existsMonsterToChangeMode()) {
                            changeMonsterMode()
                        } else {
                            println("Escolha inválida.")
                        }
                    }
                    "f" -> {
                        if (playerOfMoment == players.last()) {
                            round++
                            allowsMonstersToTrackStatus()
                            giveCardsToPlayers()
                            println("\n" + "-".repeat(30) + "> RODADA TERMINADA, JOGADORES RECEBEM UMA NOVA CARTA CASO TENHA MENOS DE 10 em mãos, vez do jogador ${playerOfMoment.playerName}")
                        } else {
                            println("\n" + "-".repeat(30) + "> TURNO TERMINADO, vez do jogador ${playerOfMoment.playerName}")
                        }

                        // proximo jogador
                        playerOfMoment = returnOtherPlayer()
                        monsterAdded = false
                        equipAdded = false
                    }
                    else -> {
                        println("Escolha inválida.")
                    }
                }

                gameIsRunning = checkGameEnd()
            }
        }

        fun start() {
            chooseAction()
        }
    }

}