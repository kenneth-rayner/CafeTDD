

object Cafe {
  case class Water (temperature: Double = 0D)

  def heat (water: Water, temperature: Double = 40D): Water = {

    water.copy(temperature)

  }

  def grind(beans : String) : String = {

    "Finished grinding coffee"

  }
  def frothMilk(milk: String): String = {
    "Milk has been frothed"
  }
  def brew(water:String, coffee: String): String = {
    "Coffee has been brewed"
  }
}
