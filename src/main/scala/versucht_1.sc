/**
 * The very first worksheet of scala.
 * The worksheets contains the prototype and test database of the game.
 * In this versucht_1, we will try to create a Suit of cards, and what we can do with the cards
 */

/*
Creating a Suit using flatmap
Flat map will rather create another List.
The data structure of the Suit will be treated
as a List for the simplicity
 */

import scala.util.Random

val Suit = List("Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack").flatMap(number => List("Hearts",
                  "Diamonds","Spades","Clubs").map(suit => s"$number of $suit"))
//count variable to determine the number of cards in the desk.
//val count = Suit.length
println(Suit)
//println(count)
//Since List anthe "alike" collection is immutable, so you need to assign the shuffle to a new variable.
val ShuffleDesk = Random.shuffle(Suit)
println(ShuffleDesk)

val randomCard = ShuffleDesk(Random.nextInt(ShuffleDesk.length))

val PlayerCards = ShuffleDesk.take(13)
val PlayerCards2 = ShuffleDesk.slice(13,25)
val PlayerCards3 = ShuffleDesk.slice(26,38)
val PlayerCards4 = ShuffleDesk.slice(39,52)

println(PlayerCards)
println(PlayerCards2)
println(PlayerCards3)
println(PlayerCards4)



/**
 * The Suit of cards should be constant, that is good, cause our list is Immutable.
 * What change here is the alternative List shuffle cards, Which will be create at the beginning of the game.
 * Now we should somehow divide the cards depending on the players.
 */
