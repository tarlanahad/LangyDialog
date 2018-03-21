package com.tarlanahad.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tarlanahad.fontableviews.FontableTextView;
import com.tarlanahad.langydialog.LangyDialog;
import com.tarlanahad.langydialog.OnLanguageSelectListener;

public class MainActivity extends AppCompatActivity {

    LangyDialog dialog;
    ImageView mBtnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLanguage = findViewById(R.id.i);

        mBtnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new LangyDialog(MainActivity.this, new OnLanguageSelectListener() {
                    @Override
                    public void OnLanguageSelectListener(String lang, int position) {
                        ((FontableTextView) (findViewById(R.id.tv))).setText(lang);
                        dialog.cancel();
                    }
                });
                dialog.setItemsFont("Lato-Light.ttf").show();
            }
        });

    }
}
