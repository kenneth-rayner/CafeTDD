object Cafe {
  case class Water(temperature: Double = 0)

  type CoffeeBeans = String
  type Milk = String
  type GroundCoffee = String
  type FrothedMilk = String
  type Coffee = String

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
  def brew(water: Water, coffee: GroundCoffee): Coffee = {

    water.temperature match {
      case t if t < 40 => throw new BrewingException("The water is too cold")
      case _ => "Coffee has been brewed"
    }
  }
}