package com.mikehuff.takehomeapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mikehuff.takehomeapplication.service.DatabaseSyncService;

import timber.log.Timber;

import static com.mikehuff.takehomeapplication.Constants.EXTRA_ERROR_MESSAGE;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    updateDatabase();

  }

  @Override
  protected void onStart() {
    super.onStart();
    IntentFilter filter = new IntentFilter(DatabaseSyncService.ACTION_COMMUNICATE_ERROR);
    LocalBroadcastManager.getInstance(this).registerReceiver(errorReceiver, filter);
  }

  @Override
  protected void onDestroy() {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(errorReceiver);
    super.onDestroy();
  }

  private void updateDatabase() {
    // Construct our Intent specifying the Service
    Intent i = new Intent(this, DatabaseSyncService.class);
    // Start the service
    startService(i);
    Timber.d("fired intent");
  }

  private BroadcastReceiver errorReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String resultCode = intent.getStringExtra(EXTRA_ERROR_MESSAGE);
      Toast.makeText(MainActivity.this, resultCode, Toast.LENGTH_SHORT).show();
    }
  };
}
