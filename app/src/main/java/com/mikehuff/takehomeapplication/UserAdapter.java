package com.mikehuff.takehomeapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikehuff.takehomeapplication.persistance.RealmUser;
import com.mikehuff.takehomeapplication.transforms.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mikehuff.takehomeapplication.Constants.EXTRA_USER_ID;


class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<RealmUser> userResults = new ArrayList<>();
  private CircleTransform circleTransform;
  private Activity activity;

  public UserAdapter(Activity activity) {
    this.activity = activity;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.member_list_item, parent, false);
    circleTransform = new CircleTransform(parent.getContext());
    return new ContentViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    final ContentViewHolder contentVH = ((ContentViewHolder) holder);
    RealmUser user = userResults.get(position);
    contentVH.usernameView.setText(user.getUserName());
    contentVH.realNameView.setText(user.getRealName());
    contentVH.availabilityView.setText(user.getPresence());

    contentVH.relativeLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(activity, DetailActivity.class);
        RealmUser user = userResults.get(position);
        intent.putExtra(EXTRA_USER_ID, user.getUserId());
        activity.startActivity(intent);
      }
    });
    ((GradientDrawable) contentVH.circleView.getBackground()).setColor(Color.parseColor(user.getColor()));
    Glide.with(contentVH.imageView.getContext()).load(user.getUserAvatarUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.unknown).transform(circleTransform).into(contentVH.imageView);
  }

  @Override
  public int getItemCount() {
    return userResults.size();
  }


  public void setUserResults(List<RealmUser> userResults) {
    this.userResults = userResults;
  }

  /**
   * Viewholder Inner Class
   */
  public static class ContentViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.main_content)
    RelativeLayout relativeLayout;

    @Bind(R.id.real_name)
    TextView realNameView;

    @Bind(R.id.name)
    TextView usernameView;

    @Bind(R.id.circle)
    ImageView circleView;

    @Bind(R.id.avatar_image_view)
    ImageView imageView;

    @Bind(R.id.availability)
    TextView availabilityView;

    public ContentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
