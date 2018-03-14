import org.scalatest.{MustMatchers, WordSpec}

class CafeSpec extends WordSpec with MustMatchers{


  "Cafe" must {

    "Will return '40' when w=20 input" in {

      Cafe.heat(25) mustEqual 40
    }

  }

}
