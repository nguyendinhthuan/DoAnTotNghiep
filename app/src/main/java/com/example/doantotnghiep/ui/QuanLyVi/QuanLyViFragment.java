package com.example.doantotnghiep.ui.QuanLyVi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.doantotnghiep.Database;
import com.example.doantotnghiep.MainActivity;
import com.example.doantotnghiep.R;
import com.example.doantotnghiep.ViActivity;
import com.example.doantotnghiep.adapter.AdapterVi;
import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;
import java.util.List;

public class QuanLyViFragment extends Fragment {

    private QuanLyViViewModel galleryViewModel;
    private ImageButton button_ThemVi;
    private GridView listView_Vi;
    private View myFragment;
    private ArrayList<ArrayVi> arrayVi;
    private AdapterVi adapterVi;
    private SQLiteDatabase data;
    private Database database;
    private ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_quanlyvi, container, false);
        button_ThemVi = (ImageButton) myFragment.findViewById(R.id.button_ThemVi);
        button_ThemVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViActivity.class);
                startActivity(i);
            }
        });
        //setListView();
        //setuplistview();
        return myFragment;
    }

//    public void setListView() {
//        arrayVi = new ArrayList<ArrayVi>();
//        listView_Vi = (GridView) myFragment.findViewById(R.id.gridView_Vi);
//        adapterVi = new AdapterVi(getActivity(), R.layout.fragment_quanlyvi_item, arrayVi);
//        listView_Vi.setAdapter(adapterVi);
//    }

//    public void setArrListView() {
//        arrayVi.clear();
//        Cursor c = data.rawQuery("select mavi, tenvi, motavi, sotien from tblvi", null);
//        c.moveToFirst();
//        while (c.isAfterLast() == false) {
//            arrayVi.add(new ArrayVi(c.getInt(c.getColumnIndex("mavi")), c.getString(c.getColumnIndex("tenvi")), c.getString(c.getColumnIndex("motavi")), c.getInt(c.getColumnIndex("sotien"))));
//            c.moveToNext();
//        }
//        adapterVi.notifyDataSetChanged();
//
//    }

    public void setuplistview() {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<ArrayVi> listVi = new ArrayList<>();
        listVi = database.LayDanhSachVi();
        for (ArrayVi arrayVi : listVi) {
            list.add(arrayVi.getTenvi() + "");
            list.add(arrayVi.getMotavi());
            list.add(arrayVi.getSotien());
        }
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        listView_Vi = (GridView) myFragment.findViewById(R.id.gridView_Vi);
        listView_Vi.setAdapter(arrayAdapter);

    }
}