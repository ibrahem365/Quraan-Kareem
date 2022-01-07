package com.example.quraankareem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quraankareem.R;
import com.example.quraankareem.model.SurahDetail;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailAdapter extends RecyclerView.Adapter<SurahDetailAdapter.SurahDetailViewHolder>{

    private Context context;
    private List<SurahDetail> list;

    public SurahDetailAdapter(Context context, List<SurahDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SurahDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SurahDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.surah_datail_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SurahDetailViewHolder holder, int position) {

        holder.ayaNo.setText(String.valueOf(list.get(position).getAya()));
        holder.arabicText.setText(list.get(position).getArabic_text());
        holder.translation.setText(list.get(position).getTranslation());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filter(ArrayList<SurahDetail> details){
        list = details;
        notifyDataSetChanged();
    }

    public class SurahDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView ayaNo,arabicText,translation;

        public SurahDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            ayaNo = itemView.findViewById(R.id.aya_no);
            arabicText = itemView.findViewById(R.id.arabic_text);
            translation = itemView.findViewById(R.id.translation);

        }
    }

}
