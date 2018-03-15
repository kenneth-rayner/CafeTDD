import Cafe.{BrewingException, Coffee, GroundCoffee, Water}
import org.scalatest.{MustMatchers, WordSpec}

class CafeSpec extends WordSpec with MustMatchers {


  "Cafe" must {

    "Will return '25' when 25 input" in {

      Cafe.heat(Water(25)) mustEqual Water(25)
    }

    "Will return '20' when 20 input" in {

      Cafe.heat(Water(20)) mustEqual Water(20)
    }
    "Will return '40' when nothing input" in {

      Cafe.heat(Water()) mustEqual Water(40)
    }

    "Will return 'Finished grinding coffee'" in {

      Cafe.grind("Arabica Beans") mustEqual "Finished grinding coffee"
    }
    "Will return 'Milk has been frothed'" in {

      Cafe.frothMilk("WholeMilk") mustEqual "Milk has been frothed"
    }
    "Will return 'Illegal argument exception'when semi skimmed milk is input" in {
      val e = intercept[IllegalArgumentException] {

        Cafe.frothMilk("Semi Skimmed Milk")
      }
      e.getMessage mustEqual "Should use whole milk"
    }
  }
  "Will return a Coffee" in {

    Cafe.brew(Water(40), "Ground Coffee", None) mustEqual Coffee(Water(40), "Ground Coffee", None)
  }

  "Will return 'Brewing exception' when water input is less than 40D" in {
    val e = intercept[BrewingException] {

      Cafe.brew(Water(20), "Ground Coffee", None)
    }
    e.getMessage mustEqual "The water is too cold"
  }

  "Will return a Coffee with milk" in {

    Cafe.brew(Water(50), "Ground Coffee", Some("WholeMilk")) mustEqual Coffee(Water(50), "Ground Coffee", Some("WholeMilk"))
  }
}
