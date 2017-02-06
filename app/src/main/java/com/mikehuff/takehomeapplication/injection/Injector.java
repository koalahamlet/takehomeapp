package com.mikehuff.takehomeapplication.injection;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.mikehuff.takehomeapplication.Constants;
import com.mikehuff.takehomeapplication.TakehomeApplication;
import com.mikehuff.takehomeapplication.persistance.SharedPrefs;
import com.mikehuff.takehomeapplication.rest.UserService;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Injector {
  public static Retrofit provideRetrofit(String baseUrl) {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(provideGson())
            .build();
  }

  private static Converter.Factory provideGson() {
    return GsonConverterFactory.create(
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create());
  }

  public static UserService provideUserService() {
    return provideRetrofit(Constants.BASE_URL).create(UserService.class);
  }

  public static SharedPrefs provideSharedPrefs() {
    return new SharedPrefs(TakehomeApplication.getInstance());
  }

  public static Realm provideRealm() {
    Realm.init(TakehomeApplication.getInstance());
    RealmConfiguration realmConfiguration = new RealmConfiguration
            .Builder()
            .deleteRealmIfMigrationNeeded()
            .build();
    Realm.setDefaultConfiguration(realmConfiguration);
    return Realm.getInstance(realmConfiguration);
  }
}
