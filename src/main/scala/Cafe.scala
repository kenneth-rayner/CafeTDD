object Cafe extends App {
  case class Water(temperature: Double = 0)

  type CoffeeBeans = String
  type Milk = String
  type GroundCoffee = String
  type FrothedMilk = String

  case class Coffee (water:Water, groundCoffee: GroundCoffee,milk:Option[Milk]=None){
    def addMilk (milk:String): Coffee = this.copy(water,groundCoffee,Some(milk))
  }

  case class BrewingException(msg: String) extends Exception(msg)
  case class GrindException(msg: String) extends Exception(msg)

  def heat(water: Water): Water = {

    water.temperature match {
      case t if t == 0 =>water.copy(40)
      case _  => water.copy(water.temperature)
    }
  }

  def grind(beans : CoffeeBeans) : GroundCoffee = {

    beans match {
      case b if b == "Arabica Beans" => "GroundCoffee"
      case _ => throw new GrindException("Should use Arabica Beans")
    }
  }

  def frothMilk(milk: Milk): FrothedMilk = {

    milk match {
      case b if b == "Whole Milk" => "Milk has been frothed"
      case  _ => throw new IllegalArgumentException ("Should use Whole Milk")
    }
  }
  def brew(water: Water, coffee: GroundCoffee, milk : Option[Milk] = None): Coffee = {

    milk match {
      case _ if water.temperature < 40 => throw new BrewingException("The water is too cold")
      case m if m.isDefined => println(s"You have brewed the following Coffee at ${water.temperature-5} degrees with ${milk.get}")
        Coffee(water, coffee, milk).addMilk("Whole Milk")
      case _ => println(s"You have brewed	the following Coffee at ${water.temperature} degrees without milk")
        Coffee(water, coffee)
      }
    }

  def prepareCoffee(beans: String, temp: Double, milk: Option[String] = None) : Coffee = {

    val ground = grind(beans)
    val water = heat(Water(temp))

    milk match {
      case m if m.isDefined => frothMilk(milk.get)
        brew(water, ground, Some(milk.get))
      case _ => brew(water, ground)
    }
  }
  prepareCoffee("Arabica Beans", 40)
  prepareCoffee("Arabica Beans", 40, Some("Whole Milk"))
  //prepareCoffee("Baked Beans", 50)
  //prepareCoffee("Arabica Beans", 10)
  //prepareCoffee("Arabica Beans", 40, Some("Semi Skimmed Milk"))
}