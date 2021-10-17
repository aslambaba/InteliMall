package com.example.sairamkrishna.intelimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sairamkrishna.intelimall.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

public class RegistrationEctivity extends AppCompatActivity {

    Button registerBTN;
    TextInputLayout username,useremail,userphone,useraddress,userpassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_ectivity);
        //this.getSupportActionBar().hide();
        registerBTN = (Button) findViewById(R.id.registerbtn);
        username = (TextInputLayout) findViewById(R.id.username);
        useremail = (TextInputLayout) findViewById(R.id.useremail);
        userphone = (TextInputLayout) findViewById(R.id.userphonenumber);
        useraddress = (TextInputLayout) findViewById(R.id.useraddress);
        userpassword = (TextInputLayout) findViewById(R.id.userpassword);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username.getEditText().getText().toString();
                String email = useremail.getEditText().getText().toString();
                String phonenumber = userphone.getEditText().getText().toString();
                String address = useraddress.getEditText().getText().toString();
                String password = userpassword.getEditText().getText().toString();

                Toast.makeText(RegistrationEctivity.this, name,
                        Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegistrationEctivity.this, "Please Enter Your Username",
                        Toast.LENGTH_SHORT).show();
                        return;
                }
                else if(TextUtils.isEmpty(phonenumber)){
                    Toast.makeText(RegistrationEctivity.this, "Please Enter Your Phone Number",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(TextUtils.isEmpty(address)){
                    Toast.makeText(RegistrationEctivity.this, "Please Enter Your Username",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(TextUtils.isEmpty(email) ){
                    Toast.makeText(RegistrationEctivity.this, "Please Enter You EMail with Correct Format",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
               else if(TextUtils.isEmpty(password) || password.length() < 6 ){
                    Toast.makeText(RegistrationEctivity.this, "Please Enter Your Password and It must be greater then 6 digits",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                mAuth = FirebaseAuth.getInstance();


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationEctivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information


                                    Log.d("yes", "createUserWithEmail:success");
                                    User user = new User(name,email,phonenumber,address);

                                    mDatabase = FirebaseDatabase.getInstance().getReference("users");
                                    mDatabase.child(Objects.requireNonNull(mAuth.getUid())).setValue(user);


                                    Toast.makeText(RegistrationEctivity.this, "Registration Successfull.",
                                            Toast.LENGTH_SHORT).show();

                                    mAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(RegistrationEctivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegistrationEctivity.this, "You Are Logged In",
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent it = new Intent (RegistrationEctivity.this, HomeDrawer.class);
                                                        startActivity(it);
                                                    } else {
                                                        Toast.makeText(RegistrationEctivity.this, "Loginin failed. Try Again !",
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent it2 = new Intent (RegistrationEctivity.this, MainActivity.class);
                                                        startActivity(it2);
                                                    }
                                                }
                                            });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("nooooo", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegistrationEctivity.this, "Registration failed. Try Again !",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}