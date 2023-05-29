package com.example.newsdesk.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.newsdesk.Classes.Category;
import com.example.newsdesk.Classes.CategoryAdapterClass;
import com.example.newsdesk.Classes.NewsAdapterClass;
import com.example.newsdesk.Classes.NewsArticles;
import com.example.newsdesk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home_Fragment extends Fragment {
    ProgressBar pBar;
    RecyclerView ViewArticlesRV, CategoryRv;
    ArrayList < Category > categoryArrayList ;

    ArrayList<NewsArticles> newsArticlesArrayList ;
    NewsAdapterClass newsAdapterClass;
    CategoryAdapterClass categoryAdapterClass;
    String Category;
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        Category = sharedPreferences.getString("Category", "Local");

        pBar = view.findViewById(R.id.pbar);

        CategoryRv = view.findViewById(R.id.recyclerviewCategory);
        CategoryRv.setLayoutManager(new LinearLayoutManager( getContext(),LinearLayoutManager.HORIZONTAL, false));


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

        ViewArticlesRV = view.findViewById(R.id.recyclerview);
        ViewArticlesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        newsArticlesArrayList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("Articles");
        colRef.get().addOnSuccessListener(new OnSuccessListener < QuerySnapshot >() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List < DocumentSnapshot > documentSnapshotArrayList = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot d : documentSnapshotArrayList){
                    NewsArticles insert = d.toObject(NewsArticles.class);
                    newsArticlesArrayList.add(insert);
                }
                newsAdapterClass.notifyDataSetChanged();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pBar.setVisibility(View.GONE);
            }
        }, 10000);

        newsAdapterClass = new NewsAdapterClass(newsArticlesArrayList, Category);
        ViewArticlesRV.setAdapter(newsAdapterClass);

        categoryAdapterClass = new CategoryAdapterClass(categoryArrayList);
        CategoryRv.setAdapter(categoryAdapterClass);

        return view;
    }


}