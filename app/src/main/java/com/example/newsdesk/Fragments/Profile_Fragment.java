package com.example.newsdesk.Fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Profile_Fragment extends Fragment {
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filepath;
    String Path;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef = db.collection("Users");

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    String name, email, password, address, title, image;
    URL newUrl = null;
    TextView tvName, tvEmail, tvTitle;
    EditText etEmail, etPassword, etAddress;
    Button btnUpdate;
    ImageView ivProfile;
    ImageButton ibEdit;
    SharedPreferences preferences;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTitle = view.findViewById(R.id.tvTitle);
        etAddress = view.findViewById(R.id.etAddress);
        etPassword = view.findViewById(R.id.etPassword);
        etEmail = view.findViewById(R.id.etEmail);
        ivProfile = view.findViewById(R.id.ivProfile);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        ibEdit = view.findViewById(R.id.btnEdit);

        btnUpdate.setVisibility(View.INVISIBLE);


        email = preferences.getString("email", "Please Login");
        name = preferences.getString("name", "Please Login");
        password = preferences.getString("password", "Please Login");
        address = preferences.getString("address", "Please Login");
        title = preferences.getString("title", "Please Login");
        image = preferences.getString("image", "");



        if(image != ""){
            try {
                newUrl = new URL(image);
                Bitmap bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
                ivProfile.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tvName.setText(name);
        tvEmail.setText(email);
        tvTitle.setText(title);
        etEmail.setText(email);
        etAddress.setText(address);
        etPassword.setText(password);

        ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.setEnabled(false);
                etPassword.setEnabled(true);
                etAddress.setEnabled(true);
                btnUpdate.setVisibility(View.VISIBLE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage(email);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
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
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                ivProfile.setImageBitmap(bitmap);
            }catch(Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void UploadImage(String Email) {

        if(filepath != null){

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Updating");
            progressDialog.show();

            StorageReference ref = storageReference.child("uploads/" + Email);

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

                    storageReference.child("uploads/" + Email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri DownUri = uri;
                            Path = uri.toString();
                             {
                                String Email = etEmail.getText().toString();
                                String Password = etPassword.getText().toString();
                                String Address = etAddress.getText().toString();

                                if(Email.equals("") || Password.equals("") || Address.equals("")){
                                    Toast.makeText(getContext(), "Please Fill all Fields", Toast.LENGTH_LONG).show();
                                }

                                else{
                                    UserModel User = new UserModel(name, Email, Password, Address, "Article Writer", Path);

                                    colRef.document(Email).set(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Profile Updated Successfully!!", Toast.LENGTH_SHORT).show();
                                                etAddress.setEnabled(false);
                                                etEmail.setEnabled(false);
                                                etAddress.setEnabled(false);
                                                btnUpdate.setVisibility(View.INVISIBLE);


                                                DocumentReference documentReference = db.collection("Users").document(Email);
                                                documentReference.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot >() {
                                                    @Override
                                                    public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            DocumentSnapshot document = task.getResult();
                                                            if(document.exists()){
                                                                password = Objects.requireNonNull(document.get("password")).toString();

                                                                if(password.equals(Password)){

                                                                    name = document.get("name").toString();
                                                                    password = document.get("password").toString();
                                                                    email =  document.get("email").toString();
                                                                    address = document.get("address").toString();
                                                                    image =  document.get("image").toString();
                                                                    title =  document.get("title").toString();

                                                                    SharedPreferences.Editor editor = preferences.edit();
                                                                    editor.putString("name", name);
                                                                    editor.putString("password", password);
                                                                    editor.putString("email", email);
                                                                    editor.putString("address", address);
                                                                    editor.putString("image", image);
                                                                    editor.putString("title", title);
                                                                    editor.putBoolean("isUserLogin", true);
                                                                    editor.apply();


                                                                }

                                                            }

                                                        }
                                                    }
                                                });

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