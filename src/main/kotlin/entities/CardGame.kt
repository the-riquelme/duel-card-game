package entities

class CardGame {

    companion object {
        private var round: Int = 1
        private var players: List<Player>
        private var playerOfMoment: Player

        init {
            println("#".repeat(25) + " -> Bem-vindo ao Duel Card Game <- " + "#".repeat(25) + "\n")
            players = playersEntry()
            playerOfMoment = players.first()
        }

        private fun playersEntry(): List<Player> {
            val players = mutableListOf<Player>()

            for (i in 1..2) {
                print("Informe o nome do jogador $i: ")
                val name = readlnOrNull() ?: ""
                players.add(Player(name))
            }

            return players
        }

        fun start() {
            println("\n${players.first().playerName}, ${players.last().playerName}")
        }
    }

}