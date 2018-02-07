package com.example.admin.dreammediatechapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.Comment;
import com.example.admin.dreammediatechapp.R;
import com.example.admin.dreammediatechapp.common.CircleImageView;

/**
 * Created by Admin on 2018/2/1.
 */

public class CommentAdapter extends ListBaseAdapter<Comment> {
    public CommentAdapter(Context context) {

        super(context);

    }
    @Override
    public int getLayoutId() {
        return R.layout.item_comment_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        Comment comment = mDataList.get(position);

        CircleImageView circleImageView = holder.getView(R.id.comment_userimage);
        TextView commentUsername = holder.getView(R.id.comment_username);
        TextView commentTime = holder.getView(R.id.comment_time);
        TextView commentContent = holder.getView(R.id.comment_content);

        circleImageView.setImageResource(R.mipmap.ic_launcher_round);
        commentUsername.setText(comment.getcUser().getuNickName());
        commentTime.setText(comment.getcTime());
        commentContent.setText(comment.getcContent());


    }


    @Override
    public void onViewRecycled(SuperViewHolder holder) {
        super.onViewRecycled(holder);
    }
    private class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private TextView commentUsername,commentTime,commentContent;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.comment_userimage);
            commentUsername = itemView.findViewById(R.id.comment_username);
            commentTime = itemView.findViewById(R.id.comment_time);
            commentContent = itemView.findViewById(R.id.comment_content);
        }
    }
}
