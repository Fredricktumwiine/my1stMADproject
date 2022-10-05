package com.firstapp.madproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button btnSignUp, btnSignIn;
    DatabaseHelperClass Db;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if (id == R.id.about){
           Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
           startActivity(intent);

           return true;
       }
       else
       if (id == R.id.broadc){
           Intent intent = new Intent(MainActivity.this, TermsAndConditionsActivity.class);
           startActivity(intent);

           return true;
       }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentMyService = new Intent(this, MyService.class);
        startService(intentMyService);



        username = (EditText)findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        Db = new DatabaseHelperClass(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("")||pass.equals("")||repass.equals("")){
                    Toast.makeText(MainActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass.equals(repass)){
                        Boolean usercheckResult = Db.checkUpUsername(user);
                        if (usercheckResult == false){
                            Boolean regResult = Db.insertData(user,pass);
                            if (regResult == true){
                                Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new  Intent(getApplicationContext(), loginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "User already exists\n Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        
                    }
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intent);
            }
        });
    }
}
//https://www.youtube.com/watch?v=o9CVZ1gQgQo