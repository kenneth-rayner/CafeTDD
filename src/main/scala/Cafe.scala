object Cafe {
  case class Water(temperature: Double = 0)

  type CoffeeBeans = String
  type Milk = String
  type GroundCoffee = String
  type FrothedMilk = String
  case class Coffee (water:Water, groundCoffee: GroundCoffee,milk:Option[Milk]=None){
    def addMilk (milk:String): Coffee = this.copy(water,groundCoffee,Some(milk))
  }

  case class BrewingException(msg: String) extends Exception(msg)

  def heat (water: Water): Water = {

    water.temperature match {
      case t if t == 0 =>water.copy(40)
      case _  => water.copy(water.temperature)
    }
  }

  def grind(beans : CoffeeBeans) : GroundCoffee = {

    beans match {
      case b if b == "Arabica Beans" => "Finished grinding coffee"
      case _ => "Those are not Arabica Beans"
    }
  }

  def frothMilk(milk: Milk): FrothedMilk = {

    milk match {
      case b if b == "WholeMilk" => "Milk has been frothed"
      case  _ => throw new IllegalArgumentException ("Should use whole milk")
    }
  }
  def brew(water: Water, coffee: GroundCoffee, milk : Option[Milk]): Coffee = {

    milk match {
      case _ if water.temperature < 40 => throw new BrewingException("The water is too cold")
      case m if m.isDefined => println(s"You have brewed the following Coffee at ${water.temperature-5} degrees with $milk")
        Coffee(water, coffee, milk)
      case _ => println(s"You have brewed	the following	coffee:	Coffee at ${water.temperature} degrees without	milk")
        Coffee(water, coffee)
      }
    }

  def prepareCoffee() {



  }

}