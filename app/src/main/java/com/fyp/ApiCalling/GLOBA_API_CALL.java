package com.fyp.ApiCalling;


import com.fyp.ModelsClass.Orders;

import com.fyp.ModelsClass.ProviderModel;
import com.fyp.ModelsClass.SpinnerModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface GLOBA_API_CALL {


    @FormUrlEncoded
    @POST(GlobalInfo.ProviderLogin)
    Call<ResponseBody> providerLogin(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST(GlobalInfo.AddProduct)
    Observable<ResponseBody> add_product(
            @Field("image") String image,
            @Field("service_id") String service_id,
            @Field("provider_id") String provider_id,
            @Field("name") String name,
            @Field("price") String price,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST(GlobalInfo.Register_provider)
    Call<ResponseBody> register_provider(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("contact") String contact,
            @Field("othercontact") String othercontact,
            @Field("des") String des,
            @Field("address") String address,
            @Field("service") String service
    );

    @FormUrlEncoded
    @POST(GlobalInfo.user_reg)
    Call<ResponseBody> user_reg(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST(GlobalInfo.update_provider)
    Call<ResponseBody> update_provider(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("pass") String pass,
            @Field("contact") String contact,
            @Field("othercontact") String othercontact,
            @Field("des") String des,
            @Field("address") String address,
            @Field("user_id") String user_id
    );

    @GET(GlobalInfo.Provider)
    Observable<ProviderModel> getProvider();

    @GET(GlobalInfo.Orders)
    Observable<Orders> getOrders();

    @GET
    Call<ResponseBody> getProducts(@Url String url);

    @GET
    Call<ResponseBody> removeCart(@Url String url);

    @GET
    Call<ResponseBody> addToCart(@Url String url);

    @GET(GlobalInfo.Service_list)
    Observable<SpinnerModel> getService_list();

    @FormUrlEncoded
    @POST(GlobalInfo.Provider_Details)
    Observable<ResponseBody> getProvider_details(@Field("user_id") String user_id);
}