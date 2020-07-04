package com.example.downloadimagefrommysqlserverusingretrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("download_image.php")
    Call<ImagePOJO> downloadImage(
            @Field("SN") int sn
    );

}
