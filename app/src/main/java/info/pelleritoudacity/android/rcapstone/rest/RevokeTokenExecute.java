package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RevokeTokenExecute {
    private final RevokeTokenManager revokeTokenManager;
    String mString;
    public RevokeTokenExecute(String token,String typeToken) {
        revokeTokenManager = RevokeTokenManager.getInstance(token,typeToken);
    }

    public void RevokeTokenData(final RestRevokeCode myCallBack) {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Timber.d("benResp %s", response.raw().toString());
                if (response.isSuccessful()) {
                    myCallBack.onRestCode(response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                        Timber.e("Revoke Token Error %s",t.getMessage());
                }
            }
        };
        revokeTokenManager.getLoginAPI(callback);
    }

    public interface RestRevokeCode{
            void onRestCode(Integer listenerData);
    }

}
