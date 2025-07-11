// Generated by view binder compiler. Do not edit!
package com.fingenie.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.fingenie.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAddGroupBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final MaterialButton buttonCreateGroup;

  @NonNull
  public final TextInputEditText editTextGroupDescription;

  @NonNull
  public final TextInputEditText editTextGroupName;

  @NonNull
  public final TextInputLayout textInputLayoutGroupDescription;

  @NonNull
  public final TextInputLayout textInputLayoutGroupName;

  private FragmentAddGroupBinding(@NonNull NestedScrollView rootView,
      @NonNull MaterialButton buttonCreateGroup,
      @NonNull TextInputEditText editTextGroupDescription,
      @NonNull TextInputEditText editTextGroupName,
      @NonNull TextInputLayout textInputLayoutGroupDescription,
      @NonNull TextInputLayout textInputLayoutGroupName) {
    this.rootView = rootView;
    this.buttonCreateGroup = buttonCreateGroup;
    this.editTextGroupDescription = editTextGroupDescription;
    this.editTextGroupName = editTextGroupName;
    this.textInputLayoutGroupDescription = textInputLayoutGroupDescription;
    this.textInputLayoutGroupName = textInputLayoutGroupName;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAddGroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAddGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_add_group, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAddGroupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonCreateGroup;
      MaterialButton buttonCreateGroup = ViewBindings.findChildViewById(rootView, id);
      if (buttonCreateGroup == null) {
        break missingId;
      }

      id = R.id.editTextGroupDescription;
      TextInputEditText editTextGroupDescription = ViewBindings.findChildViewById(rootView, id);
      if (editTextGroupDescription == null) {
        break missingId;
      }

      id = R.id.editTextGroupName;
      TextInputEditText editTextGroupName = ViewBindings.findChildViewById(rootView, id);
      if (editTextGroupName == null) {
        break missingId;
      }

      id = R.id.textInputLayoutGroupDescription;
      TextInputLayout textInputLayoutGroupDescription = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayoutGroupDescription == null) {
        break missingId;
      }

      id = R.id.textInputLayoutGroupName;
      TextInputLayout textInputLayoutGroupName = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayoutGroupName == null) {
        break missingId;
      }

      return new FragmentAddGroupBinding((NestedScrollView) rootView, buttonCreateGroup,
          editTextGroupDescription, editTextGroupName, textInputLayoutGroupDescription,
          textInputLayoutGroupName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
