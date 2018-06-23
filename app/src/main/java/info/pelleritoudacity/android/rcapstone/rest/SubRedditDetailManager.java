package info.pelleritoudacity.android.rcapstone.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SubRedditDetailManager {

    private static RedditAPI sCommentsAPI;
    private static SubRedditDetailManager sSubRedditDetailManager;
    private final boolean mIsAuthenticate;
    private final String mAccessToken;
    private final String mNameRedditId;
    private final String mSubRedditName;
    private HashMap<String, String> mFieldMap;
    private Call<List<T1>> mCall;

    private SubRedditDetailManager(String token, String subRedditName, String nameRedditId, boolean isAuthenticate) {

        mIsAuthenticate = isAuthenticate;
        mAccessToken = token;
        mSubRedditName = subRedditName;
        mNameRedditId = nameRedditId;


        mFieldMap = new HashMap<>();
        mFieldMap.put("depth", Costants.LIMIT_DEPTH_RESULTS);
        mFieldMap.put("limit", Costants.LIMIT_COMMENTS_RESULTS);
        mFieldMap.put("showedits", "false");
        mFieldMap.put("showmore", "true");
// todo add sort
        //        mFieldMap.put("sort", sortBy);


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // todo remove interceptor
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        String baseUrl;

        if (isAuthenticate) {
            baseUrl = Costants.REDDIT_OAUTH_URL;
        } else {
            baseUrl = Costants.REDDIT_BASE_URL;
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

    public static SubRedditDetailManager getInstance(String accessToken, String subRedditName, String nameRedditId, boolean isAuthenticate) {
        if (sSubRedditDetailManager != null) {
            sSubRedditDetailManager.cancelRequest();
        }

        sSubRedditDetailManager = new SubRedditDetailManager(accessToken, subRedditName, nameRedditId, isAuthenticate);


        return sSubRedditDetailManager;
    }

    public void getCommentsAPI(Callback<List<T1>> callback) {
        if (mIsAuthenticate) {
            mCall = sCommentsAPI.getCommentsAuth(Costants.REDDIT_BEARER + mAccessToken,
                    mSubRedditName, mNameRedditId, mFieldMap);
        } else {
            mCall = sCommentsAPI.getComments(mSubRedditName, mNameRedditId, mFieldMap);

        }

        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
