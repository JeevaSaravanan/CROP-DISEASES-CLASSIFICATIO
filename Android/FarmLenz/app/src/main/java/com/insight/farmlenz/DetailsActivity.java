package com.insight.farmlenz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.insight.farmlenz.Model.dismodel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {

    String data="",dimg="";
    dismodel obj=null;
    ImageView imgAct,imgReal;
    TextView posDis,disCause,disSymp,bioCntrl,chemCntrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        data=getIntent().getStringExtra("data");
        dimg=getIntent().getStringExtra("dimg");

        posDis=findViewById(R.id.posDis);
        disCause=findViewById(R.id.disCause);
        disSymp=findViewById(R.id.disSymp);
        bioCntrl=findViewById(R.id.bioCntrl);
        chemCntrl=findViewById(R.id.chemCntrl);
        imgAct=findViewById(R.id.imgAct);
        imgReal=findViewById(R.id.imgReal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseDatabase.getInstance().getReference().child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obj=dataSnapshot.getValue(dismodel.class);
                setValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setValue()
    {
        Intent i=new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(obj==null) return;

        posDis.setText(obj.getDisease());
        disCause.setText(obj.getCause());
        disSymp.setText(obj.getSymptoms());
        bioCntrl.setText(obj.getBiological_Control());
        chemCntrl.setText(obj.getChemical_Control());
       // imgAct.setImageBitmap(StringToImage(dimg));
        Glide.with(this).load(obj.getImage()).fitCenter().override(1000,1000).into(imgReal);
    }
    @Override
    public void onBackPressed(){
            super.onBackPressed();

    }
    public Bitmap StringToImage(String img) {

        Bitmap bitmap= null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
