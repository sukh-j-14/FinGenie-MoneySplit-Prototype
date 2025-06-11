package com.fingenie.ui.expenses

import android.os.Bundle
import androidx.navigation.NavDirections
import com.fingenie.R
import kotlin.Int
import kotlin.String

public class ExpensesFragmentDirections private constructor() {
  private data class ActionExpensesFragmentToAddExpenseFragment(
    public val groupId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_expensesFragment_to_addExpenseFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("groupId", this.groupId)
        return result
      }
  }

  private data class ActionExpensesFragmentToExpenseDetailsFragment(
    public val expenseId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_expensesFragment_to_expenseDetailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("expenseId", this.expenseId)
        return result
      }
  }

  public companion object {
    public fun actionExpensesFragmentToAddExpenseFragment(groupId: String): NavDirections =
        ActionExpensesFragmentToAddExpenseFragment(groupId)

    public fun actionExpensesFragmentToExpenseDetailsFragment(expenseId: String): NavDirections =
        ActionExpensesFragmentToExpenseDetailsFragment(expenseId)
  }
}
