package com.tarlanahad.langydialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.tarlanahad.fontableviews.FontableEditText;
import com.tarlanahad.fontableviews.FontableTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LangyDialog extends Dialog {

    private Activity activity;

    private CardView mCardView;

    private FontableEditText mEtSearch;

    private FontableTextView mPositiveBtn;

    private FontableTextView mNegativeBtn;

    private RecyclerView mList;

    private OnLanguageSelectListener onLanguageSelectListener;

    public LangyDialog(@NonNull Activity activity, OnLanguageSelectListener onLanguageSelectListener) {
        super(activity);
        this.activity = activity;
        this.onLanguageSelectListener = onLanguageSelectListener;
        show();
        dismiss();
    }

    public LangyDialog(Activity activity, OnLanguageSelectListener onLanguageSelectListener, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
        this.onLanguageSelectListener = onLanguageSelectListener;
        show();
        dismiss();
    }

    private String ItemFont = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        requestWindowFeature(1);
        setContentView(R.layout.dailog_langy);

        mCardView = findViewById(R.id.container);

        mList = findViewById(R.id.list);

        mList.setLayoutManager(new LinearLayoutManager(activity));

        final LangyListAdapter adapter = new LangyListAdapter(LanguageList.getHumanReadable());

        mList.setAdapter(adapter);

        mEtSearch = findViewById(R.id.searchEt);

        mEtSearch.setFont(ItemFont);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            filter(charSequence.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mCardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int heightOfView = mCardView.getHeight();
                FrameLayout.LayoutParams cardViewParams = new FrameLayout.LayoutParams(Utils.getScreenDimensions(activity)[0] - (int) Utils.convertDpToPixel(40, activity), ViewGroup.LayoutParams.WRAP_CONTENT);
                mCardView.setLayoutParams(cardViewParams);
                if (heightOfView > Utils.getScreenDimensions(activity)[1] - Utils.convertDpToPixel(200, activity)) {
                    ViewGroup.LayoutParams params = mList.getLayoutParams();
                    params.height = (int) (Utils.getScreenDimensions(activity)[1] - Utils.convertDpToPixel(200, activity));
                    mList.setLayoutParams(params);
                }
            }
        });


    }

    private class LangyListAdapter extends RecyclerView.Adapter<LangyListAdapter.Holder> {

        String[] languages;

        public LangyListAdapter(String[] languages) {
            this.languages = languages;
        }

        public class Holder extends RecyclerView.ViewHolder {
            View Container;
            FontableTextView mLangTv;

            public Holder(View view) {
                super(view);
                this.mLangTv = (FontableTextView) view.findViewById(R.id.lang);
                this.Container = view.findViewById(R.id.container);
            }
        }

        public void onBindViewHolder(Holder holder, final int position) {

            holder.mLangTv.setFont(ItemFont);
            holder.mLangTv.setText(languages[position]);
            holder.Container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLanguageSelectListener.OnLanguageSelectListener(languages[position], position);
                }
            });

        }

        public int getItemCount() {
            return languages.length;
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_langy_list_item, parent, false));
        }
    }

    public LangyDialog setItemsFont(String fontName) {
        this.ItemFont = fontName;
        return this;
    }

    private void filter(String text) throws Exception {

        List<String> itemsCopy = Arrays.asList(LanguageList.getHumanReadable());
        final List<String> items = new ArrayList<>();
        items.clear();
        if (text.isEmpty()) {
            items.addAll(itemsCopy);
        } else {
            text = text.toLowerCase();
            for (String item : itemsCopy) {
                if (item.toLowerCase().contains(text)) {
                    items.add(item);
                }
            }
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mList.setAdapter(new LangyListAdapter(items.toArray(new String[items.size()])));
            }
        });
    }
}