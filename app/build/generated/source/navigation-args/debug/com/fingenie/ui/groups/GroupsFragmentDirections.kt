package com.fingenie.ui.groups

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.fingenie.R
import kotlin.Int
import kotlin.String

public class GroupsFragmentDirections private constructor() {
  private data class ActionGroupsFragmentToManageMembersFragment(
    public val groupId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_groupsFragment_to_manageMembersFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("groupId", this.groupId)
        return result
      }
  }

  public companion object {
    public fun actionGroupsFragmentToAddGroupFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_groupsFragment_to_addGroupFragment)

    public fun actionGroupsFragmentToManageMembersFragment(groupId: String): NavDirections =
        ActionGroupsFragmentToManageMembersFragment(groupId)
  }
}
