package com.oletob.rpncalc.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oletob.rpncalc.R;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<HistoryHolder>{

    public HistoryAdapter(Context context, ArrayList<HistoryHolder> history) {
        super(context, 0, history);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        HistoryHolder entry = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_list, parent, false);
        }
        // Lookup view for data population
        ((TextView) convertView.findViewById(R.id.txtOperation)).setText(entry.operation);
        ((TextView) convertView.findViewById(R.id.txtResult)).setText(entry.result);


        // Return the completed view to render on screen
        return convertView;
    }
}
