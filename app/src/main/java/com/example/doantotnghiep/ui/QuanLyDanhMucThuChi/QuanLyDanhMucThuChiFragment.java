package com.example.doantotnghiep.ui.QuanLyDanhMucThuChi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    private String[] arrSpinner, arrSpinnerDialog;
    private ArrayAdapter<String> adapterSpinner, adapterSpinnerDialog, adapterViUuTienDialog;
    private Spinner spinner_LoaiKhoan, spinner_LoaiKhoanDialog, spinner_ViUuTienChoDanhMuc;
    private ImageButton button_ThemDanhMuc;
    private EditText editText_TenDanhMucThuChi;
    private ArrayList<ArrayDanhMucThuChi> arrDanhMucThuChi;
    private AdapterDanhMucThuChi adapterDanhMucThuChi;
    private List<ArrayDanhMucThuChi> list = null;
    private List<ArrayVi> listViDialog = null;
    private ListView listView_DanhMucThuChi;
    private SharedPreferences sharedPreferences;
    private Button button_LuuThemDanhMucThuChi, button_HuyThemDanhMucThuChi;
    private ArrayList<Integer> arrMaViDialog;
    private ArrayList<String> arrTenViDialog;

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

        sharedPreferences = getActivity().getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");

        AnhXa();
        LoadSpinner();
        LoadTatCaDanhMucThuChi();
        XoaDanhMuc();
        ThemDanhMucThuChiDialog();
    }

    public void AnhXa() {
        listView_DanhMucThuChi = (ListView) myFragment.findViewById(R.id.listView_DanhMucThuChi);

        spinner_LoaiKhoan = (Spinner) myFragment.findViewById(R.id.spinner_LoaiKhoan);

        button_ThemDanhMuc = (ImageButton) myFragment.findViewById(R.id.button_ThemDanhMuc);

        editText_TenDanhMucThuChi = (EditText) myFragment.findViewById(R.id.editText_TenDanhMucThuChi);
    }

    public void LoadSpinner()  {
        //Spinner Loai thu chi
        arrSpinner = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinner = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiKhoan.setAdapter(adapterSpinner);
        spinner_LoaiKhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadTatCaDanhMucThuChi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void LoadSpinnerDialog() {
        //Spinner loai khoan dialog
        arrSpinnerDialog = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinnerDialog = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrSpinnerDialog);
        adapterSpinnerDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiKhoanDialog.setAdapter(adapterSpinnerDialog);

        //Spinner vi dialog
        arrMaViDialog = new ArrayList<Integer>();
        arrTenViDialog = new ArrayList<String>();
        adapterViUuTienDialog = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrTenViDialog);
        adapterViUuTienDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ViUuTienChoDanhMuc.setAdapter(adapterViUuTienDialog);
    }

    public void LoadTatCaDanhMucThuChi() {
        String dieukien = "= '" + spinner_LoaiKhoan.getSelectedItem().toString() + "'";
        Cursor cursor = data.rawQuery("select madanhmuc, tendanhmuc, loaikhoan from tbldanhmucthuchi" +
                " where loaikhoan" + dieukien + " and tentaikhoan = '" + taikhoan + "'", null);
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

        adapterDanhMucThuChi = new AdapterDanhMucThuChi(getContext(), R.layout.adapter_quanlydanhmucthuchi_item, list);
        listView_DanhMucThuChi.setAdapter(adapterDanhMucThuChi);
    }

    public void LoadDanhSachViLenSpinnerDialog() {
        arrMaViDialog.clear();
        arrTenViDialog.clear();
        Cursor cursor = data.rawQuery("select * from tblvi where tentaikhoan = '" + taikhoan +"'", null);
        cursor.moveToFirst();
        listViDialog = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            arrMaViDialog.add(cursor.getInt(cursor.getColumnIndex("mavi")));
            arrTenViDialog.add(cursor.getString(cursor.getColumnIndex("tenvi")));
            cursor.moveToNext();
        }
        adapterViUuTienDialog.notifyDataSetChanged();
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
                data.rawQuery("delete from tbldanhmucthuchi " +
                        "where tendanhmuc = '" + tendanhmuc + "'", null).moveToFirst();
                LoadTatCaDanhMucThuChi();
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

    public void ThemDanhMucThuChiDialog() {
        button_ThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
                final Dialog d = new Dialog(getActivity());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_themdanhmucthuchi);
                d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                d.show();

                //Anh xa
                editText_TenDanhMucThuChi = (EditText) d.findViewById(R.id.editText_TenDanhMucThuChi);
                spinner_LoaiKhoanDialog = (Spinner) d.findViewById(R.id.spinner_LoaiKhoanDialog);
                spinner_ViUuTienChoDanhMuc = (Spinner) d.findViewById(R.id.spinner_ViUuTienChoDanhMuc);
                button_LuuThemDanhMucThuChi = (Button) d.findViewById(R.id.button_LuuThemDanhMucThuChi);
                button_HuyThemDanhMucThuChi = (Button) d.findViewById(R.id.button_HuyThemDanhMucThuChi);

                //Xu ly
                LoadSpinnerDialog();
                LoadDanhSachViLenSpinnerDialog();

                button_HuyThemDanhMucThuChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                button_LuuThemDanhMucThuChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String thongbao = "";
                        int madanhmuc = 1;
                        boolean tendanhmuc = true;
                        Cursor cursor = data.rawQuery("select madanhmuc, tendanhmuc from tbldanhmucthuchi", null);
                        cursor.moveToFirst();
                        while (cursor.isAfterLast()==false) {
                            if (cursor.getString(cursor.getColumnIndex("tendanhmuc")).equals(editText_TenDanhMucThuChi.getText().toString())) {
                                tendanhmuc = false;
                            }
                            cursor.moveToNext();
                        }
                        if (tendanhmuc == false) {
                            thongbao = "Tên danh mục này đã tồn tại";
                            editText_TenDanhMucThuChi.startAnimation(animation);
                        } else if (editText_TenDanhMucThuChi.getText().toString().equals("")) {
                            thongbao = "Bạn chưa nhập tên danh mục";
                            editText_TenDanhMucThuChi.startAnimation(animation);
                        } else if (cursor.moveToLast() == true) {
                            madanhmuc = cursor.getInt(cursor.getColumnIndex("madanhmuc")) + 1;
                        }

                        //Da sua luu ma vi sang int
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("madanhmuc", madanhmuc);
                        contentValues.put("tendanhmuc", editText_TenDanhMucThuChi.getText().toString());
                        contentValues.put("loaikhoan", spinner_LoaiKhoanDialog.getSelectedItem().toString());
                        contentValues.put("mavi",  arrMaViDialog.get(spinner_ViUuTienChoDanhMuc.getSelectedItemPosition()));
                        contentValues.put("tentaikhoan", taikhoan);

                        if (data.insert("tbldanhmucthuchi", null, contentValues) != -1) {
                            thongbao = "Thêm danh mục thành công";
                            d.dismiss();
                            //load list view o day
                            LoadTatCaDanhMucThuChi();
                        }
                        Toast.makeText(getActivity(), thongbao, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
