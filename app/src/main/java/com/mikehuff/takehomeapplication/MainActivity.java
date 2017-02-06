package com.mikehuff.takehomeapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mikehuff.takehomeapplication.injection.Injector;
import com.mikehuff.takehomeapplication.persistance.RealmUser;
import com.mikehuff.takehomeapplication.service.DatabaseSyncService;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import timber.log.Timber;

import static com.mikehuff.takehomeapplication.Constants.EXTRA_ERROR_MESSAGE;

public class MainActivity extends AppCompatActivity {

  private Realm realm;

  @Bind(R.id.user_recycler_view)
  RecyclerView userRecyclerView;

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.swipe_container)
  SwipeRefreshLayout swipeRefreshLayout;

  UserAdapter userAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    realm = Injector.provideRealm();

    RecyclerView.LayoutManager   layoutManager = new LinearLayoutManager(this);
    userRecyclerView.setLayoutManager(layoutManager);

    userAdapter = new UserAdapter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    IntentFilter filter = new IntentFilter(DatabaseSyncService.ACTION_COMMUNICATE_ERROR);
    LocalBroadcastManager.getInstance(this).registerReceiver(errorReceiver, filter);

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        updateDatabase();
      }
    });

    realm.addChangeListener(new RealmChangeListener<Realm>() {
      @Override
      public void onChange(Realm element) {
        Timber.d("Realm Changed!");
        updateAdapter();
      }
    });

    updateDatabase();
    updateAdapter();
  }

  @Override
  protected void onStop() {
    super.onStop();
    realm.removeAllChangeListeners();
  }

  @Override
  protected void onDestroy() {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(errorReceiver);
    realm.close();
    super.onDestroy();
  }

  private void updateAdapter() {
    RealmResults<RealmUser> userlist = realm.where(RealmUser.class).findAll();
    userRecyclerView.setAdapter(userAdapter);
    userAdapter.setUserResults(userlist);
    userAdapter.notifyDataSetChanged();
    swipeRefreshLayout.setRefreshing(false);
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
      swipeRefreshLayout.setRefreshing(false);
    }
  };
}
