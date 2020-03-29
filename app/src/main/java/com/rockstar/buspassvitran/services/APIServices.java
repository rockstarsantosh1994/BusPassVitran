package com.rockstar.buspassvitran.services;


import com.rockstar.buspassvitran.model.BusPassData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by rockstar on 06-05-2017.
 */

public interface APIServices {

    @POST("getbusspassdata.php")
    Call<List<BusPassData>> busspass_list();

   /* @FormUrlEncoded()
    @POST("saurabh/user/register_user.php")
    Call<JSONResult> register_user(@Field("user_name") String user_name, @Field("user_lastname") String user_lastname, @Field("user_email") String user_mail_id, @Field("user_mobile") String user_mobile, @Field("user_address") String user_address, @Field("user_password") String user_password);

    @FormUrlEncoded()
    @POST("saurabh/user/login.php")
    Call<BasicInfo> login(@Field("user_mobile") String user_mobile, @Field("user_password") String user_password);

    @POST("saurabh/user/category_list.php")
    Call<List<ImageModel>> image_list();

    @POST("saurabh/user/about_list.php")
    Call<List<About>> about_list();*/



    /*@FormUrlEncoded()
    @POST("saurabh/user/pay_fees.php")
    Call<JSONResult> enroll_stud(@Field("fees_fname") String fees_fname, @Field("fees_lname") String fees_lname, @Field("fees_emailid") String fees_emailid, @Field("fees_add") String fees_add, @Field("fees_mob") String fees_mob, @Field("user_id") String user_id, @Field("fees_paid") String fees_paid, @Field("fees_bal") int fees_bal);
*/

    /*@POST("user/get_all_record.php")
    Call<List<Job>> job_list();*/


   /* @FormUrlEncoded()
    @POST("user/check_otp.php")
    Call<Otp> check_otp(@Field("user_login_code") String user_login_code);

    @POST("category/show_all_category_list.php")
    Call<List<ImageModel>> image_list();

    @FormUrlEncoded()
    @POST("items/items_list.php")
    Call<List<Items>> items_list(@Field("category_id") int cate_ID);

    @FormUrlEncoded()
    @POST("cart/add_to_cart.php")
    Call<JSONResult> add_to_cart(@Field("user_id") String anInt, @Field("cart_iteam_name") String item_name, @Field("cart_rate") int item_rate, @Field("cart_quantity") String s, @Field("cart_amount") String s1);

    @FormUrlEncoded()
    @POST("cart/select_cart_list.php")
    Call<List<Cart>> cart_list(@Field("user_id") String cate_ID);

    @FormUrlEncoded()
    @POST("cart/delete_cart_item.php")
    Call<JSONResult> deleteCartItem(@Field("cart_id") int cart_id);

    @FormUrlEncoded()
    @POST("order/place_order.php")
    Call<JSONResult> placeOrder(@Field("user_id") String anInt, @Field("place_order_id") int count, @Field("place_order_cart_item_name") String cart_iteam_name, @Field("place_order_cart_item_rate") String cart_rate, @Field("place_order_cart_item_qty") String cart_quantity, @Field("place_order_cart_item_rs") String cart_amount);

    @FormUrlEncoded()
    @POST("cart/flushcart.php")
    Call<JSONResult> flushCart(@Field("user_id") int user_id);*/

    /*@FormUrlEncoded()
    @POST("cart/add_to_cart.php")
    Call<JSONResult> add_to_cart(@Field("user_id") int user_id, @Field("cart_iteam_name") String item_name, @Field("cart_rate")int item_rate, @Field("cart_quantity") String s, @Field("cart_amount")String s1);*/
}
