package entities

class Card(
    private val name: String,
    private val description: String,
    private var attack: Int,
    private var defense: Int,
    private val type: String,
    private var mode: String,
    private var allowStateChange: Boolean
) {
    private val equipment: MutableList<Card> = mutableListOf()

    var attackValue: Int
        get() = attack
        set(value) {
            if (value >= 0) {
                attack = value
            } else {
                throw IllegalArgumentException("O valor de 'attack' deve ser não negativo.")
            }
        }

    var defenseValue: Int
        get() = defense
        set(value) {
            if (value >= 0) {
                defense = value
            } else {
                throw IllegalArgumentException("O valor de 'defense' deve ser não negativo.")
            }
        }

    var cardMode: String
        get() = mode
        set(value) {
            mode = value
        }

    var stateChangeAllowed: Boolean
        get() = allowStateChange
        set(value) {
            allowStateChange = value
        }

}