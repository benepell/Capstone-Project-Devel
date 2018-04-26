package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.model.RedditToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AccessTokenExecute {
    private final AccessTokenManager accessTokenManager;
    private RedditToken mLogin;

    public AccessTokenExecute(String code) {
        accessTokenManager = AccessTokenManager.getInstance(code);
    }

    public void loginData(final RestAccessToken myCallBack) {
        Callback<RedditToken> callback = new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                if (response.isSuccessful()) {
                    mLogin = response.body();
                    //todo bug: if login ... logout.... and then login ..... value  in %s is null
                    myCallBack.onRestAccessToken(mLogin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorAccessToken(t);
                }
            }
        };
        accessTokenManager.getLoginAPI(callback);
    }

    public interface RestAccessToken {

        void onRestAccessToken(RedditToken listenerData);
        void onErrorAccessToken(Throwable t);
    }
}
