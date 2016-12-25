package com.ahmetturkmen.android.sqliteexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;
import com.ahmetturkmen.android.sqliteexample.database.Database;
import com.ahmetturkmen.android.sqliteexample.objects.Car;
import com.ahmetturkmen.android.sqliteexample.R;

public class carsAdapter extends BaseAdapter {

    private List<Car> carsList;
    private LayoutInflater layoutInflater;

    public carsAdapter(Context context){
        super();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        carsList = new Database(context).getCarsFromDatabase();
    }

    @Override
    public int getCount() {
        return carsList.size();
    }

    @Override
    public Car getItem(int position) {
        return carsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carsList.get(position).getId();
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {

        row = layoutInflater.inflate(R.layout.car_spinner_layout,null);

        TextView markModel = (TextView) row.findViewById(R.id.car_spinner_textView);

        markModel.setText(getItem(position).getId() + " - " + getItem(position).getBrand() + " - " + getItem(position).getModel() + " - " + getItem(position).getYear());

        return row;
    }
}
