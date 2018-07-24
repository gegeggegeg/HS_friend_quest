package com.android.peter.hs_friend_quest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface redditService {
    @GET("r/hearthstone/comments/8kfhxk/battletag_findafriend_witchwood_edition/")
    Call<ResponseBody> getCall();
}
