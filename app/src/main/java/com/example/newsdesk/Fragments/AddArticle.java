package com.example.newsdesk.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.newsdesk.Classes.Category;
import com.example.newsdesk.Classes.NewsArticles;
import com.example.newsdesk.Classes.UserModel;
import com.example.newsdesk.HomeActivity;
import com.example.newsdesk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class AddArticle extends Fragment {


    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filepath;
    String Path;

    ArrayList < String > categoryArrayList ;
    Spinner spCategory;
    ArrayAdapter<String> categoryAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    CollectionReference colRef;
    Button btnSave, btnUpload;
    String email;
    SharedPreferences preferences;
    EditText Title,Body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_article, container, false);

        btnSave = view.findViewById(R.id.btnSave);
        spCategory = view.findViewById(R.id.spCategory);
        Title = view.findViewById(R.id.etTitle);
        Body = view.findViewById(R.id.etArticleBody);
        btnUpload = view.findViewById(R.id.btnImageUpload);

        categoryArrayList = new ArrayList<>();
        categoryArrayList.add("Sports");
        categoryArrayList.add("Weather");
        categoryArrayList.add("Politics");
        categoryArrayList.add("International");
        categoryArrayList.add("Local");

        categoryAdapter = new ArrayAdapter <String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,categoryArrayList);
        spCategory.setAdapter(categoryAdapter);
        preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        email = preferences.getString("email", "admin@newsdesk.com");

         colRef = db.collection("Articles");




        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Title.getText().toString() != "" && Body.getText().toString() != ""){
                    UploadImage(email);
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

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            filepath = data.getData();
        }
    }


    private void UploadImage(String Email) {

        if(filepath != null){

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Updating");
            progressDialog.show();

            StorageReference ref = storageReference.child("articles/" + Email + Title.getText().toString());

            ref.putFile(filepath).addOnCompleteListener(new OnCompleteListener < UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task <UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        progressDialog.setMessage("Upload Complete");
                        Toast.makeText(getContext(), "Image Uploaded Successfully! Please Wait", Toast.LENGTH_SHORT).show();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Unexpected Error! not Uploaded! Try Again", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener <UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload " + progress + "%");
                }
            }).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.child("articles/" + Email + Title.getText().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri DownUri = uri;
                            Path = uri.toString();
                            {
                                String title = Title.getText().toString();
                                String body = Body.getText().toString();

                                if(title.equals("") || body.equals("")){
                                    Toast.makeText(getContext(), "Please Fill all Fields", Toast.LENGTH_LONG).show();
                                }

                                else{
                                    NewsArticles newsArticles = new NewsArticles(Path, title, body, spCategory.getSelectedItem().toString(), email);

                                    colRef.document(title).set(newsArticles).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Article Added Successfully!!", Toast.LENGTH_SHORT).show();
                                                Title.setText("");
                                                Body.setText("");
                                                progressDialog.cancel();
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Unexpected Error! Try Again", Toast.LENGTH_SHORT).show();
                                                progressDialog.cancel();
                                            }
                                        }

                                    });
                                }
                            }
                        }
                    });

                }
            });
        }
        else{
            Toast.makeText(getContext(), "1", Toast.LENGTH_LONG).show();
        }
    }


}