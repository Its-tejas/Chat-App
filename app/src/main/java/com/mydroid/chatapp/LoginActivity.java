package com.mydroid.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.mydroid.chatapp.Models.Users;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    EditText edttxtEmail2, edttxtPass2;
    Button btnSignin;
    TextView txtvsignin;
    FirebaseDatabase mdatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edttxtEmail2 = findViewById(R.id.edttxtEmail);
        edttxtPass2 = findViewById(R.id.edttxtpassword);
        btnSignin   = findViewById(R.id.btnSignin);
        txtvsignin  = findViewById(R.id.txtvsignin);

        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance();

        progressDialog =new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Please wait\nValidation in progress...");

        btnSignin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!edttxtEmail2.getText().toString().isEmpty() && !edttxtPass2.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword( edttxtEmail2.getText().toString(), edttxtPass2.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Users users = new Users();

                                        users.setUserid(user.getUid());
                                        users.setEmail(user.getEmail());
                                        users.setName(user.getDisplayName());
                                        users.setProfilepic(user.getPhotoUrl().toString());

                                        mdatabase.getReference().child("Users").child(user.getUid().toString()).setValue(users);

                                        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "You are not registerd user\nPlease sign up", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(intent);
        }

        txtvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            currentUser.reload();
        }
    }
}