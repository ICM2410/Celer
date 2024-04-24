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
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.app.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class BottomSheetOffert2Binding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnConfirmRequest;

  @NonNull
  public final TextView textDestiny;

  @NonNull
  public final TextView textOrigin;

  @NonNull
  public final TextView textPrice;

  @NonNull
  public final TextView textTimeDistance;

  private BottomSheetOffert2Binding(@NonNull LinearLayout rootView, @NonNull Button btnCancel,
      @NonNull Button btnConfirmRequest, @NonNull TextView textDestiny,
      @NonNull TextView textOrigin, @NonNull TextView textPrice,
      @NonNull TextView textTimeDistance) {
    this.rootView = rootView;
    this.btnCancel = btnCancel;
    this.btnConfirmRequest = btnConfirmRequest;
    this.textDestiny = textDestiny;
    this.textOrigin = textOrigin;
    this.textPrice = textPrice;
    this.textTimeDistance = textTimeDistance;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetOffert2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetOffert2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_offert2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetOffert2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCancel;
      Button btnCancel = ViewBindings.findChildViewById(rootView, id);
      if (btnCancel == null) {
        break missingId;
      }

      id = R.id.btnConfirmRequest;
      Button btnConfirmRequest = ViewBindings.findChildViewById(rootView, id);
      if (btnConfirmRequest == null) {
        break missingId;
      }

      id = R.id.textDestiny;
      TextView textDestiny = ViewBindings.findChildViewById(rootView, id);
      if (textDestiny == null) {
        break missingId;
      }

      id = R.id.textOrigin;
      TextView textOrigin = ViewBindings.findChildViewById(rootView, id);
      if (textOrigin == null) {
        break missingId;
      }

      id = R.id.textPrice;
      TextView textPrice = ViewBindings.findChildViewById(rootView, id);
      if (textPrice == null) {
        break missingId;
      }

      id = R.id.textTimeDistance;
      TextView textTimeDistance = ViewBindings.findChildViewById(rootView, id);
      if (textTimeDistance == null) {
        break missingId;
      }

      return new BottomSheetOffert2Binding((LinearLayout) rootView, btnCancel, btnConfirmRequest,
          textDestiny, textOrigin, textPrice, textTimeDistance);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}