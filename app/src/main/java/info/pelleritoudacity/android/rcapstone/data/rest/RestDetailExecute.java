package info.pelleritoudacity.android.rcapstone.data.rest;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestDetailExecute {

    private static RedditAPI sApi;
    private final OnRestCallBack mCallback;
    private final String mCode;
    private final DetailModel mModel;
    private final boolean isAutheticate;
    private final Context mContext;

    public RestDetailExecute(OnRestCallBack callback, Context context, String code, DetailModel model) {

        if (PermissionUtil.isLogged(context)) {
            sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, T1.class);
            isAutheticate = true;
        } else {
            sApi = RetrofitClient.createService(Costant.REDDIT_BASE_URL, T1.class);
            isAutheticate = false;
        }

        mCallback = callback;
        mContext = context;
        mCode = code;
        mModel = model;

    }

    public void getData() {


        HashMap<String, String> mFieldMap;

        mFieldMap = new HashMap<>();
        mFieldMap.put("depth", String.valueOf(Preference.getGeneralSettingsDepthPage(mContext)));
        mFieldMap.put("limit", String.valueOf(Preference.getGeneralSettingsItemPage(mContext)));
        mFieldMap.put("showedits", "false");
        mFieldMap.put("showmore", Costant.SHOW_MORE_COMMENTS);

        String strTimeSort = Preference.getTimeSort(mContext);
        if (!TextUtils.isEmpty(strTimeSort)) {
            mFieldMap.put("t", strTimeSort);
        }

        if(!TextUtils.isEmpty( Preference.getSubredditSort(mContext))){
            Preference.setSubredditSort(mContext,Costant.DEFAULT_SORT_BY);
        }

        if (isAutheticate) {
            sApi.getCommentsAuth(TextUtil.authCode(mCode),
                    Preference.getLastCategory(mContext), mModel.getStrId(),
                    strTimeSort,
                    mFieldMap).enqueue(new Callback<List<T1>>() {
                @Override
                public void onResponse(@NonNull Call<List<T1>> call, @NonNull Response<List<T1>> response) {
                    mCallback.success(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<List<T1>> call, @NonNull Throwable t) {
                    mCallback.unexpectedError(t);
                }
            });


        } else {

            sApi.getComments(Preference.getLastCategory(mContext), mModel.getStrId(), strTimeSort, mFieldMap)
                    .enqueue(new Callback<List<T1>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<T1>> call, @NonNull Response<List<T1>> response) {
                            mCallback.success(response.body());
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<T1>> call, @NonNull Throwable t) {
                            mCallback.unexpectedError(t);
                        }
                    });

        }
    }

    public interface OnRestCallBack {

        void success(List<T1> response);

        void unexpectedError(Throwable tList);
    }

}

