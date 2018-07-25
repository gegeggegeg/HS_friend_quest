package com.Peter.chen.hs_friend_quest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CallService {
    @GET("Hearthstone/M.1520254421.A.0DA.html")
    Call<ResponseBody> url();
}
