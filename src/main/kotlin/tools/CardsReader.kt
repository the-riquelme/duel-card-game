package tools

import model.Card
import java.io.File
import java.io.InputStream

class CardsReader (){

    companion object{
        private lateinit var cards:List<Card>

        fun getCartas():List<Card> {
            if(!::cards.isInitialized){
                /*aqui deve ocorrer a carga das cartas
                *
                *Sugiro usar a função map para transformar as String recuperadas do arquivo em objetos do tipo carta
                */
                //cartas = lerCartasCSV()
                println(lerCartasCSV())
            }

            return cards.map { it }  //retorna uma replica das cartas
        }

        fun lerCartasCSV():List<String> {
            val streamDados:InputStream = File("cartas.csv").inputStream()
            val leitorStream = streamDados.bufferedReader()
            return leitorStream.lineSequence()
                .filter { it.isNotBlank() }.toList()
        }
    }



}