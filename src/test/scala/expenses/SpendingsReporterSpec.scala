package expenses

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class SpendingsReporterSpec extends Specification with Mockito {
  "Expense report" should {
    "show a basic report" in {
      val debtStore = mock[DebtStore]
      debtStore.getPeople() returns List("Alice", "Bob", "Chris")
      debtStore.getOwersFor("Alice") returns List("Bob", "Chris")
      debtStore.getOwersFor("Bob") returns List("Alice", "Chris")
      debtStore.getOwersFor("Chris") returns List("Alice", "Bob")
      debtStore.getDebt("Alice", "Bob") returns 24.0
      debtStore.getDebt("Bob", "Alice") returns -24.0
      debtStore.getDebt("Alice", "Chris") returns 0
      debtStore.getDebt("Chris", "Alice") returns 0
      debtStore.getDebt("Bob", "Chris") returns 25.30
      debtStore.getDebt("Chris", "Bob") returns -25.30
      val expenseReport = new SpendingsReporter
      val expectedReport =
        """Alice:
          | owes Bob $24.00
          | owes Chris $0.00
          |
          |Bob:
          | owes Alice ($24.00)
          | owes Chris $25.30
          |
          |Chris:
          | owes Alice $0.00
          | owes Bob ($25.30)""".stripMargin
      expenseReport.showReport(debtStore) shouldEqual expectedReport
    }
  }
}
