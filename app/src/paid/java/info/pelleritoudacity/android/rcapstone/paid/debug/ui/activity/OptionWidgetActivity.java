package info.pelleritoudacity.android.rcapstone.paid.debug.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.paid.debug.ui.service.WidgetService;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class OptionWidgetActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preference.isNightMode(this)) {
            setTheme(R.style.AppThemeDark);
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_option_widget);

        TextView tvTitle = findViewById(R.id.text_option_widget_title);

        if (Preference.isNightMode(getApplicationContext())) {
            tvTitle.setBackgroundResource(R.color.colorBackgroundDark);
        }

        mStringList = TextUtil.stringToArray(Costant.DEFAULT_CATEGORY_WIDGET);

        RadioGroup radioGroup = findViewById(R.id.radio_group);

        for (String s : mStringList) {
            RadioButton rb = new RadioButton(this);
            rb.setText(s);
            rb.setTextSize(getResources().getInteger(R.integer.text_radio_widget));
            radioGroup.addView(rb);
        }

        if (mStringList != null) {
            radioGroup.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Preference.setWidgetCategory(getApplicationContext(), mStringList.get((checkedId - 1) % mStringList.size()));
        WidgetService.start(getApplicationContext());
        finish();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
