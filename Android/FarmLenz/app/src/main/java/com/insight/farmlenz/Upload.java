package com.insight.farmlenz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class Upload extends AppCompatActivity {

    private Bitmap btmap=null;
    private String  fruit="Grape";
    private String imgbtmap;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 108;
    private static  final int REQUEST_IMAGE_CAPTURE=101;
    private static final int PICK_IMAGE_REQUEST=102;
    ImageView disp,cam,file;
        EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);



        Button submit=findViewById(R.id.submit);
        editText=findViewById(R.id.id_ET_link);
        fruit=getIntent().getStringExtra("fruit");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btmap!=null) {
                    imgbtmap = imagetoString(btmap);
                    Volley(imgbtmap,fruit,editText.getText().toString());
                }
            }
        });
        disp=findViewById(R.id.disp);
        cam=findViewById(R.id.cam);
        file=findViewById(R.id.file);

        requestCameraPermission();

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchUploadPictureIntent();
            }
        });

    }


    private void dispatchUploadPictureIntent() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PICK_IMAGE_REQUEST);
        }

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            showSnackbar(R.string.phonePermissionRationale, android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(Upload.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE);
                }
            });
        } else {
            ActivityCompat.requestPermissions(Upload.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {

        Snackbar.make(this.findViewById(android.R.id.content), getString(mainTextStringId), Snackbar.LENGTH_INDEFINITE).setAction(getString(actionStringId), listener).show();
    }


    public void Volley(String img, String leafname, String link){
        try{
            System.out.println("booo"+img);
            // Intent i=new Intent(Upload.this,DetailsActivity.class);
            //i.putExtra("data",fruit+"_"+"Esca");
            //startActivity(i);




            JSONObject object=new JSONObject();
            object.put("Image",img);
            object.put("Leaf",leafname);


            JsonObjectRequest jSONObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,"http://"+link+".ngrok.io", object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response != null) {
                                System.out.println(response);
                                try {
                                    Toast.makeText(Upload.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Upload.this,DetailsActivity.class);
                                    i.putExtra("data",fruit+"_"+response.getString("result"));
                                    finish();
                                    startActivity(i);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse networkResponse = error.networkResponse;
                            if(networkResponse!=null){
                                int statusCode = networkResponse.statusCode;
                                if(statusCode== HttpURLConnection.HTTP_UNAUTHORIZED){
                                }
                                else{
                                }
                                error.printStackTrace();
                            }
                        }
                    }
            );
            MyVolleyRequest.getInstance(Upload.this).addToRequestQueue(jSONObjectRequest);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public String imagetoString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
          Bitmap  yourSelectedImage = (Bitmap) extras.get("data");
            Toast.makeText(Upload.this, "ok", Toast.LENGTH_SHORT).show();
            //ListImages.add(imageBitmap);
            btmap=yourSelectedImage;
            disp.setImageBitmap(yourSelectedImage);
            //postImageAdapter.UpdatePostAdapter(ListImages);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText( Upload.this, "ok", Toast.LENGTH_SHORT).show();
            System.out.println("ok");
            if (data.getData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    clipData.getItemAt(i);
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                       Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                       btmap=yourSelectedImage;
                        disp.setImageBitmap(yourSelectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
