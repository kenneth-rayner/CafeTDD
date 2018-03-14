import org.scalatest.{MustMatchers, WordSpec}

class CafeSpec extends WordSpec with MustMatchers{


  "Cafe" must {

    "Will return '40' when 25 input" in {

      Cafe.heat(25) mustEqual 40
    }

    "Will return 'Finished grinding coffee'" in {

      Cafe.grind("Arabica Beans") mustEqual "Finished grinding coffee"
    }
    "Will return 'Milk has been frothed'" in {

      Cafe.frothMilk("WholeMilk") mustEqual "Milk has been frothed"
    }
    "Will return 'Coffee has been brewed" in {
      Cafe.brew("Water","Ground Coffee") mustEqual "Coffee has been brewed"
    }
  }

}
