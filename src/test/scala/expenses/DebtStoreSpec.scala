package expenses

import org.specs2.mutable.Specification

class DebtStoreSpec(val emptyDebtStore: DebtStore, val specName: String)
    extends Specification {
  val debtStore = emptyDebtStore + Debt("Lisa", "Veronica", 2)

  specName should {
    "return people in alphabetical order" in {
      debtStore.getPeople() shouldEqual List("Lisa", "Veronica")
    }

    "get owers for a given payer" in {
      debtStore.getOwersFor("Lisa") shouldEqual List("Veronica")
      debtStore.getOwersFor("Veronica") shouldEqual List("Lisa")
    }

    "get debt between given payer and ower" in {
      debtStore.getDebt("Lisa", "Veronica") shouldEqual 2
      debtStore.getDebt("Veronica", "Lisa") shouldEqual -2
    }

    "update debt for given payer and ower when both already exist" in {
      val updatedDebtStore = debtStore.updateDebt("Lisa", "Veronica", _ + 3)
      updatedDebtStore.getDebt("Lisa", "Veronica") shouldEqual 5
      updatedDebtStore.getDebt("Veronica", "Lisa") shouldEqual -5
    }

    "update debt for given payer and ower when payer does not exist" in {
      val updatedDebtStore = debtStore.updateDebt("Alice", "Veronica", _ + 3)
      updatedDebtStore.getDebt("Alice", "Veronica") shouldEqual 3
      updatedDebtStore.getDebt("Veronica", "Alice") shouldEqual -3
    }

    "update debt for given payer and ower when ower does not exist" in {
      val updatedDebtStore = debtStore.updateDebt("Veronica", "Alice", _ + 3)
      updatedDebtStore.getDebt("Veronica", "Alice") shouldEqual 3
      updatedDebtStore.getDebt("Alice", "Veronica") shouldEqual -3
    }

    "update debt for given payer and ower when neither exist" in {
      val updatedDebtStore = debtStore.updateDebt("Bob", "Alice", _ + 3)
      updatedDebtStore.getDebt("Bob", "Alice") shouldEqual 3
      updatedDebtStore.getDebt("Alice", "Bob") shouldEqual -3
    }
  }
}
