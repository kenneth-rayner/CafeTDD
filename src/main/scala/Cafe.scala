import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

object Cafe extends App {

  implicit val ec : ExecutionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

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
    println("Finished heating water")
    water.temperature match {
      case t if t == 0 => water.copy(40)
      case _ => water.copy(water.temperature)
    }
  }

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {

    beans match {
      case b if b == "Arabica Beans" => println("Started grinding")
        println("Finished grinding")
        "GroundCoffee"
      case _ => throw GrindException("Should use Arabica Beans")
    }
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {

    milk match {
      case b if b == "Whole Milk" => println("Milk frothing system engaged")
        println("Shutting down milk frothing system")
        "Whole Milk"
      case _ => throw new IllegalArgumentException("Should use Whole Milk")
    }
  }

  def brew(water: Water, coffee: GroundCoffee, milk: Option[Milk] = None): Future[Coffee] = Future {

    (water.temperature, milk) match {
      case (t,_) if t < 40 => throw BrewingException("The water is too cold")
      case (_,m) if m.isDefined => println(s"You have brewed the following Coffee at ${water.temperature-5} degrees with ${milk.get}")
        Coffee(water, coffee, milk).addMilk("Whole Milk")
      case _ => println(s"You have brewed	the following Coffee at ${water.temperature} degrees without milk")
        Coffee(water, coffee)
    }
  }

  def prepareCoffee(beans: String, temp: Double, milk: Option[String] = None): Future[Coffee] = {

    val groundCoffee = grind(beans)
    val heatedWater = heat(Water(temp))

    milk match {

      case _ if milk.isDefined => val frothyMilk = frothMilk(milk.get)
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



  //prepareCoffee("Arabica Beans", 40)
  prepareCoffee("Arabica Beans", 40, Some("Whole Milk"))
  //prepareCoffee("Baked Beans", 50)
  //prepareCoffee("Arabica Beans", 10)
  //prepareCoffee("Arabica Beans", 40, Some("Semi Skimmed Milk"))
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