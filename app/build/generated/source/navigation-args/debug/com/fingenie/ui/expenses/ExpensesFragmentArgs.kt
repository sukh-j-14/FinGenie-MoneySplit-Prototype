package com.fingenie.ui.expenses

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ExpensesFragmentArgs(
  public val groupId: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("groupId", this.groupId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("groupId", this.groupId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ExpensesFragmentArgs {
      bundle.setClassLoader(ExpensesFragmentArgs::class.java.classLoader)
      val __groupId : String?
      if (bundle.containsKey("groupId")) {
        __groupId = bundle.getString("groupId")
        if (__groupId == null) {
          throw IllegalArgumentException("Argument \"groupId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"groupId\" is missing and does not have an android:defaultValue")
      }
      return ExpensesFragmentArgs(__groupId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): ExpensesFragmentArgs {
      val __groupId : String?
      if (savedStateHandle.contains("groupId")) {
        __groupId = savedStateHandle["groupId"]
        if (__groupId == null) {
          throw IllegalArgumentException("Argument \"groupId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"groupId\" is missing and does not have an android:defaultValue")
      }
      return ExpensesFragmentArgs(__groupId)
    }
  }
}
