package com.fingenie.ui.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.fingenie.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginFragmentToRegisterFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)

    public fun actionLoginFragmentToDashboardFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_dashboardFragment)
  }
}
