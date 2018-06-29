package info.pelleritoudacity.android.rcapstone.rest;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubRedditDetailManager {

    private static RedditAPI sCommentsAPI;
    private static SubRedditDetailManager sSubRedditDetailManager;
    private Context mContext;
    private final boolean mIsAuthenticate;
    private final String mAccessToken;
    private final String mNameRedditId;
    private final String mSubRedditName;
    private HashMap<String, String> mFieldMap;
    private Call<List<T1>> mCall;


    private SubRedditDetailManager(Context context, String token, String subRedditName, String nameRedditId, boolean isAuthenticate, String sortBy) {

        mContext = context;
        mIsAuthenticate = isAuthenticate;
        mAccessToken = token;
        mSubRedditName = subRedditName;
        mNameRedditId = nameRedditId;

        String strTimeSort = Preference.getTimeSort(mContext);

        mFieldMap = new HashMap<>();
        mFieldMap.put("depth", Costant.LIMIT_DEPTH_RESULTS);
        mFieldMap.put("limit", Costant.LIMIT_COMMENTS_RESULTS);
        mFieldMap.put("showedits", "false");
        mFieldMap.put("showmore", "true");
        if(!TextUtils.isEmpty(strTimeSort)){
            mFieldMap.put("t",strTimeSort );

        }


        // todo add sort


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // todo remove interceptor
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
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
//                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sCommentsAPI = retrofit.create(RedditAPI.class);

    }

    private static GsonConverterFactory buildGsonConverter() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(T1.class, new T1Deserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }

    public static SubRedditDetailManager getInstance(Context context, String accessToken, String subRedditName, String nameRedditId, boolean isAuthenticate, String sortBy) {
        if (sSubRedditDetailManager != null) {
            sSubRedditDetailManager.cancelRequest();
        }

        sSubRedditDetailManager = new SubRedditDetailManager(context,accessToken, subRedditName, nameRedditId, isAuthenticate,sortBy);


        return sSubRedditDetailManager;
    }

    public void getCommentsAPI(Callback<List<T1>> callback) {
       String sortBy = Preference.getSubredditSort(mContext);
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
