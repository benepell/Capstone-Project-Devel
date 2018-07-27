package info.pelleritoudacity.android.rcapstone.data.rest.deserialize;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.rest.More;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestMoreManager {

    private static RedditAPI sCommentsAPI;
    private static RestMoreManager sRestMoreManager;
    private final WeakReference<Context> mWeakContext;
    private final String mAccessToken;
    private HashMap<String, String> mFieldMap;
    private Call<More> mCall;
    private final DetailModel model;


    private RestMoreManager(WeakReference<Context> weakContext, String token, DetailModel model) {

        mWeakContext = weakContext;
        mAccessToken = token;
        this.model = model;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // todo remove interceptor
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        String baseUrl;

        baseUrl = Costant.REDDIT_OAUTH_URL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(buildGsonConverter())
//                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sCommentsAPI = retrofit.create(RedditAPI.class);

    }


    private static GsonConverterFactory buildGsonConverter() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(More.class, new MoreDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }


    public static RestMoreManager getInstance(WeakReference<Context> weakContext, String accessToken, DetailModel model) {
       /* if (sRestMoreManager != null) {
            sRestMoreManager.cancelRequest();
        }
*/
        sRestMoreManager = new RestMoreManager(weakContext, accessToken, model);


        return sRestMoreManager;
    }

    public void getMoreCommentsAPI(Callback<More> callback) {

        mFieldMap = new HashMap<>();
//        mFieldMap.put("sort", Preference.getSubredditSort(mWeakContext.get()));
        mFieldMap.put("api_type", "json");
        mFieldMap.put("link_id", model.getStrLinkId());
        mFieldMap.put("children",model.getStrArrId());


      /*  if (!TextUtils.isEmpty(Preference.getTimeSort(mWeakContext.get()))) {
            mFieldMap.put("t", Preference.getTimeSort(mWeakContext.get()));

        }
      */
        mCall = sCommentsAPI.getMoreCommentsAuth(Costant.REDDIT_BEARER + mAccessToken,
                mFieldMap);

        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
