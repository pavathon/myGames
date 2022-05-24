package com.myGames.PokemonInfoScalaTest

import com.myGames.PokemonInfoScala.{ Info, Pokemon, Type }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class InfoSpec extends AnyFlatSpec with Matchers {
  behavior of "getEffectiveDamage"

  it should "be super effective" in {
    val defender: Pokemon = Pokemon("Dummy", Type.Grass, List.empty)
    val result: (Float, String) = Info.getEffectiveDamage("Ember", defender)
    val expected = (40.0f, "You used Ember! It's super effective!")
    result shouldBe expected
  }

  it should "be normally effective" in {
    val defender: Pokemon = Pokemon("Dummy", Type.Normal, List.empty)
    val result: (Float, String) = Info.getEffectiveDamage("Tackle", defender)
    val expected = (10.0f, "You used Tackle! It's normally effective.")
    result shouldBe expected
  }

  it should "not very effective" in {
    val defender: Pokemon = Pokemon("Dummy", Type.Fire, List.empty)
    val result: (Float, String) = Info.getEffectiveDamage("Razor Leaf", defender)
    val expected = (10.0f, "You used Razor Leaf! It's not very effective.")
    result shouldBe expected
  }

  it should "have no effect" in {
    val defender: Pokemon = Pokemon("Dummy", Type.Ghost, List.empty)
    val result: (Float, String) = Info.getEffectiveDamage("Tackle", defender)
    val expected = (0, "You used Tackle! It did nothing.")
    result shouldBe expected
  }
}