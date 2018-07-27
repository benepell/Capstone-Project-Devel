package info.pelleritoudacity.android.rcapstone.data.rest.deserialize;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestDetailManager {

    private static RedditAPI sCommentsAPI;
    private static RestDetailManager sRestDetailManager;
    private final WeakReference< Context>  mWeakContext;
    private final boolean mIsAuthenticate;
    private final String mAccessToken;
    private final String mNameRedditId;
    private final String mSubRedditName;
    private final HashMap<String, String> mFieldMap;
    private Call<List<T1>> mCall;


    private RestDetailManager(WeakReference<Context> weakContext, String token, String subRedditName, String nameRedditId, boolean isAuthenticate, String sortBy) {

        mWeakContext = weakContext;
        mIsAuthenticate = isAuthenticate;
        mAccessToken = token;
        mSubRedditName = subRedditName;
        mNameRedditId = nameRedditId;

        String strTimeSort = Preference.getTimeSort(mWeakContext.get());

        mFieldMap = new HashMap<>();
        mFieldMap.put("depth", String.valueOf(Preference.getGeneralSettingsDepthPage(mWeakContext.get())));
        mFieldMap.put("limit", String.valueOf(Preference.getGeneralSettingsItemPage(mWeakContext.get())));
        mFieldMap.put("showedits", "false");
        mFieldMap.put("showmore", Costant.SHOW_MORE_COMMENTS);
        if(!TextUtils.isEmpty(strTimeSort)){
            mFieldMap.put("t",strTimeSort );

        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        String baseUrl;

        if (isAuthenticate) {
            baseUrl = Costant.REDDIT_OAUTH_URL;
        } else {
            baseUrl = Costant.REDDIT_BASE_URL;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(buildGsonConverter())
                .build();
        sCommentsAPI = retrofit.create(RedditAPI.class);

    }

    private static GsonConverterFactory buildGsonConverter() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(T1.class, new T1Deserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }

    public static RestDetailManager getInstance(WeakReference<Context> weakContext, String accessToken, String subRedditName, String nameRedditId, boolean isAuthenticate, String sortBy) {
        if (sRestDetailManager != null) {
            sRestDetailManager.cancelRequest();
        }

        sRestDetailManager = new RestDetailManager(weakContext,accessToken, subRedditName, nameRedditId, isAuthenticate,sortBy);


        return sRestDetailManager;
    }

    public void getCommentsAPI(Callback<List<T1>> callback) {
       String sortBy = Preference.getSubredditSort(mWeakContext.get());
        if (mIsAuthenticate) {
            mCall = sCommentsAPI.getCommentsAuth(Costant.REDDIT_BEARER + mAccessToken,
                    mSubRedditName, mNameRedditId,
                    sortBy,
                    mFieldMap);
        } else {
            mCall = sCommentsAPI.getComments(mSubRedditName, mNameRedditId,sortBy,mFieldMap);

        }

        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
