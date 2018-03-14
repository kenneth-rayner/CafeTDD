object Cafe {


  def heat (temperature: Double): Double = {
    val increaseHeat = 40 - temperature

    temperature + increaseHeat

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
