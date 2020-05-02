package com.example.doantotnghiep.ui.QuanLyThuChi;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterThongKe;
import com.example.doantotnghiep.model.ArrayThongKe;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThuChiNgayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThuChiThangFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Activity a;
    private String[] arrtong;
    private ArrayList<ArrayThongKe> arrthu, arrchi;
    private ExpandableListView listView;
    private AdapterThongKe adapterlistview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThuChiThangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThuChiNgayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThuChiNgayFragment newInstance(String param1, String param2) {
        ThuChiNgayFragment fragment = new ThuChiNgayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thu_chi_thang, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();
        setListView();
    }

    public void setListView() {
        arrtong = getResources().getStringArray(R.array.thongketong);
        arrthu = new ArrayList<ArrayThongKe>();
        arrchi = new ArrayList<ArrayThongKe>();
        listView = (ExpandableListView)a.findViewById(R.id.lvThongKeThang);
        adapterlistview = new AdapterThongKe(this.a, arrtong, arrthu, arrchi);
        listView.setAdapter(adapterlistview);
    }

}
