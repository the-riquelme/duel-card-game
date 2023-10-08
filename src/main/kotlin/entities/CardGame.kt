package entities

class CardGame {

    companion object {
        private var round: Int = 1
        private lateinit var players: List<Player>
        private lateinit var playerOfMoment: Player
    }

}