package com.future.it.taxi.client.net;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by monir.sobuj on 5/25/17.
 */

public interface APIServices {

    String TAG = APIServices.class.getSimpleName();
    //jsonblob
    @GET("zone/read.php")
    Observable<ZoneResponse> getZone();

    @GET("client/read.php")
    Observable<ClientResponse> getClients();


    @FormUrlEncoded
    @POST("upload/update_user.php")
    Observable<UpdateResponse> updateClient(
            @Field("client_id") String UserID,
            @Field("name") String name,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("z_id") String zoneId,
            @Field("image_photo") String imgPhoto,
            @Field("image_sign") String imgSign,
            @Field("image_nid") String imgNID
    );


}
