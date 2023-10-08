package entities

class Card(
    private val name: String,
    private val description: String,
    private var attack: Int,
    private var defense: Int,
    private val type: String
) {
    private var mode: String = "static"
    private var allowModeChange: Boolean = true
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
        get() = allowModeChange
        set(value) {
            allowModeChange = value
        }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendLine("- ".repeat(50))

        builder.appendLine(name)
        builder.append("   ATK: $attack  DEF: $defense")
        builder.appendLine("\n   Modo: $mode")

        if (description.isNotBlank()) {
            builder.appendLine("\n   Descrição:")
            description.lines().forEach { line ->
                builder.appendLine("      $line")
            }
        }

        if (equipment.isNotEmpty()) {
            builder.appendLine("   Equipamentos:")
            equipment.forEachIndexed { index, equipmentCard ->
                builder.appendLine("      ${index + 1}. ${equipmentCard.name} (${equipmentCard.attackValue - attack} ATK, ${equipmentCard.defenseValue - defense} DEF)")
            }
        }

        builder.appendLine("- ".repeat(50))

        return builder.toString()
    }





}