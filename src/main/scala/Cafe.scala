import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Random, Success}

object Cafe extends App {

  implicit def ec : ExecutionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

  type CoffeeBeans = String
  type Milk = String
  type GroundCoffee = String
  type FrothedMilk = String
  case class Water(temperature: Double = 0)
  case class BrewingException(msg: String) extends Exception(msg)
  case class GrindException(msg: String) extends Exception(msg)
  case class Coffee(water: Water, groundCoffee: GroundCoffee, milk: Option[Milk] = None) {
    def addMilk(milk: String): Coffee = this.copy(water, groundCoffee, Some(milk))
  }

  def heat(water: Water): Future[Water] = Future {
    println("Heating water")
    Thread.sleep(Random.nextInt(2000))
    println("Finished heating water")
    water.temperature match {

      case t if t == 0 => water.copy(40)
      case _ => water.copy(water.temperature)
    }
  }

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {

    beans match {
      case b if b == "Arabica Beans" => println("Started grinding")
        Thread.sleep(Random.nextInt(2000))
        println("Finished grinding")
        "Ground Arabica Beans"
      case _ => throw GrindException("Should use Arabica Beans")
    }
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {

    milk match {
      case b if b == "Whole Milk" => println("Milk frothing system engaged")
        Thread.sleep(Random.nextInt(2000))
        println("Shutting down milk frothing system")
        "Frothed Whole Milk"
      case _ => throw new IllegalArgumentException("Should use Whole Milk")
    }
  }

  def brew(water: Water, coffee: GroundCoffee, milk: Option[Milk] = None): Future[Coffee] = Future {

    (water.temperature, milk) match {
      case (t,_) if t < 40 => throw BrewingException("The water is too cold")
      case (_,m) if m.isDefined => println(s"You have brewed the following Coffee at ${water.temperature-5} degrees with ${milk.get}")
        Thread.sleep(Random.nextInt(2000))
        Coffee(water, coffee, milk).addMilk("Frothed Whole Milk")
      case _ => println(s"You have brewed	the following Coffee at ${water.temperature} degrees without Milk")
        Thread.sleep(Random.nextInt(2000))
        Coffee(water, coffee)
    }
  }

  def prepareCoffee(beans: String, temp: Double, milk: Option[String] = None): Future[Coffee] = {

    val groundCoffee = grind(beans)
    val heatedWater = heat(Water(temp))
    val frothyMilk = frothMilk(milk.getOrElse(""))

    milk match {

      case _ if milk.isDefined =>
        for {
          froth <- frothyMilk
          ground <- groundCoffee
          water <- heatedWater
          coffee: Coffee <- brew(water, ground, Some(froth))
        } yield coffee
      case _ =>
        for {
          ground <- groundCoffee
          water <- heatedWater
          coffee: Coffee <- brew(water, ground)
        } yield coffee

    }

  }

  val completed = prepareCoffee("Arabica Beans", 40, Some("Whole Milk"))
  completed onComplete {
    case Success(c) => c
    case Failure(e) => println(e.getMessage)

  }
}













// potential alternative to two for loops
//
//    frothMilk(milk.getOrElse("")) map {
//      m =>
//        // brew with milk?
//    } recover {
//      case e : IllegalArgumentException =>
//        // no milk
//        // brew without milk?
//    }