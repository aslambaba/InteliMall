package com.example.sairamkrishna.intelimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button loginbtn, regbtn;
    TextInputLayout loginUserEmail, loginUserPass ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.getSupportActionBar().hide();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in

            Toast.makeText(MainActivity.this, "Already Loggedin",
                    Toast.LENGTH_SHORT).show();
            Intent it = new Intent (MainActivity.this, HomeDrawer.class);
            startActivity(it);
        } else {

        }


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        regbtn = (Button) findViewById(R.id.registerbtn);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        loginUserEmail = (TextInputLayout) findViewById(R.id.loginUserEmail);
        loginUserPass = (TextInputLayout) findViewById(R.id.loginUserPass);


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, RegistrationEctivity.class);
                startActivity(it);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginUserEmail.getEditText().getText().toString();
                String password = loginUserPass.getEditText().getText().toString();
                Toast.makeText(MainActivity.this, email,
                        Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "YOu Are Loged In",
                                            Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent (MainActivity.this, HomeDrawer.class);
                                    startActivity(it);
                                } else {
                                    Toast.makeText(MainActivity.this, "Loginin failed. Try Again !",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}