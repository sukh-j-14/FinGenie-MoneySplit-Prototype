package com.fingenie.ui.dashboard

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.fingenie.R
import kotlin.Int
import kotlin.String

public class DashboardFragmentDirections private constructor() {
  private data class ActionDashboardFragmentToExpensesFragment(
    public val groupId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_dashboardFragment_to_expensesFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("groupId", this.groupId)
        return result
      }
  }

  public companion object {
    public fun actionDashboardFragmentToGroupsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_groupsFragment)

    public fun actionDashboardFragmentToExpensesFragment(groupId: String): NavDirections =
        ActionDashboardFragmentToExpensesFragment(groupId)

    public fun actionDashboardFragmentToProfileFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_profileFragment)

    public fun actionDashboardFragmentToSettingsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_settingsFragment)
  }
}
