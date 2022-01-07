package com.example.quraankareem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.quraankareem.activites.SurahDetailActivity;
import com.example.quraankareem.adapter.SurahAdapter;
import com.example.quraankareem.common.Common;
import com.example.quraankareem.listener.SurahListener;
import com.example.quraankareem.model.Surah;
import com.example.quraankareem.response.SurahResponse;
import com.example.quraankareem.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurahListener {

    private RecyclerView recyclerView;
    private SurahAdapter surahAdapter;
    private List<Surah> list;
    private SurahViewModel surahViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.surahRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);
        surahViewModel.getSurah().observe(this, new Observer<SurahResponse>() {
            @Override
            public void onChanged(SurahResponse surahResponse) {
                for (int i=0; i<surahResponse.getList().size(); i++){
                    list.add(new Surah(surahResponse.getList().get(i).getNumber(),
                            String.valueOf(surahResponse.getList().get(i).getName()),
                            String.valueOf(surahResponse.getList().get(i).getEnglishName()),
                            String.valueOf(surahResponse.getList().get(i).getEnglishNameTranslation()),
                            surahResponse.getList().get(i).getNumberOfAyahs(),
                            String.valueOf(surahResponse.getList().get(i).getRevelationType())
                            ));
                }

                if (list.size()!=0){
                    surahAdapter = new SurahAdapter(MainActivity.this,list,MainActivity.this::onSurahListener);
                    recyclerView.setAdapter(surahAdapter);
                    surahAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(MainActivity.this, SurahDetailActivity.class);
        intent.putExtra(Common.SURAH_NO,list.get(position).getNumber());
        intent.putExtra(Common.SURAH_NAME,list.get(position).getName());
        intent.putExtra(Common.SURAH_TOTAL_AYA,list.get(position).getNumberOfAyahs());
        intent.putExtra(Common.SURAH_TYPE,list.get(position).getRevelationType());
        intent.putExtra(Common.SURAH_TRANSLATION,list.get(position).getEnglishNameTranslation());
        startActivity(intent);
    }
}