import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.collection.mutable.Queue


class CardSoccer extends AnyWordSpec {

  "A CardSoccer game" when {
    "initialized" should {
      val game = new CardSoccer()
      "have two players with 26 cards each" in {
        assert(game.player1.cards.length == 26)
        assert(game.player2.cards.length == 26)
      }
      "have an empty playing field" in {
        assert(game.playingField.isEmpty)
      }
      "have a score of 0 for both players" in {
        assert(game.player1Score == 0)
        assert(game.player2Score == 0)
      }
    }

    "during gameplay : " should {

      "handle a debate when attacker's card is the same as defender's card" in {
        val game = new CardSoccer()
        game.setup()
        val attacker = game.player1
        val defender = game.player2
        val initialPlayingFieldSize = game.playingField.size
        val initialDefenderCards = defender.cards.clone()

        game.playingField.clear()
        val sameCard = game.player1.cards.head
        game.player1.cards.dequeue()
        game.player2.cards.dequeue()
        game.playingField.enqueue(new Card(attacker, sameCard.value), new Card(defender, sameCard.value))

        val debateResult = game.debate(attacker, defender)
        if (debateResult.successful) {
          assert(game.playingField.size == initialPlayingFieldSize)
          assert(game.player1.cards.length == 25)
          assert(game.player2.cards.length == initialDefenderCards.length - 1)
        } else {
          assert(game.playingField.size == initialPlayingFieldSize)
          assert(game.player1.cards.length == initialDefenderCards.length)
          assert(game.player2.cards.length == initialDefenderCards.length)
        }
      }
    }


    "during setup" should {
      val game = new CardSoccer()
      game.setup()
      "have 8 cards placed on the playing field (4 from each player)" in {
        assert(game.playingField.size == 8)
      }
      "have exactly 4 cards placed by player 1 on the playing field" in {
        assert(game.playingField.count(_.owner == game.player1) == 4)
      }
      "have exactly 4 cards placed by player 2 on the playing field" in {
        assert(game.playingField.count(_.owner == game.player2) == 4)
      }
    }

    "during gameplay" should {
      val game = new CardSoccer()
      game.setup()
      val attacker = game.player1
      val defender = game.player2
      val initialPlayingFieldSize = game.playingField.size
      val initialDefenderCards = defender.cards.clone()

      "allow player 1 to start attacking with his cards" in {
        val attackResult = game.attack(attacker, defender)
        if (attackResult.successful) {
          assert(game.playingField.size == initialPlayingFieldSize - 1)
          assert(game.player1Score == 1)
          assert(game.player2.cards.length == initialDefenderCards.length - 1)
        } else {
          assert(game.playingField.size == initialPlayingFieldSize)
          assert(game.player1Score == 0)
          assert(game.player2.cards.length == initialDefenderCards.length)
        }
      }

      "allow player 2 to attack after player 1's attack" in {
        val initialPlayingFieldSize = game.playingField.size
        val initialDefenderCards = defender.cards.clone()
        game.attack(defender, attacker)
        assert(game.playingField.size == initialPlayingFieldSize)
        assert(game.player1.cards.length == 26)
        assert(game.player2.cards.length == initialDefenderCards.length)
      }
    }
  }
}
