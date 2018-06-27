/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.service;


import java.util.List;
import java.util.Map;

import info.pelleritoudacity.android.rcapstone.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RedditAPI {
    @GET("/r/.json")
    Call<T5> getReddit();

    @GET("/r/{subreddit_name}/{sort}/.json")
    Call<T3> getSubReddit(
            @Path(value = "subreddit_name", encoded = true) String subreddit_name,
            @Path(value = "sort", encoded = true) String sortBy,
            @QueryMap Map<String, String> options
    );

    @GET("/r/{subreddit_name}/{sort}/.json")
    @Headers("User-Agent: " + Costants.REDDIT_USER_AGENT)
    Call<T3> getSubRedditAuth(@Header("Authorization") String authorization,
                              @Path(value = "subreddit_name", encoded = true) String subreddit_name,
                              @Path(value = "sort", encoded = true) String sortBy,
                              @QueryMap Map<String, String> options
    );

    @GET("/r/{subreddit_name}/comments/{id}/{sort}/.json")
    Call<List<T1>> getComments(
            @Path(value = "subreddit_name", encoded = true) String subreddit_name,
            @Path(value = "id", encoded = true) String strId,
            @Path(value = "sort", encoded = true) String sortBy,
            @QueryMap Map<String, String> options
    );

    @GET("/r/{subreddit_name}/comments/{id}/{sort}/.json")
    @Headers("User-Agent: " + Costants.REDDIT_USER_AGENT)
    Call<List<T1>> getCommentsAuth(@Header("Authorization") String authorization,
                                   @Path(value = "subreddit_name", encoded = true) String subreddit_name,
                                   @Path(value = "id", encoded = true) String strId,
                                   @Path(value = "sort", encoded = true) String sortBy,

                                   @QueryMap Map<String, String> options
    );

    @FormUrlEncoded
    @POST("/api/v1/access_token")
    Call<RedditToken> getAccessToken(
            @HeaderMap Map<String, String> headers,
            @FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("/api/v1/revoke_token")
    Call<String> getRevokeToken(
            @HeaderMap Map<String, String> headers,
            @FieldMap Map<String, String> fields);

    @GET("/api/v1/me")
    Call<RedditAboutMe> getAboutMe(
            @Header(Costants.REDDIT_AUTHORIZATION) String authorization);

    @FormUrlEncoded
    @POST("/api/vote")
    @Headers("User-Agent: " + Costants.REDDIT_USER_AGENT)
    Call<ResponseBody> postVote(@Header("Authorization") String authorization,
                                @Field("dir") String dir,
                                @Field("id") String nameReddit);


}
