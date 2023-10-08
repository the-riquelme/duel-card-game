package tools

import entities.Card
import java.io.File
import java.io.InputStream

class CardsReader () {

    companion object {
        private lateinit var cards: List<Card>

        fun getCards(): List<Card> {
            if (!::cards.isInitialized) {
                val streamData:InputStream = File("cartas.csv").inputStream()
                val data: List<String> = streamData.bufferedReader().lineSequence()
                    .filter { it.isNotBlank() }.toList()

                cards = data.map {
                    val cardData = it.split(";")
                    Card(
                        cardData[0],
                        cardData[1],
                        cardData[2].toInt(),
                        cardData[3].toInt(),
                        cardData[4]
                    )
                }
            }

            return cards;
        }
    }

}