package com.fingenie.ui.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.fingenie.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterFragmentToDashboardFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_registerFragment_to_dashboardFragment)
  }
}
