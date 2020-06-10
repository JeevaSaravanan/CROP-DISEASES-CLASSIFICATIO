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

public class loginpage extends AppCompatActivity {
    TextView tv1;
    private FirebaseAuth mAuth;
    EditText emailId,pass;
    Button bt;
    String email,password;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Intent i=new Intent(loginpage.this, MainActivity.class);
            finish();
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        mAuth=FirebaseAuth.getInstance();
        tv1=findViewById(R.id.log);
        bt=findViewById(R.id.logbt);
        emailId=findViewById(R.id.Email);
        pass=findViewById(R.id.pass);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(loginpage.this,signup.class));
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailId.getText().toString();
                password= pass.getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(loginpage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i=new Intent(loginpage.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(loginpage.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}