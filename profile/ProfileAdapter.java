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

public class ProfileAdapter extends ArrayAdapter<SearchProfile> {
    Context context;
    List<SearchProfile> arrayListSearch;

    public ProfileAdapter(@NonNull Context context, List<SearchProfile> arrayListUser) {
        super(context, R.layout.recycle_diary_din,arrayListUser);

        this.context = context;
        this.arrayListSearch = arrayListSearch;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_diary_din,null,true);

        //TextView tvID = view.findViewById(R.id.txt_id);
        TextView tvName = view.findViewById(R.id.night_text);

        //tvID.setText(arrayListEmployee.get(position).getId());
        tvName.setText(arrayListSearch.get(position).getName());

        return view;
    }
}
