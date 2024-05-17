// Generated by view binder compiler. Do not edit!
package com.app.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class BottomSheetTripBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView imageCall;

  @NonNull
  public final ImageView imageChat;

  @NonNull
  public final ImageView imageCircleView;

  @NonNull
  public final TextView textDestiny;

  @NonNull
  public final TextView textName;

  @NonNull
  public final TextView textOrigin;

  private BottomSheetTripBinding(@NonNull LinearLayout rootView, @NonNull ImageView imageCall,
      @NonNull ImageView imageChat, @NonNull ImageView imageCircleView,
      @NonNull TextView textDestiny, @NonNull TextView textName, @NonNull TextView textOrigin) {
    this.rootView = rootView;
    this.imageCall = imageCall;
    this.imageChat = imageChat;
    this.imageCircleView = imageCircleView;
    this.textDestiny = textDestiny;
    this.textName = textName;
    this.textOrigin = textOrigin;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetTripBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetTripBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_trip, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetTripBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageCall;
      ImageView imageCall = ViewBindings.findChildViewById(rootView, id);
      if (imageCall == null) {
        break missingId;
      }

      id = R.id.imageChat;
      ImageView imageChat = ViewBindings.findChildViewById(rootView, id);
      if (imageChat == null) {
        break missingId;
      }

      id = R.id.imageCircleView;
      ImageView imageCircleView = ViewBindings.findChildViewById(rootView, id);
      if (imageCircleView == null) {
        break missingId;
      }

      id = R.id.textDestiny;
      TextView textDestiny = ViewBindings.findChildViewById(rootView, id);
      if (textDestiny == null) {
        break missingId;
      }

      id = R.id.textName;
      TextView textName = ViewBindings.findChildViewById(rootView, id);
      if (textName == null) {
        break missingId;
      }

      id = R.id.textOrigin;
      TextView textOrigin = ViewBindings.findChildViewById(rootView, id);
      if (textOrigin == null) {
        break missingId;
      }

      return new BottomSheetTripBinding((LinearLayout) rootView, imageCall, imageChat,
          imageCircleView, textDestiny, textName, textOrigin);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
