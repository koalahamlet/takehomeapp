package com.mikehuff.takehomeapplication;

import android.app.Application;

import timber.log.Timber;

public class TakehomeApplication extends Application {
  private static TakehomeApplication sInstance;

  @Override
  public void onCreate() {
    super.onCreate();
    sInstance = this;
    Timber.plant(new Timber.DebugTree());
  }

  public static TakehomeApplication getInstance() {
    return sInstance;
  }
}
