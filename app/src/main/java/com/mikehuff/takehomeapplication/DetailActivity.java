package com.mikehuff.takehomeapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikehuff.takehomeapplication.injection.Injector;
import com.mikehuff.takehomeapplication.persistance.RealmUser;
import com.mikehuff.takehomeapplication.transforms.CircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.mikehuff.takehomeapplication.Constants.EXTRA_USER_ID;

public class DetailActivity extends AppCompatActivity {

  private RealmUser user;
  private CircleTransform circleTransform;

  @Bind(R.id.collapsing_toolbar)
  CollapsingToolbarLayout collapsingToolbarLayout;

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.value_username)
  TextView tvUsername;

  @Bind(R.id.value_email)
  TextView tvEmail;

  @Bind(R.id.value_job)
  TextView tvJobTitle;

  @Bind(R.id.value_phone)
  TextView tvPhone;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);
    circleTransform = new CircleTransform(this);
    final String userId = getIntent().getStringExtra(EXTRA_USER_ID);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    Realm realm = Injector.provideRealm();
    user = realm.where(RealmUser.class).equalTo("userId", userId).findFirst();
    realm.close();

    collapsingToolbarLayout.setContentScrimColor(Color.parseColor(user.getColor()));
    collapsingToolbarLayout.setTitle(user.getRealName());

    toolbar.setBackgroundColor(Color.parseColor(user.getColor()));

    tvUsername.setText(user.getUserName());

    tvEmail.setText("Email: " + user.getEmail());

    tvPhone.setText("Phone: " + user.getPhone());

    tvJobTitle.setText(user.getJobTitle());

    loadBackdrop();
  }

  private void loadBackdrop() {
    final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
    final ImageView circleView = (ImageView) findViewById(R.id.circle_view);
    imageView.setBackgroundColor(Color.parseColor(user.getColor()));

    Glide.with(this).load(user.getUserAvatarUrl()).centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.unknown).transform(circleTransform).into(circleView);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
