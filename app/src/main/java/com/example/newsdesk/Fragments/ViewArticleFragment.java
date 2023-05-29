package com.example.newsdesk.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsdesk.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewArticleFragment extends Fragment {

    URL newUrl;
    ImageView imgHead;
    TextView tvTitle, tvBody;

    String Image, Body, Title;

   @SuppressLint("MissingInflatedId")
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_article, container, false);

       Bundle bundle = getArguments();
       Title = bundle.getString("Title");
       Body = bundle.getString("Body");
       Image = bundle.getString("Image");

        imgHead = view.findViewById(R.id.imgHead);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvBody = view.findViewById(R.id.tvBody);

        tvBody.setText(Body);
        tvTitle.setText(Title);

       try {
           newUrl = new URL(Image);
           Bitmap bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
           imgHead.setImageBitmap(bitmap);
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

        return view;
    }
}