package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;


public class WebviewActivity extends AppCompatActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.reddit_webview)
    protected WebView mWebview;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        ButterKnife.bind(this);

        getWebview(Preference.getLastCategory(getApplicationContext()));

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getWebview(String category) {

        String url;
        if (!category.startsWith("/")) {
            category = "r/".concat(category);
        }

        url = Costant.REDDIT_BASE_URL + category + "/" + "submit";

        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebview, true);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (url.contains(Costant.REDDIT_WEBVIEW_USE_APP)) {

                    mWebview.setVisibility(View.INVISIBLE);
                    finish();

                }else if (url.contains("https://www.reddit.com/.compact") ||
                        url.equals("http://" + Costant.REDDIT_ABOUT_URL + "/.compact")) {

                    mWebview.stopLoading();
                    mToast = Toast.makeText(getApplicationContext(), "Reddit Site", Toast.LENGTH_LONG);
                    mToast.show();

                }

            }
        });
        mWebview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(mToast!=null){
            mToast.cancel();
        }
        super.onBackPressed();

    }
}
