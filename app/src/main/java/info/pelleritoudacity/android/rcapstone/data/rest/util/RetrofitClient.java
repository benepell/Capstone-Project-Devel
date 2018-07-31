package info.pelleritoudacity.android.rcapstone.data.rest.util;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.More;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit sRetrofit = null;
    private static String sBaseUrl;

    private static Retrofit getClient(String baseUrl, Class clazz) {
        if (sRetrofit == null) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            GsonConverterFactory gsonConverterFactory;

            if (clazz != null) {
                if (clazz.equals(T1.class)) {
                    gsonBuilder.registerTypeAdapter(T1.class, new T1Deserializer());

                } else if (clazz.equals(More.class)) {
                    gsonBuilder.registerTypeAdapter(More.class, new MoreDeserializer());

                }

                Gson gson = gsonBuilder.create();
                gsonConverterFactory = GsonConverterFactory.create(gson);
            } else {
                gsonConverterFactory = GsonConverterFactory.create();

            }

            sBaseUrl = baseUrl;

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClient())
                    .addConverterFactory(gsonConverterFactory)
                    .build();

        }

        return sRetrofit;
    }

    private static OkHttpClient getOkHttpClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

    }

    private OkHttpClient getOkHttpClientDebug() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

    }

    public static RedditAPI createService(String baseUrl, Class clazz) {

        if ((clazz != null) ||
                (!TextUtils.isEmpty(sBaseUrl) && (sBaseUrl.compareTo(baseUrl) != 0))) {
            sRetrofit = null;
        }
        return RetrofitClient.getClient(baseUrl, clazz).create(RedditAPI.class);
    }

}

