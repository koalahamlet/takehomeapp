package com.mikehuff.takehomeapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikehuff.takehomeapplication.service.DatabaseSyncService;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    updateDatabase();

  }

  private void updateDatabase() {
    // Construct our Intent specifying the Service
    Intent i = new Intent(this, DatabaseSyncService.class);
    // Start the service
    startService(i);
    Timber.d("fired intent");
  }
}
