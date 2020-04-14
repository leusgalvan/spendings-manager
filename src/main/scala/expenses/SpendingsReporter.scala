package expenses

import java.util.{Currency, Locale}

import scala.util.{Properties, Try}

class SpendingsReporter {
  private def formatCurrency(value: Double): String = {
    val moneyFormatter = java.text.NumberFormat.getCurrencyInstance(Locale.US)
    moneyFormatter.format(value)
  }

  def showReport(debtStore: DebtStore): String = {
    val nl = Properties.lineSeparator

    def buildDebtRow(payer: String, ower: String): String = {
      s" owes $ower ${formatCurrency(debtStore.getDebt(payer, ower))}"
    }

    def buildDebtString(payer: String): String = {
      val owers = debtStore.getOwersFor(payer)
      val rows = s"$payer:" :: owers.map(p => buildDebtRow(payer, p))
      rows.mkString(nl)
    }

    val people = debtStore.getPeople

    people.map(buildDebtString).mkString(s"$nl$nl")
  }
}
