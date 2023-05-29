package com.example.newsdesk.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsdesk.HomeActivity;
import com.example.newsdesk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login_Fragment extends Fragment {

    SharedPreferences preferences;
    Button btnLogin;
    EditText Email,Password;
    TextView ForgotPassword, Register;
    FirebaseFirestore db;
    String name, email, password, address, title, image;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        Email = view.findViewById(R.id.etEmail);
        Password = view.findViewById(R.id.etPassword);
        ForgotPassword = view.findViewById(R.id.tvForgotPassword);
        Register = view.findViewById(R.id.tvRegister);

        preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString() != "" && Password.getText().toString() != ""){
                    Toast.makeText(getContext(), "Please Wait", Toast.LENGTH_LONG).show();

                    DocumentReference documentReference = db.collection("Users").document(Email.getText().toString());
                    documentReference.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot >() {
                        @Override
                        public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                     password = Objects.requireNonNull(document.get("password")).toString();

                                    if(password.equals(Password.getText().toString())){

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

                                        Toast.makeText(getContext(), "Successfully SignedIn", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getContext() ,HomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Wrong Password!!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), "Wrong Email/Password!!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });


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

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Register_Fragment register_fragment = new Register_Fragment();
                fragmentTransaction.replace(R.id.Main, register_fragment);
                fragmentTransaction.commit();
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ForgotPassword_Fragment forgotPassword_fragment = new ForgotPassword_Fragment();
                fragmentTransaction.replace(R.id.Main, forgotPassword_fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}