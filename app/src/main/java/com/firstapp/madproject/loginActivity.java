package com.firstapp.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;

    DatabaseHelperClass Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        Db = new DatabaseHelperClass(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uza = username.getText().toString();
                String paz = password.getText().toString();

                if (uza.equals("")||paz.equals("")){
                    Toast.makeText(loginActivity.this, "Please enter all your credentials", Toast.LENGTH_SHORT).show();
                } else{
                    Boolean rslt = Db.checkUpUsernamePassword(uza,paz);
                    if (rslt==true){
                        Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                        startActivity(intent);

                    } else{
                        Toast.makeText(loginActivity.this, "User Invalid, Access Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}