package info.pelleritoudacity.android.rcapstone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginJson {
    @SerializedName("data")
    @Expose
    private LoginData mLoginData;

    public LoginData getData() {
        return mLoginData;
    }

    public void setData(LoginData data) {
        mLoginData = data;
    }

    @Override
    public String toString() {
        return "Json{" +
                "data=" + mLoginData +
                '}';
    }
}
