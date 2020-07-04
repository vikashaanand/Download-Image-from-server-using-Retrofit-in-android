package com.example.downloadimagefrommysqlserverusingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText edtSn;
    private Button btnDownload;

    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        edtSn = findViewById(R.id.edtImageSn);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });

    }

    private void downloadImage() {

        int  sn = Integer.parseInt(edtSn.getText().toString().trim());

        Call<ImagePOJO> call = RetroClient.getInstance().getApi().downloadImage(sn);
        call.enqueue(new Callback<ImagePOJO>() {
            @Override
            public void onResponse(Call<ImagePOJO> call, Response<ImagePOJO> response) {

                ImagePOJO image = response.body();

                if(image != null){
                       encodedImage = image.getEncodedImage();

                       byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                       Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

                       imageView.setImageBitmap(decodedImage);
                }else{
                    Toast.makeText(MainActivity.this, "Invalid SN", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImagePOJO> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}