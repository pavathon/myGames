package com.myGames.PokemonInfoScalaTest

import com.myGames.PokemonInfoScala.{ AllyPokemon, GameSave, Info }
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.io.Source

class GameSaveSpec extends AnyFlatSpec with Matchers with PrivateMethodTester {
  val allyPokemonToJson: PrivateMethod[String] = PrivateMethod[String]('allyPokemonToJson)

  behavior of "allyPokemonToJson"

  it should "return the correct values" in {
    val allyPokemon: AllyPokemon = Info.starterPokemon("Charmander")
    val result = GameSave invokePrivate allyPokemonToJson(allyPokemon)
    val expected = Source.fromResource("dummySave.json").getLines.mkString
    result.filterNot(_.isWhitespace) shouldBe expected.filterNot(_.isWhitespace)
  }
}
