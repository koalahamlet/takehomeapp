package com.mikehuff.takehomeapplication.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.*;

import com.mikehuff.takehomeapplication.TakehomeApplication;

public class ResUtils {

  public static Resources getResources() {
    return TakehomeApplication.getInstance().getResources();
  }

  public static String getQuantityString(@PluralsRes int resId, int quantity, Object... args) {
    return getResources().getQuantityString(resId, quantity, args);
  }

  public static String getStringByResId(@StringRes int resId) {
    return getResources().getString(resId);
  }

  public static String getStringByResId(@StringRes int resId, Object... args) {
    return getResources().getString(resId, args);
  }

  public static String[] getStringArray(@ArrayRes int resId) {
    return getResources().getStringArray(resId);
  }

  public static int getInteger(@IntegerRes int resId) {
    return getResources().getInteger(resId);
  }

  public static Drawable getDrawable(@DrawableRes int resId) {
    return getResources().getDrawable(resId);
  }
}
