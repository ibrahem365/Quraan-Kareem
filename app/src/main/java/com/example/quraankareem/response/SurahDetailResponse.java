package com.example.quraankareem.response;

import com.example.quraankareem.model.SurahDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahDetailResponse {

    @SerializedName("result")
    private List<SurahDetail> list;

    public List<SurahDetail> getList() {
        return list;
    }

    public void setList(List<SurahDetail> list) {
        this.list = list;
    }
}
