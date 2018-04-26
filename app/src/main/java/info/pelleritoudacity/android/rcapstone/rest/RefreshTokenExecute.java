package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.model.RedditToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

// todo error retrofit state code 400
public class RefreshTokenExecute {
    private final RefreshTokenManager refreshTokenManager;
    private RedditToken mRedditToken;

    public RefreshTokenExecute(String refreshToken) {
        refreshTokenManager = RefreshTokenManager.getInstance(refreshToken);
    }

    public void loginData(final RestRefreshToken myCallBack) {
        Callback<RedditToken> callback = new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                Timber.d("second refresh authentication problem value %s",response.raw().toString()  );
                if (response.isSuccessful()) {
                    mRedditToken = response.body();
                    //todo bug: if login ... logout.... and then login ..... value  in %s is null
                    myCallBack.onRestRefreshToken(mRedditToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorRefreshToken(t);
                }
            }
        };
        refreshTokenManager.getLoginAPI(callback);
    }

    public interface RestRefreshToken {

        void onRestRefreshToken(RedditToken listenerData);
        void onErrorRefreshToken(Throwable t);
    }
}
