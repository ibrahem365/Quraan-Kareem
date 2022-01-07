package com.example.quraankareem.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.quraankareem.repository.SurahRepo;
import com.example.quraankareem.response.SurahResponse;

public class SurahViewModel extends ViewModel {

    private SurahRepo surahRepo;

    public SurahViewModel() {
        surahRepo = new SurahRepo();
    }

    public LiveData<SurahResponse> getSurah(){
        return surahRepo.getSurah();
    }
}
