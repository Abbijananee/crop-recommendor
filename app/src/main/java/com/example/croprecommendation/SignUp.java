package com.example.croprecommendation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.PhoneNo);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
        mAuth= FirebaseAuth.getInstance();

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Dashboard.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                if(!validateName() || !validateUsername() || !validateEmail() || !validatePhoneNo() || !validatePassword())
                {
                    Toast.makeText(SignUp.this,"Please try again later",Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(SignUp.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(SignUp.this,Dashboard.class));
                }

                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelper helper = new UserHelper(name,username,email,phoneNo,password);
                reference.push().setValue(helper);
                reg_user();

            }
            public void reg_user() {


                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                if (!validateName() || !validateUsername() || !validateEmail() || !validatePhoneNo() || !validatePassword()) {
                    Toast.makeText(SignUp.this, "Please try again later", Toast.LENGTH_SHORT);
                } else {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, Dashboard.class));
                            } else {
                                Toast.makeText(SignUp.this, "please try again later" + task.getException().getMessage(), Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }


            private Boolean validateName() {
                String val = regName.getEditText().getText().toString();
                if(val.isEmpty()){
                    regName.setError("Field cannot be empty");
                    return false;
                }
                else
                {
                    regName.setError(null);
                    regUsername.setErrorEnabled(false);
                    return true;
                }
            }


            private Boolean validateUsername() {
                String val = regUsername.getEditText().getText().toString();
                //String noWhiteSpace = "(?=\\s+$)";
                if(val.isEmpty()){
                    regUsername.setError("Field cannot be empty");
                    return false;
                }
                /*else if(val.length()<=15){
                    regUsername.setError("Username too long");
                    return false;
                }
                else if(!val.matches(noWhiteSpace)){
                    regUsername.setError("Whitespaces are not allowed");
                    return false;
                }*/
                else
                {
                    regUsername.setError(null);
                    regUsername.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validateEmail(){
                String val = regEmail.getEditText().getText().toString();
                String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(val.isEmpty()){
                    regEmail.setError("Field cannot be empty");
                    return false;
                }
                else if(!val.matches(ePattern)){
                    regEmail.setError("Invalid Email Address");
                    return false;
                }
                else
                {
                    regEmail.setError(null);
                    regEmail.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validatePhoneNo(){
                String val = regPhoneNo.getEditText().getText().toString();
                if(val.isEmpty()){
                    regPhoneNo.setError("Field cannot be empty");
                    return false;
                }
                else
                {
                    regPhoneNo.setError(null);
                    regPhoneNo.setErrorEnabled(false);
                    return true;
                }
            }


            private Boolean validatePassword(){
                String val = regPassword.getEditText().getText().toString();
                /*String passVal = "^" +
                        "(?=.*[a-zA-Z])"+
                        "(?=.*[@#$%^&+=])"+
                        "(?=\\s+$)"+
                        ".{4,}"+
                        "$";*/
                if(val.isEmpty()){
                    regPassword.setError("Field cannot be empty");
                    return false;
                }
                /*else if(!val.matches(passVal)){
                    regPassword.setError("Password is too weak");
                    return false;
                }*/
                else
                {
                    regPassword.setError(null);
                    regPassword.setErrorEnabled(false);
                    return true;
                }
            }

        });

    }
}