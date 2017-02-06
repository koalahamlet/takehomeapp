package com.mikehuff.takehomeapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikehuff.takehomeapplication.persistance.RealmUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<RealmUser> userResults = new ArrayList<>();

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.member_list_item, parent, false);
    return new ContentViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final ContentViewHolder contentVH = ((ContentViewHolder) holder);
    RealmUser user = userResults.get(position);
    contentVH.textView.setText(user.getUserName());
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

    @Bind(R.id.username)
    TextView textView;

    public ContentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
