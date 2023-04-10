package com.example.newsdesk.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsdesk.Classes.Category;
import com.example.newsdesk.Classes.CategoryAdapterClass;
import com.example.newsdesk.Classes.NewsAdapterClass;
import com.example.newsdesk.Classes.NewsArticles;
import com.example.newsdesk.R;

import java.util.ArrayList;

public class Home_Fragment extends Fragment {

    RecyclerView ViewArticlesRV, CategoryRv;
    ArrayList<NewsArticles> newsArticlesArrayList ;
    ArrayList<Category> categoryArrayList ;
    NewsAdapterClass newsAdapterClass;
    CategoryAdapterClass categoryAdapterClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ViewArticlesRV = view.findViewById(R.id.recyclerview);
        CategoryRv = view.findViewById(R.id.recyclerviewCategory);
        ViewArticlesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoryRv.setLayoutManager(new LinearLayoutManager( getContext(),LinearLayoutManager.HORIZONTAL, false));

        newsArticlesArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
        Category categorySport = new Category("Sports");
        Category categoryWeather = new Category("Weather");
        Category categoryPolitics = new Category("Politics");
        Category categoryInternational = new Category("International");
        Category categoryLocal = new Category("Local");
        categoryArrayList.add(categorySport);
        categoryArrayList.add(categoryWeather);
        categoryArrayList.add(categoryPolitics);
        categoryArrayList.add(categoryInternational);
        categoryArrayList.add(categoryLocal);

        NewsArticles newsArticles = new NewsArticles("https://wallpapercave.com/wp/wp4251167.jpg", "Babar Azam Scores Century", "Mohammad Babar Azam, is a Pakistani international cricketer and captain of the Pakistan national cricket team in all formats. Regarded as one of the finest batters in contemporary world cricket, he is the only cricketer in the world to be in the top five rankings across all formats.");
        NewsArticles newsArticles1 = new NewsArticles("https://i.pinimg.com/originals/fb/6c/90/fb6c90c64e6de472af56a78eafbfdf9e.jpg", "Babar the King", "");
        NewsArticles newsArticles2 = new NewsArticles("https://media.istockphoto.com/id/458685027/photo/german-total-gas-station-sign.jpg?s=1024x1024&w=is&k=20&c=IluENr-Bxhce-ko7SKu4dif_yxnirC9Psl5QmRJ5_8U=", "No hike in Fuel prices till 15th April says Spokes person Govt. of Pakistan", "");
        newsArticlesArrayList.add(newsArticles);
        newsArticlesArrayList.add(newsArticles1);
        newsArticlesArrayList.add(newsArticles2);

        newsAdapterClass = new NewsAdapterClass(newsArticlesArrayList);
        categoryAdapterClass = new CategoryAdapterClass(categoryArrayList);

        ViewArticlesRV.setAdapter(newsAdapterClass);
        CategoryRv.setAdapter(categoryAdapterClass);

        return view;
    }


}