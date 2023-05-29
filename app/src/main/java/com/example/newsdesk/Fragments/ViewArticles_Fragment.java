package com.example.newsdesk.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.newsdesk.Classes.Category;
import com.example.newsdesk.Classes.CategoryAdapterClass;
import com.example.newsdesk.Classes.NewsAdapterClass;
import com.example.newsdesk.Classes.NewsArticles;
import com.example.newsdesk.Classes.ViewArticlesAdapterClass;
import com.example.newsdesk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewArticles_Fragment extends Fragment {
    String email;
    ProgressBar pBar;
    SharedPreferences preferences;
    RecyclerView ViewArticlesRV;
    ArrayList<NewsArticles> newsArticlesArrayList ;
    ViewArticlesAdapterClass newsAdapterClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_articles_, container, false);

        ViewArticlesRV = view.findViewById(R.id.RVArticles);
        pBar = view.findViewById(R.id.pbar);
        ViewArticlesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        newsArticlesArrayList = new ArrayList <>();

        preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        email = preferences.getString("email", "admin@newsdesk.com");


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

        newsAdapterClass = new ViewArticlesAdapterClass(newsArticlesArrayList, email);
        ViewArticlesRV.setAdapter(newsAdapterClass);

        return view;
    }
}