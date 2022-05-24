package com.myGames.PokemonInfoScala

import com.badlogic.gdx.utils.{ Json, JsonReader, JsonWriter }

import java.io.{ FileWriter, IOException, StringWriter }
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.io.Source

object GameSave {
  private val pathToSaveFile = "/home/mithrandir/bin/gameSaves/save.json"

  def saveGame(ally: AllyPokemon): Unit = {
    val resultJson: String = allyPokemonToJson(ally)
    writeToSaveFile(resultJson)
  }

  private def allyPokemonToJson(ally: AllyPokemon): String = {
    val jsonObject: Json = new Json()
    val jsonText = new StringWriter()
    val writer = new JsonWriter(jsonText)
    jsonObject.setOutputType(JsonWriter.OutputType.json)
    jsonObject.setWriter(writer)
    jsonObject.writeObjectStart()
    jsonObject.writeValue("name", ally.name)
    jsonObject.writeValue("type",ally.pokemonType.toString)

    jsonObject.writeArrayStart("moveSet")
    ally.moveSet.foreach(moveOpt => {
      val moveJson: Json = new Json()
      moveJson.setOutputType(JsonWriter.OutputType.json)
      moveJson.setWriter(writer)
      moveJson.writeObjectStart()
      if (moveOpt.nonEmpty) {
        val move = moveOpt.get
        moveJson.writeValue("name", move.name)
        moveJson.writeValue("type", move.moveType.toString)
        moveJson.writeValue("damage", move.damage)
      }
      moveJson.writeObjectEnd()
    })
    jsonObject.writeArrayEnd()

    jsonObject.writeValue("level", ally.level)
    jsonObject.writeValue("experience", ally.exp)
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
