package com.androidevelopers.cs5540.businessexchange;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    TextView textViewUserEmail;
    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.tv_user_email);
        textViewUserEmail.setText("Welcome " + user.getEmail());

        buttonLogout = (Button) findViewById(R.id.bt_logout);

        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
