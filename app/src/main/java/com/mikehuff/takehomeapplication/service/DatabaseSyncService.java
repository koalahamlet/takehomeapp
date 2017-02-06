package com.mikehuff.takehomeapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mikehuff.takehomeapplication.R;
import com.mikehuff.takehomeapplication.injection.Injector;
import com.mikehuff.takehomeapplication.models.Member;
import com.mikehuff.takehomeapplication.models.Profile;
import com.mikehuff.takehomeapplication.models.TeamResponse;
import com.mikehuff.takehomeapplication.persistance.RealmUser;
import com.mikehuff.takehomeapplication.persistance.SharedPrefs;
import com.mikehuff.takehomeapplication.rest.UserService;
import com.mikehuff.takehomeapplication.utils.ResUtils;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;

import static com.mikehuff.takehomeapplication.Constants.API_KEY;
import static com.mikehuff.takehomeapplication.Constants.DUMMY_IMAGE;
import static com.mikehuff.takehomeapplication.Constants.EXTRA_ERROR_MESSAGE;

public class DatabaseSyncService extends IntentService {

  public static final String ACTION_COMMUNICATE_ERROR = "com.mikehuff.networking_error";

  public DatabaseSyncService() {
    super("DatabaseSyncService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Realm realm = Injector.provideRealm();
    UserService service = Injector.provideUserService();
    TeamResponse response;

    try {
      response = service.getUserList(API_KEY, "1", "1").execute().body();

      List<Member> members = response.getMembers();

      realm.beginTransaction();
      for (Member m : members) {
        Profile p = m.getProfile();
        RealmUser rUser = new RealmUser();
        rUser.setUserId(m.getId());
        rUser.setUserName(m.getName());
        rUser.setRealName(p.getRealName());
        rUser.setJobTitle(p.getTitle() != null ? p.getTitle() : ResUtils.getStringByResId(R.string.default_title));
        rUser.setUserAvatarUrl(p.getImageOriginal() != null ? p.getImageOriginal() : DUMMY_IMAGE);
        rUser.setEmail(p.getEmail() != null ? p.getEmail() : ResUtils.getStringByResId(R.string.no_email));
        rUser.setPresence(m.getPresence());
        rUser.setColor("#" + m.getColor());
        rUser.setPhone(p.getPhone() != null ? p.getPhone() : ResUtils.getStringByResId(R.string.default_telephone));

        //  This attempts to account for if one of the users on the team is deleted on the server.
        if (!m.isDeleted()) {
          realm.copyToRealmOrUpdate(rUser);
        } else {
          if (rUser.isValid()) rUser.deleteFromRealm();
        }
      }
      realm.commitTransaction();

      new SharedPrefs(this).setRecentUpdateTime(response.getCacheTs());

    } catch (IOException e) {
      e.printStackTrace();
      Intent in = new Intent(ACTION_COMMUNICATE_ERROR);
      in.putExtra(EXTRA_ERROR_MESSAGE, e.getMessage());
      LocalBroadcastManager.getInstance(this).sendBroadcast(in);
    }
    realm.close();
  }
}
