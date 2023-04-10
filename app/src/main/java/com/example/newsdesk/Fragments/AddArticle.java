package com.example.newsdesk.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.newsdesk.HomeActivity;
import com.example.newsdesk.R;

public class AddArticle extends Fragment {

    Button btnSave;
    EditText Title,Body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_article, container, false);

        btnSave = view.findViewById(R.id.btnSave);
        Title = view.findViewById(R.id.etTitle);
        Body = view.findViewById(R.id.etArticleBody);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Title.getText().toString() != "" && Body.getText().toString() != ""){
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("Please Fill all Fields ?");

                    builder.setTitle("Alert !");

                    builder.setNegativeButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        return  view;
    }
}