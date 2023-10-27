package com.example.gympartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private TextView loginTextView;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Creating new account");
        progressDialog.setCanceledOnTouchOutside(false);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    // Registration successful, do something here
                                    progressDialog.dismiss();
                                    startActivity(new Intent(Register.this,Login.class));
                                    finish();
                                } else {
                                    // Registration failed, handle the error
                                }
                            });
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Return to the login activity
            }
        });
    }
}