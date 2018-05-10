package info.pelleritoudacity.android.rcapstone.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RevokeTokenExecute {
    private final RevokeTokenManager revokeTokenManager;

    public RevokeTokenExecute(String token, String typeToken) {
        revokeTokenManager = RevokeTokenManager.getInstance(token, typeToken);
    }

    public void RevokeTokenData(final RestRevokeCode myCallBack) {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    myCallBack.onRestCode(response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    Timber.e("Revoke Token Error %s", t.getMessage());
                }
            }
        };
        revokeTokenManager.getLoginAPI(callback);
    }


    public void RevokeTokenSync(final Context context) {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if(response.code()== Costants.REDDIT_REVOKE_SUCCESS){
                        PrefManager.putLongPref(context, R.string.pref_time_token,0);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    Timber.e("Sync Revoke Token Error %s", t.getMessage());
                }
            }
        };
        revokeTokenManager.getLoginAPI(callback);
    }


    public interface RestRevokeCode {
        void onRestCode(Integer listenerData);
    }

}
