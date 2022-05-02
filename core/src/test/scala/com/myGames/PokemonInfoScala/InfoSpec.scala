package com.myGames.PokemonInfoScala

import com.myGames.PokemonInfo.Type
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InfoSpec extends AnyFlatSpec with Matchers {
  "getEffectiveDamage" should "return the correct values for normal effectiveness" in {
    val defender = Pokemon("pokemon1", Type.NORMAL, Seq.empty)
    val result = Info.getEffectiveDamage("Tackle", defender)
    val expected = (Info.allMoves("Tackle"), "You used Tackle! It's normally effective")
    result shouldBe expected
  }
}