package com.example.doantotnghiep.ui.QuanLyVi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Activity activity;
    private ImageButton button_ThemVi;
    private Button button_Reload;
    private GridView listView_Vi;
    private View myFragment;
    private ArrayList<ArrayVi> arrayVi;
    private AdapterVi adapterVi;
    private SQLiteDatabase data;
    private Database database;
    private ArrayAdapter arrayAdapter;
    private Animation animation;
    private List<ArrayVi> list = null;

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
        button_Reload = (Button) myFragment.findViewById(R.id.button_Reload);
        button_Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayDanhSachVi();
                Toast.makeText(activity, "Load thanh cong", Toast.LENGTH_SHORT).show();
            }
        });




        //setListView();
        //setuplistview();
        //setListView_Vi();
        //setArrListView_Vi();

        return myFragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
    }

    public void LayDanhSachVi() {
        Cursor cursor = data.query("tblvi", null, null, null, null, null, null);
        cursor.moveToFirst();
        list = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            ArrayVi a = new ArrayVi();
            a.setMavi(cursor.getInt(0));
            a.setTenvi(cursor.getString(1));
            a.setMotavi(cursor.getString(2));
            a.setSotien(cursor.getString(3));
            list.add(a);
            cursor.moveToNext();
        }
        cursor.close();
        adapterVi = new AdapterVi(getContext(), R.layout.fragment_quanlyvi_item, list);
        listView_Vi = (GridView) myFragment.findViewById(R.id.gridView_Vi);
        listView_Vi.setAdapter(adapterVi);
    }

//    public void setListView_Vi() {
//        arrayVi = new ArrayList<ArrayVi>();
//        listView_Vi = (GridView) myFragment.findViewById(R.id.gridView_Vi);
//        adapterVi = new AdapterVi(getActivity(), R.layout.fragment_quanlyvi_item, arrayVi);
//        listView_Vi.setAdapter(adapterVi);
//    }
//
//    public void setArrListView_Vi() {
//        arrayVi.clear();
//        Cursor cursor = data.rawQuery("select * from tblvi", null);
//        cursor.moveToFirst();
//        while (cursor.isAfterLast() == false) {
//            arrayVi.add(new ArrayVi(cursor.getInt(cursor.getColumnIndex("mavi")), cursor.getString(cursor.getColumnIndex("tenvi")), cursor.getString(cursor.getColumnIndex("motavi")), cursor.getString(cursor.getColumnIndex("sotien"))));
//            cursor.moveToNext();
//        }
//        adapterVi.notifyDataSetChanged();
//    }

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

//    public void setuplistview() {
//        ArrayList<String> list = new ArrayList<>();
//        ArrayList<ArrayVi> listVi = new ArrayList<>();
//        listVi = database.LayDanhSachVi();
//        for (ArrayVi arrayVi : listVi) {
//            list.add(arrayVi.getTenvi() + "");
//            list.add(arrayVi.getMotavi());
//            list.add(arrayVi.getSotien());
//        }
//        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
//        listView_Vi = (GridView) myFragment.findViewById(R.id.gridView_Vi);
//        listView_Vi.setAdapter(arrayAdapter);
//
//    }
}