package com.example.admin.dreammediatechapp.Adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.Article;
import com.example.admin.dreammediatechapp.R;

import java.util.List;

/**
 * Created by Admin on 2018/2/7.
 */

public class ArticleListAdapter extends ListBaseAdapter<Article>{
    public ArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.article_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        Article article = mDataList.get(position);

        ImageView article_cover = holder.getView(R.id.article_cover);
        TextView article_title = holder.getView(R.id.article_title);
        TextView article_time = holder.getView(R.id.article_time);
        TextView article_read = holder.getView(R.id.article_read);

        article_cover.setImageResource(R.mipmap.banner2);
        article_title.setText(article.getaTitle());
        article_time.setText(article.getaTime());
        article_read.setText(String.valueOf(article.getClicks()));

    }
}
