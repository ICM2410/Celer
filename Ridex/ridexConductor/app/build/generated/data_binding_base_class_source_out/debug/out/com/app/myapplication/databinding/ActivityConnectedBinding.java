// Generated by view binder compiler. Do not edit!
package com.app.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public final class ActivityConnectedBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnFinishTrip;

  @NonNull
  public final ImageView btnInfo;

  @NonNull
  public final Button btnStartTrip;

  @NonNull
  public final TextView textViewDistance;

  @NonNull
  public final TextView textViewTimer;

  private ActivityConnectedBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnFinishTrip, @NonNull ImageView btnInfo, @NonNull Button btnStartTrip,
      @NonNull TextView textViewDistance, @NonNull TextView textViewTimer) {
    this.rootView = rootView;
    this.btnFinishTrip = btnFinishTrip;
    this.btnInfo = btnInfo;
    this.btnStartTrip = btnStartTrip;
    this.textViewDistance = textViewDistance;
    this.textViewTimer = textViewTimer;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityConnectedBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityConnectedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_connected, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityConnectedBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnFinishTrip;
      Button btnFinishTrip = ViewBindings.findChildViewById(rootView, id);
      if (btnFinishTrip == null) {
        break missingId;
      }

      id = R.id.btnInfo;
      ImageView btnInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnInfo == null) {
        break missingId;
      }

      id = R.id.btnStartTrip;
      Button btnStartTrip = ViewBindings.findChildViewById(rootView, id);
      if (btnStartTrip == null) {
        break missingId;
      }

      id = R.id.textViewDistance;
      TextView textViewDistance = ViewBindings.findChildViewById(rootView, id);
      if (textViewDistance == null) {
        break missingId;
      }

      id = R.id.textViewTimer;
      TextView textViewTimer = ViewBindings.findChildViewById(rootView, id);
      if (textViewTimer == null) {
        break missingId;
      }

      return new ActivityConnectedBinding((ConstraintLayout) rootView, btnFinishTrip, btnInfo,
          btnStartTrip, textViewDistance, textViewTimer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
