package com.example.newsdesk.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsdesk.HomeActivity;
import com.example.newsdesk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ForgotPassword_Fragment extends Fragment {

    Button btnForgotPassword;
    EditText Email, Address;
    String email, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_forgot_password, container, false);

       FirebaseFirestore db = FirebaseFirestore.getInstance();


        btnForgotPassword = view.findViewById(R.id.btnForgotPassword);
        Email = view.findViewById(R.id.etEmail);
        Address = view.findViewById(R.id.etAddress);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString() != ""){

                    DocumentReference documentReference = db.collection("Users").document(Email.getText().toString());
                    documentReference.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot >() {
                        @Override
                        public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    address = Objects.requireNonNull(document.get("address")).toString();

                                    if(address.equals(Address.getText().toString())){
                                        new AlertDialog.Builder(getContext()) .setTitle("Congrats! Your Password is " +
                                                        document.get("password").toString()+ " . Please Remember.")
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
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Error!!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), "Error!!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("Please Fill all Fields ?");

                    builder.setTitle("Alert !" +
                            "");

                    builder.setNegativeButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        return view;
    }
}