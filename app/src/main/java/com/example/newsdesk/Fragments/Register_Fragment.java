package com.example.newsdesk.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.newsdesk.HomeActivity;
import com.example.newsdesk.R;

public class Register_Fragment extends Fragment {

    Button btnRegister;
    EditText Email,Password, Address;
    TextView  Login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        btnRegister = view.findViewById(R.id.btnRegister);
        Email = view.findViewById(R.id.etEmail);
        Password = view.findViewById(R.id.etPassword);
        Address = view.findViewById(R.id.etAddress);
        Login = view.findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString() != "" && Password.getText().toString() != "" && Address.getText().toString() != ""){
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

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Login_Fragment login_fragment = new Login_Fragment();
                fragmentTransaction.replace(R.id.Main, login_fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}