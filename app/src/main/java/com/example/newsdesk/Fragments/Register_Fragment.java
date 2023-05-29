package com.example.newsdesk.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsdesk.Classes.UserModel;
import com.example.newsdesk.HomeActivity;
import com.example.newsdesk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register_Fragment extends Fragment {

    Button btnRegister;
    EditText Email, Password, Address, Name;
    TextView Login;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        btnRegister = view.findViewById(R.id.btnRegister);
        Email = view.findViewById(R.id.etEmail);
        Password = view.findViewById(R.id.etPassword);
        Address = view.findViewById(R.id.etAddress);
        Name = view.findViewById(R.id.etName);
        Login = view.findViewById(R.id.tvLogin);

        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString() != "" && Password.getText().toString() != "" && Address.getText().toString() != "" && Name.getText().toString() != "") {
                    UserModel User = new UserModel(Name.getText().toString(), Email.getText().toString(), Password.getText().toString(), Address.getText().toString(), "Article Writer", "");
                    Toast.makeText(getContext(), "Please Wait...", Toast.LENGTH_LONG).show();


                    collectionReference.document(Email.getText().toString()).set(User).addOnCompleteListener(new OnCompleteListener < Void >() {
                        @Override
                        public void onComplete(@NonNull Task < Void > task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "User Created Successfully. Please SignIn", Toast.LENGTH_LONG).show();

                                new AlertDialog.Builder(getContext()) .setTitle("Congrats! User Created Successfully. Please SignIn")
                                        .setPositiveButton("OK", new
                                                DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int j){

                                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                            transaction.replace(R.id.Main, new Login_Fragment());
                                                            transaction.commit();
                                                        }
                                                }
                            ).create().show();

                            } else {
                                Toast.makeText(getContext(), "Unexpected Error. Please Try Again ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                } else {
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

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Login_Fragment login_fragment = new Login_Fragment();
                fragmentTransaction.replace(R.id.Main, login_fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}