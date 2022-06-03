package com.myGames.PokemonInfoScalaTest

import com.myGames.PokemonInfoScala.{ AllyPokemon, Type }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AllyPokemonSpec extends AnyFlatSpec with Matchers {
  behavior of "AllyPokemon"

  it should "return correct values for default values of level and exp" in {
    val allyPokemon = new AllyPokemon("Dummy", Type.Normal, List.empty)
    (allyPokemon.level, allyPokemon.exp) shouldBe (1, 0)
  }

  it should "return correct values for provided values of level and exp" in {
    val allyPokemon = new AllyPokemon("Dummy", Type.Normal, List.empty, 2, 50)
    (allyPokemon.level, allyPokemon.exp) shouldBe (2, 50)
  }

  it should "stay the same level when not given enough exp" in {
    val allyPokemon = new AllyPokemon("Dummy", Type.Normal, List.empty)
    allyPokemon.gainExp(50)
    (allyPokemon.level, allyPokemon.exp) shouldBe (1, 50)
  }

  it should "level up when given enough exp" in {
    val allyPokemon = new AllyPokemon("Dummy", Type.Normal, List.empty)
    allyPokemon.gainExp(100)
    (allyPokemon.level, allyPokemon.exp) shouldBe (2, 0)
  }
}
