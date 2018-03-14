

object Cafe {
  case class Water (temperature: Double = 0D)

  type CoffeeBeans = String
  type Milk = String
  type GroundCoffee = String
  type FrothedMilk = String
  type Coffee = String

  def heat (water: Water, temperature: Double = 40D): Water = {

    water.copy(temperature)

  }

  def grind(beans : CoffeeBeans) : GroundCoffee = {

    beans match {
      case b if b == "Arabica Beans" => "Finished grinding coffee"
      case _ => "Those are not Arabica Beans"
    }

  }
  def frothMilk(milk: Milk): FrothedMilk = {
    "Milk has been frothed"
  }
  def brew(water: Water, coffee: GroundCoffee): Coffee = {
    "Coffee has been brewed"
  }
}
