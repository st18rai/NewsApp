package com.st18apps.newsapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.st18apps.newsapp.utils.ItemClickSupport;
import com.st18apps.newsapp.R;
import com.st18apps.newsapp.adapters.RecyclerAdapter;
import com.st18apps.newsapp.model.Article;
import com.st18apps.newsapp.model.ResponseModel;
import com.st18apps.newsapp.rest.APIInterface;
import com.st18apps.newsapp.rest.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Article> articles;
    private String API_KEY = "855401c7ff6040639e7b9671d0e9a37e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articles = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call = apiService.getLatestNews("techcrunch", API_KEY);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        articles = articleList;
                        final RecyclerAdapter adapter = new RecyclerAdapter(articleList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("TAG", t.toString());

            }
        });


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                if (!TextUtils.isEmpty(articles.get(position).getUrl())) {
                    Log.e("clicked url", articles.get(position).getUrl());
                    Intent webActivity = new Intent(getApplicationContext(), WebActivity.class);
                    webActivity.putExtra("url", articles.get(position).getUrl());
                    startActivity(webActivity);
                }


            }
        });
    }
}
