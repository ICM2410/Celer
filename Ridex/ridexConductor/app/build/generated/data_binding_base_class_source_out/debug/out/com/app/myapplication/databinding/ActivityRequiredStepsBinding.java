// Generated by view binder compiler. Do not edit!
package com.app.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.app.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRequiredStepsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnBack;

  @NonNull
  public final LinearLayout btnGotoCertificateInfo;

  @NonNull
  public final LinearLayout btnGotoEnsuranceInfo;

  @NonNull
  public final LinearLayout btnGotoLicenseInfo;

  @NonNull
  public final LinearLayout btnGotoProfilePhotoInfo;

  @NonNull
  public final Button btnNextGotoProfilePhotoInfo;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final LinearLayout linearLayout4;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView titleCashout;

  private ActivityRequiredStepsBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnBack,
      @NonNull LinearLayout btnGotoCertificateInfo, @NonNull LinearLayout btnGotoEnsuranceInfo,
      @NonNull LinearLayout btnGotoLicenseInfo, @NonNull LinearLayout btnGotoProfilePhotoInfo,
      @NonNull Button btnNextGotoProfilePhotoInfo, @NonNull LinearLayout linearLayout2,
      @NonNull LinearLayout linearLayout4, @NonNull TextView textView2,
      @NonNull TextView titleCashout) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnGotoCertificateInfo = btnGotoCertificateInfo;
    this.btnGotoEnsuranceInfo = btnGotoEnsuranceInfo;
    this.btnGotoLicenseInfo = btnGotoLicenseInfo;
    this.btnGotoProfilePhotoInfo = btnGotoProfilePhotoInfo;
    this.btnNextGotoProfilePhotoInfo = btnNextGotoProfilePhotoInfo;
    this.linearLayout2 = linearLayout2;
    this.linearLayout4 = linearLayout4;
    this.textView2 = textView2;
    this.titleCashout = titleCashout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRequiredStepsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRequiredStepsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_required_steps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRequiredStepsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      Button btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnGotoCertificateInfo;
      LinearLayout btnGotoCertificateInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnGotoCertificateInfo == null) {
        break missingId;
      }

      id = R.id.btnGotoEnsuranceInfo;
      LinearLayout btnGotoEnsuranceInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnGotoEnsuranceInfo == null) {
        break missingId;
      }

      id = R.id.btnGotoLicenseInfo;
      LinearLayout btnGotoLicenseInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnGotoLicenseInfo == null) {
        break missingId;
      }

      id = R.id.btnGotoProfilePhotoInfo;
      LinearLayout btnGotoProfilePhotoInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnGotoProfilePhotoInfo == null) {
        break missingId;
      }

      id = R.id.btnNextGotoProfilePhotoInfo;
      Button btnNextGotoProfilePhotoInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnNextGotoProfilePhotoInfo == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.linearLayout4;
      LinearLayout linearLayout4 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout4 == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.titleCashout;
      TextView titleCashout = ViewBindings.findChildViewById(rootView, id);
      if (titleCashout == null) {
        break missingId;
      }

      return new ActivityRequiredStepsBinding((ConstraintLayout) rootView, btnBack,
          btnGotoCertificateInfo, btnGotoEnsuranceInfo, btnGotoLicenseInfo, btnGotoProfilePhotoInfo,
          btnNextGotoProfilePhotoInfo, linearLayout2, linearLayout4, textView2, titleCashout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
