import org.scalatest.{MustMatchers, WordSpec}

class CafeSpec extends WordSpec with MustMatchers{


  "Cafe" must {

    "Will return '40' when 25 input" in {

      Cafe.heat(25) mustEqual 40
    }

    "Will return 'Finished grinding coffee'" in {

      Cafe.grind("Arabica Beans") mustEqual "Finished grinding coffee"
    }

  }

}
