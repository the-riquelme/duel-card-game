package entities

class Card(
    private val name: String,
    private val description: String,
    private var attack: Int,
    private var defense: Int,
    private val type: String
) {
    private var mode: String = "Estático"
    private var allowModeChange: Boolean = true
    private val equipment: MutableList<Card> = mutableListOf()

    val nameValue: String
        get() = name

    val descriptionValue: String
        get() = description

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

    var modeValue: String
        get() = mode
        set(value) {
            mode = value
        }

    var stateChangeAllowed: Boolean
        get() = allowModeChange
        set(value) {
            allowModeChange = value
        }

    val typeValue: String
        get() = type

    val equipmentList: MutableList<Card>
        get() = equipment

    fun addEquipment(equipment: Card) {
        this.attack += equipment.attackValue
        this.defense += equipment.defenseValue
        this.equipment.add(equipment)
    }

}