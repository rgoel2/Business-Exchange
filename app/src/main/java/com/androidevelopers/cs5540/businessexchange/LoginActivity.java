package com.androidevelopers.cs5540.businessexchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewGoToCreateAccount;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            //user is logged in, start user profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog (this);

        buttonLogin = (Button) findViewById(R.id.bt_login);
        editTextUsername = (EditText) findViewById(R.id.et_email);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        textViewGoToCreateAccount = (TextView) findViewById(R.id.tv_go_to_create_account);

        buttonLogin.setOnClickListener(this);
        textViewGoToCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == textViewGoToCreateAccount) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        if(v == buttonLogin) userLogin();
    }

    private void userLogin() {
        String email = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //username field empty
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password field is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if fields have entries
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> authResultTask) {
                        progressDialog.dismiss();
                        if(authResultTask.isSuccessful()){
                            //start users provile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }
}
