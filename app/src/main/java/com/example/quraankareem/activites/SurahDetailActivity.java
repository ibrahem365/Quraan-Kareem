package com.example.quraankareem.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quraankareem.R;
import com.example.quraankareem.adapter.SurahDetailAdapter;
import com.example.quraankareem.common.Common;
import com.example.quraankareem.model.SurahDetail;
import com.example.quraankareem.viewmodel.SurahDetailViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailActivity extends AppCompatActivity {

    private TextView surahName,surahType,surahTranslation;
    private int no;
    RecyclerView recyclerView;
    private List<SurahDetail> list;
    private SurahDetailAdapter adapter;
    private SurahDetailViewModel surahDetailViewModel;
    private String urdu = "urdu_junagarhi";
    private String german = "german_bubenheim";
    private String english = "english_hilali_khan";

    private EditText searchView;
    private ImageButton settingButton;

    private RadioGroup radioGroup;
    private RadioButton translationButton;

    private String lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

        no = getIntent().getIntExtra(Common.SURAH_NO,0);
        surahName.setText(getIntent().getStringExtra(Common.SURAH_NAME));
        surahType.setText(getIntent().getStringExtra(Common.SURAH_TYPE)+""+
                getIntent().getIntExtra(Common.SURAH_TOTAL_AYA,0)+" AYA");
        surahTranslation.setText(getIntent().getStringExtra(Common.SURAH_TRANSLATION));

        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        surahTranslationApi(urdu,no);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SurahDetailActivity.this,
                        R.style.BottomSheetDialogTheme);

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view =inflater.inflate(R.layout.bottom_sheet_layout,
                        findViewById(R.id.sheet_container));

                view.findViewById(R.id.save_settings_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radioGroup = view.findViewById(R.id.translation_group);

                        int selectionId = radioGroup.getCheckedRadioButtonId();
                        translationButton = view.findViewById(selectionId);
                        if (selectionId == -1){
                            Toast.makeText(SurahDetailActivity.this, "noting selected", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SurahDetailActivity.this, "selected", Toast.LENGTH_SHORT).show();
                        }

                        if (translationButton.getText().toString().toLowerCase().trim().equals("urdu")){
                            lan = urdu;
                        }else if (translationButton.getText().toString().toLowerCase().trim().equals("german")){
                            lan = german;
                        }else if (translationButton.getText().toString().toLowerCase().trim().equals("english")){
                            lan = english;
                        }

                        surahTranslationApi(lan,no);

                        bottomSheetDialog.dismiss();

                    }
                });

                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

            }
        });

    }

    private void filter(String id) {
        ArrayList<SurahDetail> arrayList = new ArrayList<>();
        for (SurahDetail detail : list){
            if (String.valueOf(detail.getId()).contains(id)){
                arrayList.add(detail);
            }
        }
        adapter.filter(arrayList);
    }

    private void init(){
        surahName = findViewById(R.id.surah_name);
        surahType = findViewById(R.id.type);
        surahTranslation = findViewById(R.id.translation);
        recyclerView = findViewById(R.id.surah_detail_rv);

        searchView = findViewById(R.id.search_view);
        settingButton = findViewById(R.id.settings_button);
    }

    private void surahTranslationApi(String lan, int id) {

        if (list.size()>0){
            list.clear();
        }
        surahDetailViewModel = new ViewModelProvider(this).get(SurahDetailViewModel.class);
        surahDetailViewModel.getSurahDetail(lan, id).observe(this, surahDetailResponse ->{
            for (int i=0; i<surahDetailResponse.getList().size();i++){
                list.add(new SurahDetail(surahDetailResponse.getList().get(i).getId(),
                        surahDetailResponse.getList().get(i).getSura(),
                        surahDetailResponse.getList().get(i).getAya(),
                        surahDetailResponse.getList().get(i).getArabic_text(),
                        surahDetailResponse.getList().get(i).getTranslation(),
                        surahDetailResponse.getList().get(i).getFootnotes()
                        ));
            }

            if (list.size()!=0){
                adapter = new SurahDetailAdapter(this,list);
                recyclerView.setAdapter(adapter);
            }

        });
    }
}