package com.example.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter<SearchDiary> {
    Context context;
    List<SearchDiary> arrayListSearch;


    public DiaryAdapter(@NonNull Context context, List<SearchDiary> arrayListEmployee) {
        super(context, R.layout.recycle_diary_break,arrayListEmployee);

        this.context = context;
        this.arrayListSearch = arrayListSearch;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_diary_break,null,true);

        //TextView tvID = view.findViewById(R.id.txt_id);
        TextView tvTitle = view.findViewById(R.id.morn_text);

        //tvID.setText(arrayListEmployee.get(position).getId());
        tvTitle.setText(arrayListSearch.get(position).gettitle());

        return view;
    }
}
