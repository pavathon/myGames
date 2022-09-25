package com.myGames.PokemonInfoScala

import com.badlogic.gdx.utils.{ Json, JsonReader, JsonWriter }
import com.myGames.PokemonInfoScala.Potions.Potions

import java.io.{ FileWriter, IOException, StringWriter }
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object GameSave {
  private val pathToSaveFile = "/home/mithrandir/bin/gameSaves/save.json"

  def saveGame(): Unit = {
    val resultJson: String = allyPokemonToJson(Player.pokemonBag, Player.potionsBag)
    writeToSaveFile(resultJson)
  }

  private def allyPokemonToJson(
    pokemon: ArrayBuffer[AllyPokemon],
    potions: mutable.Map[Potions, Int]
  ): String = {
    val jsonObject: Json = new Json()
    val jsonText = new StringWriter()
    val writer = new JsonWriter(jsonText)
    jsonObject.setOutputType(JsonWriter.OutputType.json)
    jsonObject.setWriter(writer)
    jsonObject.writeObjectStart()

      jsonObject.writeArrayStart("pokemon")
        jsonObject.writeObjectStart()

          jsonObject.writeValue("name", pokemon.head.name)
          jsonObject.writeValue("type",pokemon.head.pokemonType.toString)

          jsonObject.writeArrayStart("moveSet")
            pokemon.head.moveSet.foreach { moveOpt =>
              jsonObject.writeObjectStart()
                moveOpt.foreach { move =>
                  jsonObject.writeValue("name", move.name)
                  jsonObject.writeValue("type", move.moveType.toString)
                  jsonObject.writeValue("damage", move.damage)
                }
              jsonObject.writeObjectEnd()
            }
          jsonObject.writeArrayEnd()

          jsonObject.writeValue("level", pokemon.head.level)
          jsonObject.writeValue("experience", pokemon.head.exp)

        jsonObject.writeObjectEnd()
      jsonObject.writeArrayEnd()

      potions.get(Potions.Potion).foreach(jsonObject.writeValue("potion", _))

    jsonObject.writeObjectEnd()

    jsonObject.prettyPrint(jsonObject.getWriter.getWriter.toString)
  }

  def loadSave: List[AllyPokemon] = {
    val jsonReader = new JsonReader()
    val jsonString = readFromSaveFile
    val base = jsonReader.parse(jsonString)
    val pokemonName = base.getString("name")
    val pokemonType = Type.withName(base.getString("type"))

    val moveSet: List[Option[Move]] = base.get("moveSet").iterator().asScala.map { moveObj =>
      if (moveObj.isEmpty) None
      else {
        val moveName: String = moveObj.getString("name")
        val moveType: Type.Value = Type.withName(moveObj.getString("type"))
        val moveDamage: Int = moveObj.getInt("damage")
        Some(Move(moveName, moveType, moveDamage))
      }
    }.toList

    val level = base.getInt("level")
    val exp = base.getInt("experience")

    List(new AllyPokemon(pokemonName, pokemonType, moveSet, level, exp))
  }

  private def readFromSaveFile: String = {
    val saveFile = Source.fromFile(pathToSaveFile)
    val jsonString = saveFile.getLines().mkString
    saveFile.close()
    jsonString
  }

  private def writeToSaveFile(jsonString: String): Unit = {
    try {
      val saveFileWriter = new FileWriter(pathToSaveFile)
      saveFileWriter.write(jsonString)
      saveFileWriter.close()
    } catch {
      case e: IOException =>
        System.out.println("An error occurred trying to save the game.")
        e.printStackTrace()
    }
  }
}
