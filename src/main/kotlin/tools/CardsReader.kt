package tools

import entities.Card
import java.io.File
import java.io.InputStream

class CardsReader () {

    companion object {
        private lateinit var cards: MutableList<Card>

        fun getCards(): MutableList<Card> {
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
                }.shuffled() as MutableList<Card>
            }

            return cards;
        }

        fun pickFiveRandomCards(deck: MutableList<Card>): MutableList<Card> {
            require(deck.size >= 5) { "O deck deve ter pelo menos 5 cartas para selecionar." }

            val selectedCards = deck.shuffled().take(5)
            deck.removeAll(selectedCards)

            return selectedCards as MutableList<Card>
        }

        fun pickRandomCard(deck: MutableList<Card>): Card {
            require(deck.size >= 1) { "O deck deve ter pelo menos 1 cartas para selecionar." }

            val selectedCard = deck.shuffled().take(1)
            deck.removeAll(selectedCard)

            return selectedCard[0]
        }

    }

}