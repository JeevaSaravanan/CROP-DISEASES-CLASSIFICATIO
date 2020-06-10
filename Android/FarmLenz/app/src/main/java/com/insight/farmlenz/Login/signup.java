package com.insight.farmlenz.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.insight.farmlenz.MainActivity;
import com.insight.farmlenz.R;

public class signup extends AppCompatActivity {
    TextView tv;
    private FirebaseAuth mAuth;
    EditText emailId,pass;
    Button bt;
    String email,password;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(signup.this, MainActivity.class));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tv=findViewById(R.id.sign);
        emailId=findViewById(R.id.Email);
        pass=findViewById(R.id.pass);
        mAuth=FirebaseAuth.getInstance();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(signup.this,loginpage.class);
                finish();
                startActivity(i);
            }
        });


        bt=findViewById(R.id.signbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailId.getText().toString();
                password= pass.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i=new Intent(signup.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}