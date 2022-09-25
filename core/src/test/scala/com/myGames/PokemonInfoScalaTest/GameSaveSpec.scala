package com.myGames.PokemonInfoScalaTest

import com.myGames.PokemonInfoScala.Potions.Potions
import com.myGames.PokemonInfoScala.{ AllyPokemon, GameSave, Info, Potions }
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class GameSaveSpec extends AnyFlatSpec with Matchers with PrivateMethodTester {
  val allyPokemonToJson: PrivateMethod[String] = PrivateMethod[String]('allyPokemonToJson)

  behavior of "allyPokemonToJson"

  it should "return the correct values" in {
    val pokemon: ArrayBuffer[AllyPokemon] = ArrayBuffer(Info.starterPokemon("Charmander"))
    val potions: mutable.Map[Potions, Int] = mutable.Map(Potions.Potion -> 5)

    val result = GameSave invokePrivate allyPokemonToJson(pokemon, potions)
    val expected = Source.fromResource("dummySave.json").getLines.mkString
    result.filterNot(_.isWhitespace) shouldBe expected.filterNot(_.isWhitespace)
  }
}
