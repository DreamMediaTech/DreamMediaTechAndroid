package com.example.admin.dreammediatechapp.UI.InformationPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.dreammediatechapp.Entities.Article;
import com.example.admin.dreammediatechapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyShareActivity extends AppCompatActivity {
    private List<Article> articleList = new ArrayList<>();
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_share);
        for (int i=1;i<4;i++){
            Article article = new Article();
            article.setaTitle("第"+i+"篇文章");
            article.setaTime("2018-01-0"+i);
            articleList.add(article);
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        actionBar.setTitle("我的分享");
        init();
    }
    //监听标题栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void init(){
        recyclerView=findViewById(R.id.share_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new ArticleAdapter());
    }
    private class ArticleHolder extends RecyclerView.ViewHolder{

        private Article article;
        // private ConstraintLayout item_layout;
        private ImageView article_cover;
        private TextView article_title,article_time;


        public ArticleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.article_item_layout,parent,false));

            article_cover = itemView.findViewById(R.id.article_cover);
            article_time = itemView.findViewById(R.id.article_time);
            article_title = itemView.findViewById(R.id.article_title);
        }

    }

    private class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder>{

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            return new ArticleHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int i) {
            Article article=articleList.get(i);
            holder.article=article;
            holder.article_cover.setImageResource(R.mipmap.banner2);
            holder.article_title.setText(article.getaTitle());
            holder.article_time.setText(article.getaTime());


        }

        @Override
        public int getItemCount() {
            return articleList.size();
        }
    }
}
