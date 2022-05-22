package com.myGames.PokemonInfoScala

import com.badlogic.gdx.utils.{ Json, JsonReader, JsonWriter }

import java.io.{ FileWriter, IOException, StringWriter }
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.io.Source

object SaveGame {

  def saveAllyPokemon(ally: AllyPokemon): Unit = {
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

    val resultJson: String = jsonObject.prettyPrint(jsonObject.getWriter.getWriter.toString)
    writeToFile(resultJson)
  }

  def loadAllyPokemon: List[AllyPokemon] = {
    val jsonReader = new JsonReader()
    val file = Source.fromFile("/home/mithrandir/bin/gameSaves/save.json")
    val str = file.getLines().mkString
    val base = jsonReader.parse(str)
    file.close()
    val pokemonName = base.getString("name")
    val pokemonType = Type.withName(base.getString("type"))

    val moveSet: List[Option[Move]] = base.get("moveSet").iterator().asScala.map(moveObj => {
      if (moveObj.isEmpty) None
      else {
        val moveName = moveObj.getString("name")
        val moveType = moveObj.getString("type")
        val moveDamage = moveObj.getInt("damage")
        Some(Move(moveName, Type.withName(moveType), moveDamage))
      }
    }).toList

    val level = base.getInt("level")
    val exp = base.getInt("experience")

    List(new AllyPokemon(pokemonName, pokemonType, moveSet, level, exp))
  }

  private def writeToFile(jsonString: String): Unit = {
    try {
      val fileWriter = new FileWriter("/home/mithrandir/bin/gameSaves/save.json")
      fileWriter.write(jsonString)
      fileWriter.close()
    } catch {
      case e: IOException =>
        System.out.println("An error occurred trying to save the game.")
        e.printStackTrace()
    }
  }
}
