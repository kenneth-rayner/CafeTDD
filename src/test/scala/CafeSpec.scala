import Cafe._
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.{ExecutionContext, Future}

class CafeSpec extends AsyncWordSpec with MustMatchers {

  implicit def ec: ExecutionContext = Cafe.ec

  "Cafe" should {

    "return '20' when 20 input" in {

      val heatedWater: Future[Water] = Cafe.heat(Water(20))
      heatedWater.map { water: Water => assert(water.temperature == 20) }

    }

    "return '25' when 25 input" in {

      val heatedWater: Future[Water] = Cafe.heat(Water(25))
      heatedWater.map { water: Water => assert(water.temperature == 25) }

    }
  }

  "Will return '40' when nothing input" in {

    val heatedWater: Future[Water] = Cafe.heat(Water())
    heatedWater.map { water: Water => assert(water.temperature == 40) }
  }

  "Will return 'Finished grinding coffee'" in {

    val groundBeans: Future[GroundCoffee] = Cafe.grind("Arabica Beans")
    groundBeans.map { beans: GroundCoffee => assert(beans == "Ground Arabica Beans") }
  }

  "Will return 'Grind exception' when wrong coffee beans input" in {

    recoverToSucceededIf[GrindException] {

      Cafe.grind("Baked Beans")

    }
  }

  "Will return 'Milk has been frothed'" in {

    val frothyMilk: Future[FrothedMilk] = Cafe.frothMilk("Whole Milk")
    frothyMilk.map { milk: FrothedMilk => assert(milk == "Frothed Whole Milk") }
  }

  "Will return 'Illegal argument exception'when semi skimmed milk is input" in {
    recoverToSucceededIf[IllegalArgumentException] {

      Cafe.frothMilk("Semi Skimmed Milk")

    }
  }

  "Will return a Coffee" in {

    val brewedCoffee: Future[Coffee] = Cafe.brew(Water(40), "Ground Arabica Beans", None)
    brewedCoffee.map { coffee: Coffee => assert(coffee == Coffee(Water(40), "Ground Arabica Beans")) }
  }

  "Will return 'Brewing exception' when water input is less than 40D" in {
    recoverToSucceededIf[BrewingException] {

      Cafe.brew(Water(20), "Arabica Ground Coffee", None)

    }
  }
  
  "Will return a Coffee with milk" in {

    val brewedCoffee: Future[Coffee] = Cafe.brew(Water(40), "Ground Arabica Beans", Some("Whole Milk"))
    brewedCoffee.map { coffee: Coffee => assert(coffee == Coffee(Water(40), "Ground Arabica Beans", Some("Frothed Whole Milk"))) }
  }
}
