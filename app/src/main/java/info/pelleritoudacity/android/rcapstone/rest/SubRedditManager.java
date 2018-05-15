package info.pelleritoudacity.android.rcapstone.rest;

import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.T3;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubRedditManager {

    private static RedditAPI sSubRedditAPI;
    private static SubRedditManager sSubRedditManager;
    private final String mSubReddit;
    private Call<T3> mCall;

    private SubRedditManager(String subReddit) {
        mSubReddit = subReddit;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.REDDIT_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sSubRedditAPI = retrofit.create(RedditAPI.class);

    }

    public static SubRedditManager getInstance(String subReddit) {
        if (sSubRedditManager != null) {
            sSubRedditManager.cancelRequest();
        }

        sSubRedditManager = new SubRedditManager(subReddit);


        return sSubRedditManager;
    }

    public void getSubRedditAPI(Callback<T3> callback) {
        mCall = sSubRedditAPI.getSubReddit(mSubReddit);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
