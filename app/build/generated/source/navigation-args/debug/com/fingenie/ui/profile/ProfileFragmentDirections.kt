package com.fingenie.ui.profile

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.fingenie.R

public class ProfileFragmentDirections private constructor() {
  public companion object {
    public fun actionProfileFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_profileFragment_to_loginFragment)
  }
}
