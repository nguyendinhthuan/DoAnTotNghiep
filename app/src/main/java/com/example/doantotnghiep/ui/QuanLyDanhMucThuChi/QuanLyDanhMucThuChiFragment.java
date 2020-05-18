package com.example.doantotnghiep.ui.QuanLyDanhMucThuChi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterDanhMucThuChi;
import com.example.doantotnghiep.model.ArrayDanhMucThuChi;
import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDanhMucThuChiFragment extends Fragment {
    private View myFragment;
    private String taikhoan;
    private Activity activity;
    private SQLiteDatabase data;
    private Animation animation;
    private String[] arrSpinner;
    private ArrayAdapter<String> adapterSpinner;
    private Spinner spinner_LoaiThuChi2, spinner_LoaiThuChi3;
    private ImageButton button_ThemDanhMuc;
    private EditText editText_TenDanhMucThuChi;
    private ArrayList<ArrayDanhMucThuChi> arrDanhMucThuChi;
    private AdapterDanhMucThuChi adapterDanhMucThuChi;
    private List<ArrayDanhMucThuChi> list = null;
    private ListView listView_DanhMucThuChi;

//    public QuanLyDanhMucThuChiFragment(String taikhoan) {
//        this.taikhoan = taikhoan;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_quanlydanhmucthuchi, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);

        AnhXa();
        LoadSpinner();
        LayDanhSachDanhMucThuChi();
        XoaDanhMuc();
    }

    public void AnhXa() {
        listView_DanhMucThuChi = (ListView) myFragment.findViewById(R.id.listView_DanhMucThuChi);

        spinner_LoaiThuChi2 = (Spinner) myFragment.findViewById(R.id.spinner_LoaiThuChi2);
        spinner_LoaiThuChi3 = (Spinner) myFragment.findViewById(R.id.spinner_LoaiThuChi3);

        button_ThemDanhMuc = (ImageButton) myFragment.findViewById(R.id.button_ThemDanhMuc);
        button_ThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemDanhMuc();
            }
        });

        editText_TenDanhMucThuChi = (EditText) myFragment.findViewById(R.id.editText_TenDanhMucThuChi);
    }

    public void LoadSpinner() {
        //Spinner Loai thu chi
        arrSpinner = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChi2.setAdapter(adapterSpinner);
        spinner_LoaiThuChi2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LayDanhSachDanhMucThuChi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_LoaiThuChi3.setAdapter(adapterSpinner);
    }

    public void ThemDanhMuc() {
        if (editText_TenDanhMucThuChi.getText().toString().equals("")) {
            editText_TenDanhMucThuChi.setAnimation(animation);
            Toast.makeText(getActivity(), "Bạn chưa nhập tên danh mục", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            dialog.setTitle("Thông báo !");
            dialog.setMessage("Bạn có muốn thêm danh mục này ?");
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int madanhmuc = 1;
                    Cursor cursor = data.rawQuery("select madanhmuc from tbldanhmucthuchi", null);
                    if (cursor.moveToLast() == true) {
                        madanhmuc = cursor.getInt(cursor.getColumnIndex("madanhmuc")) + 1;
                    }
                    String thongbao = "";
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("madanhmuc", madanhmuc);
                    contentValues.put("tendanhmuc", editText_TenDanhMucThuChi.getText().toString());
                    contentValues.put("loaikhoan", spinner_LoaiThuChi3.getSelectedItem().toString());
                    contentValues.put("tentaikhoan", taikhoan);

                    if (data.insert("tbldanhmucthuchi", null, contentValues) != -1) {
                        thongbao = "Thêm danh mục thành công";
                        editText_TenDanhMucThuChi.setText(null);
                        //load list view o day
                        LayDanhSachDanhMucThuChi();
                    } else {
                        thongbao = "Thêm danh mục thất bại";
                    }
                    Toast.makeText(getActivity(), thongbao, Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
    }

    public void LayDanhSachDanhMucThuChi() {
        String dieukien = "= '" + spinner_LoaiThuChi2.getSelectedItem().toString() + "'";
        Cursor cursor = data.rawQuery("select madanhmuc, tendanhmuc, loaikhoan from tbldanhmucthuchi where loaikhoan" + dieukien + "", null);
        //Cursor cursor = data.query("tbldanhmucthuchi", null, null, null, null, null, null);
        cursor.moveToFirst();
        list = new ArrayList<ArrayDanhMucThuChi>();
        while (cursor.isAfterLast() == false) {
            ArrayDanhMucThuChi a = new ArrayDanhMucThuChi();
            a.setMadanhmuc(cursor.getInt(0));
            a.setTendanhmuc(cursor.getString(1));
            a.setLoaikhoan(cursor.getString(2));
            list.add(a);

            cursor.moveToNext();
        }
        cursor.close();

        adapterDanhMucThuChi = new AdapterDanhMucThuChi(getContext(), R.layout.fragment_quanlydanhmuc_item, list);
        listView_DanhMucThuChi.setAdapter(adapterDanhMucThuChi);

    }

    public void XoaDanhMuc() {
        listView_DanhMucThuChi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HamXoaDanhMuc(list.get(position).tendanhmuc);
                return false;
            }
        });
    }

    public void HamXoaDanhMuc(final String tendanhmuc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn có chắc chắn muốn xóa danh mục này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tbldanhmucthuchi where tendanhmuc = '" + tendanhmuc + "'", null).moveToFirst();
                LayDanhSachDanhMucThuChi();
                Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
