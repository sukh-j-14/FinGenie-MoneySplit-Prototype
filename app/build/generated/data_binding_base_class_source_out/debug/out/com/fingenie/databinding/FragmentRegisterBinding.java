// Generated by view binder compiler. Do not edit!
package com.fingenie.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.fingenie.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton buttonRegister;

  @NonNull
  public final TextInputEditText editTextEmail;

  @NonNull
  public final TextInputEditText editTextName;

  @NonNull
  public final TextInputEditText editTextPassword;

  @NonNull
  public final TextInputLayout textInputLayoutEmail;

  @NonNull
  public final TextInputLayout textInputLayoutName;

  @NonNull
  public final TextInputLayout textInputLayoutPassword;

  private FragmentRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton buttonRegister, @NonNull TextInputEditText editTextEmail,
      @NonNull TextInputEditText editTextName, @NonNull TextInputEditText editTextPassword,
      @NonNull TextInputLayout textInputLayoutEmail, @NonNull TextInputLayout textInputLayoutName,
      @NonNull TextInputLayout textInputLayoutPassword) {
    this.rootView = rootView;
    this.buttonRegister = buttonRegister;
    this.editTextEmail = editTextEmail;
    this.editTextName = editTextName;
    this.editTextPassword = editTextPassword;
    this.textInputLayoutEmail = textInputLayoutEmail;
    this.textInputLayoutName = textInputLayoutName;
    this.textInputLayoutPassword = textInputLayoutPassword;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonRegister;
      MaterialButton buttonRegister = ViewBindings.findChildViewById(rootView, id);
      if (buttonRegister == null) {
        break missingId;
      }

      id = R.id.editTextEmail;
      TextInputEditText editTextEmail = ViewBindings.findChildViewById(rootView, id);
      if (editTextEmail == null) {
        break missingId;
      }

      id = R.id.editTextName;
      TextInputEditText editTextName = ViewBindings.findChildViewById(rootView, id);
      if (editTextName == null) {
        break missingId;
      }

      id = R.id.editTextPassword;
      TextInputEditText editTextPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextPassword == null) {
        break missingId;
      }

      id = R.id.textInputLayoutEmail;
      TextInputLayout textInputLayoutEmail = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayoutEmail == null) {
        break missingId;
      }

      id = R.id.textInputLayoutName;
      TextInputLayout textInputLayoutName = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayoutName == null) {
        break missingId;
      }

      id = R.id.textInputLayoutPassword;
      TextInputLayout textInputLayoutPassword = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayoutPassword == null) {
        break missingId;
      }

      return new FragmentRegisterBinding((ConstraintLayout) rootView, buttonRegister, editTextEmail,
          editTextName, editTextPassword, textInputLayoutEmail, textInputLayoutName,
          textInputLayoutPassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
