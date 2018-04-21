package info.pelleritoudacity.android.rcapstone.rest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.Login;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginManager {

    private static RedditAPI sRedditAPI;
    private static LoginManager sLoginManager;
    private Call<Login> mCall;

    HashMap<String, String> headerMap;

    private String mUsername;
    private String mPassword;


    private LoginManager(String username, String password) {

        mUsername = username;
        mPassword = password;

        headerMap = new HashMap<>();
        headerMap.put(Costants.CONTENT_TYPE, Costants.APPLICATION_JSON);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.LOGIN_API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sRedditAPI = retrofit.create(RedditAPI.class);

    }

    public static LoginManager getInstance(String username, String password) {
        if (sLoginManager == null) {
            sLoginManager = new LoginManager(username, password);
        }

        return sLoginManager;
    }

    public void getLoginAPI(Callback<Login> callback) {
        mCall = sRedditAPI.getLogin(headerMap, Costants.LOGIN_API_USERNAME, mUsername, mPassword, Costants.JSON_TYPE);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
