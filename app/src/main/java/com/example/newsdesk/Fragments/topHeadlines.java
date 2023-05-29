package com.example.newsdesk.Fragments;

import static java.lang.System.out;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsdesk.Classes.MainArticleAdapter;
import com.example.newsdesk.R;
import com.example.newsdesk.model.Article;
import com.example.newsdesk.model.ResponseModel;
import com.example.newsdesk.rests.APIInterface;
import com.example.newsdesk.rests.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class topHeadlines extends Fragment {

    private static final String API_KEY = "...";


    public topHeadlines() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_headlines, container, false);

        final RecyclerView mainRecycler = view.findViewById(R.id.activity_main_rv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mainRecycler.setLayoutManager(linearLayoutManager);

        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call < ResponseModel > call = apiService.getLatestNews("techcrunch",API_KEY);
        call.enqueue(new Callback <ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel>call, Response <ResponseModel> response) {
                if(response.body().getStatus().equals("ok")) {
                    List < Article > articleList = response.body().getArticles();
                    if(articleList.size()>0) {
                        final MainArticleAdapter mainArticleAdapter = new MainArticleAdapter(articleList);
                        mainRecycler.setAdapter(mainArticleAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel>call, Throwable t) {
                Log.e("out", t.toString());
            }
        });

        return view;
    }
}