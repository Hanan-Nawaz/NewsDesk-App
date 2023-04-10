package com.example.newsdesk.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsdesk.Classes.Category;
import com.example.newsdesk.Classes.CategoryAdapterClass;
import com.example.newsdesk.Classes.NewsAdapterClass;
import com.example.newsdesk.Classes.NewsArticles;
import com.example.newsdesk.Classes.ViewArticlesAdapterClass;
import com.example.newsdesk.R;

import java.util.ArrayList;

public class ViewArticles_Fragment extends Fragment {

    RecyclerView ViewArticlesRV;
    ArrayList<NewsArticles> newsArticlesArrayList ;
    ViewArticlesAdapterClass newsAdapterClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_articles_, container, false);

        ViewArticlesRV = view.findViewById(R.id.RVArticles);
        ViewArticlesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        newsArticlesArrayList = new ArrayList <>();

        NewsArticles newsArticles = new NewsArticles("https://wallpapercave.com/wp/wp4251167.jpg", "Babar Azam Scores Century", "Mohammad Babar Azam, is a Pakistani international cricketer and captain of the Pakistan national cricket team in all formats. Regarded as one of the finest batters in contemporary world cricket, he is the only cricketer in the world to be in the top five rankings across all formats.");
        NewsArticles newsArticles1 = new NewsArticles("https://i.pinimg.com/originals/fb/6c/90/fb6c90c64e6de472af56a78eafbfdf9e.jpg", "Babar the King", "");
        NewsArticles newsArticles2 = new NewsArticles("https://media.istockphoto.com/id/458685027/photo/german-total-gas-station-sign.jpg?s=1024x1024&w=is&k=20&c=IluENr-Bxhce-ko7SKu4dif_yxnirC9Psl5QmRJ5_8U=", "No hike in Fuel prices till 15th April says Spokes person Govt. of Pakistan", "");
        newsArticlesArrayList.add(newsArticles);
        newsArticlesArrayList.add(newsArticles1);
        newsArticlesArrayList.add(newsArticles2);

        newsAdapterClass = new ViewArticlesAdapterClass(newsArticlesArrayList);
        ViewArticlesRV.setAdapter(newsAdapterClass);

        return view;
    }
}