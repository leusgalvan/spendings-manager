package expenses

class DebtStore(
  private val debtByPerson: Map[String, Map[String, Double]] = Map.empty
) {
  def getPeople(): List[String] = {
    val payers = debtByPerson.keySet
    val owers = payers.flatMap(
      p => debtByPerson.get(p).map(_.keySet).getOrElse(Set.empty[String])
    )
    val allPeople = payers ++ owers
    allPeople.toList.sorted
  }
  def getOwersFor(payer: String): List[String] = getPeople().filter(_ != payer)
  def getDebt(payer: String, ower: String): Double = {
    def getDebtOpt(payer: String, ower: String): Option[Double] =
      debtByPerson
        .get(payer)
        .flatMap(_.get(ower))
    getDebtOpt(payer, ower).getOrElse(-getDebtOpt(ower, payer).getOrElse(0d))
  }

  def updateDebt(payer: String,
                 ower: String,
                 update: Double => Double): DebtStore = {
    val debtByOwer: Map[String, Double] =
      debtByPerson.getOrElse(payer, Map.empty)
    val currentDebt = debtByOwer.getOrElse(ower, 0d)
    val newDebt = update(currentDebt)
    val newDebtByOwer = debtByOwer + (ower -> newDebt)
    val newDebtByPerson = debtByPerson + (payer -> newDebtByOwer)
    new DebtStore(newDebtByPerson)
  }
  override def toString(): String = debtByPerson.toString()

  def canEqual(other: Any): Boolean = other.isInstanceOf[DebtStore]

  override def equals(other: Any): Boolean = other match {
    case that: DebtStore =>
      (that canEqual this) &&
        debtByPerson == that.debtByPerson
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(debtByPerson)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
