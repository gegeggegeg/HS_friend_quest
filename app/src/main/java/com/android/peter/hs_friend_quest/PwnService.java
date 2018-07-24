package com.android.peter.hs_friend_quest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PwnService {
    @GET("forums/search?search-thread-id=214403&search-forum-id=&forum-scope=f&display-type=0&search=Battletag&search-type=0&by-author=&begin-date=07%2F23%2F2018&begin-time=&end-date=&end-time=&min-posts=&min-views=&display-type=0&submit=y")
    Call<ResponseBody> url();
}
