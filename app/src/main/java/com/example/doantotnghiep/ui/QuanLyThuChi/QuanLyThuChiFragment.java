package com.example.doantotnghiep.ui.QuanLyThuChi;

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
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterThongKe;
import com.example.doantotnghiep.adapter.AdapterThuChi;
import com.example.doantotnghiep.model.ArrayThongKe;
import com.example.doantotnghiep.model.ArrayThuChi;
import com.example.doantotnghiep.model.ArrayVi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuanLyThuChiFragment extends Fragment {
    View myFragment;
    private Activity activity;
    private SQLiteDatabase data;
    private Animation animation;
    private ImageButton imageButton_ThemThuChi;
    private Button button_ThoatThuChiDialog,button_LuuThuChiDialog,button_NgayThuChiDialog,button_GioThuChiDialog,button_ThoiGianHienTaiDialog;
    private QuanLyThuChiViewModel quanLyThuChiViewModel;
    private String taikhoan;
    private Spinner spinner_LocThuChi,spinner_LoaiThuChiDialog,spinner_ViDialog,spinner_DanhMucDialog;
    private String ngaythang;
    private Calendar today,todayDialog;
    private int thang, nam;
    private ArrayList<ArrayThongKe> arrthu, arrchi;
    private String[] arrGroup;
    private ListView listView_LichSuThuChi;
    private AdapterThongKe adapterThongKe;
    private ArrayList<ArrayThuChi> arr;
    private AdapterThuChi adapterThuChi;
    private SharedPreferences sharedPreferences;
    private List<ArrayThuChi> list = null;
    private ArrayList<String> arrSpinner;
    private ArrayAdapter<String> adapterSpinner;

    //DialogThem
    private EditText editText_SoTienThuChiDialog,editText_MoTaThuChiDialog;
    private SimpleDateFormat simpleDateFormatDialog;
    private Date dateDialog;
    private ArrayList<Integer> arrMaViDialog, arrMaDanhMucDialog;
    private ArrayList<String> arrTenViDialog, arrTenDanhMucDialog;
    private ArrayAdapter<String> adapterSpinnerDialog, adapterViDialog, adapterDanhMucDialog;
    private String[] arrSpinnerDialog;
    private List<ArrayVi> listDialog = null;
    private String gioDialog, tenVi;
    private Cursor cursor;
    private int sotientuvi,sotienthuchi,sotienchi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_quanlythuchi, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
        simpleDateFormatDialog = new SimpleDateFormat("dd/MM/yyyy");
        dateDialog = new Date();

        sharedPreferences = getActivity().getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");

        AnhXa();
        ThemThuChi();
        setListview();
//        LocCoSoDuLieu();
        //LocCoSoDuLieu();
        XoaThuChi();
        setSpinner();
        LoadNgayLenSpinner();
    }

    public void AnhXa() {
        imageButton_ThemThuChi = (ImageButton) myFragment.findViewById(R.id.imageButton_ThemThuChi);
        spinner_LocThuChi = (Spinner) myFragment.findViewById(R.id.spinner_LocThuChi);
        listView_LichSuThuChi = (ListView) myFragment.findViewById(R.id.listView_LichSuThuChi);
    }


    public void ThemThuChi() {
        imageButton_ThemThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
                final Dialog d = new Dialog(getActivity());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.activity_thu_chi);
                d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                d.show();

                //AnhXa
                button_ThoatThuChiDialog = (Button) d.findViewById(R.id.button_ThoatThuChi);
                button_LuuThuChiDialog = (Button) d.findViewById(R.id.button_LuuThuChi);
                button_NgayThuChiDialog = (Button) d.findViewById(R.id.button_NgayThuChi);
                button_GioThuChiDialog = (Button) d.findViewById(R.id.button_GioThuchi);
                button_ThoiGianHienTaiDialog = (Button) d.findViewById(R.id.button_ThoiGianHienTai);

                editText_SoTienThuChiDialog = (EditText) d.findViewById(R.id.editText_SoTienThuChi);
                editText_MoTaThuChiDialog = (EditText) d.findViewById(R.id.editText_MoTaThuChi);

                spinner_LoaiThuChiDialog = (Spinner) d.findViewById(R.id.spinner_LoaiThuChi);
                spinner_ViDialog = (Spinner) d.findViewById(R.id.spinner_Vi);
                spinner_DanhMucDialog = (Spinner) d.findViewById(R.id.spinner_DanhMuc);

                todayDialog = Calendar.getInstance();

                //Xu ly
                HienThiThoiGian();
                LoadSpinnerDialog();
                LoadDanhSachViLenSpinnerDialog();
                //KiemTraChiTieuMax();


                editText_SoTienThuChiDialog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(!b && editText_SoTienThuChiDialog.getText().toString()!= null)
                        {
                          KiemTraChiTieuMax();
                        }
                    }
                });
                button_ThoatThuChiDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                button_NgayThuChiDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChonNgay();
                    }
                });
                button_ThoiGianHienTaiDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HienThiThoiGian();
                    }
                });
                button_LuuThuChiDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editText_SoTienThuChiDialog.getText().toString().equals("")) {
                            editText_SoTienThuChiDialog.startAnimation(animation);
                            Toast.makeText(activity, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
                        } else if (editText_MoTaThuChiDialog.getText().toString().equals("")) {
                            editText_MoTaThuChiDialog.startAnimation(animation);
                            Toast.makeText(activity, "Bạn chưa nhập mô tả", Toast.LENGTH_SHORT).show();
                        } else {
                            ThemThuChiDialog();
                            d.dismiss();
                        }

                        LoadNgayLenSpinner();

                        //Delay ham xuat du lieu de tranh bi crash app
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadThuChiTheoSpinner();
                            }
                        }, 100 );

                    }
                });


            }
        });
    }

    //Dialog Them Thu Chi
    public void LoadSpinnerDialog() {
        //Spinner Danh muc
        arrMaDanhMucDialog = new ArrayList<Integer>();
        arrTenDanhMucDialog = new ArrayList<String>();
        adapterDanhMucDialog = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrTenDanhMucDialog);
        adapterDanhMucDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_DanhMucDialog.setAdapter(adapterDanhMucDialog);
        //Spinner Loai thu chi
        arrSpinnerDialog = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinnerDialog = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrSpinnerDialog);
        adapterSpinnerDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChiDialog.setAdapter(adapterSpinnerDialog);
        spinner_LoaiThuChiDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadDanhSachDanhMucLenSpinnerDialog();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Vi
        arrMaViDialog = new ArrayList<Integer>();
        arrTenViDialog = new ArrayList<String>();
        adapterViDialog = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrTenViDialog);
        adapterViDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ViDialog.setAdapter(adapterViDialog);

    }

    public void LoadDanhSachDanhMucLenSpinnerDialog() {
        arrMaDanhMucDialog.clear();
        arrTenDanhMucDialog.clear();
        Cursor cursor = data.rawQuery("select madanhmuc, tendanhmuc from tbldanhmucthuchi where loaikhoan = '" + spinner_LoaiThuChiDialog.getSelectedItem().toString() + "'", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrMaDanhMucDialog.add(cursor.getInt(cursor.getColumnIndex("madanhmuc")));
            arrTenDanhMucDialog.add(cursor.getString(cursor.getColumnIndex("tendanhmuc")));
            cursor.moveToNext();
        }
        adapterDanhMucDialog.notifyDataSetChanged();
    }

    public void LoadDanhSachViLenSpinnerDialog() {
        arrMaViDialog.clear();
        arrTenViDialog.clear();
        //cursor = data.query("tblvi", null, null, null, null, null, null);
        Cursor cursor = data.rawQuery("select * from tblvi where tentaikhoan = '" + taikhoan +"'", null);
        cursor.moveToFirst();
        listDialog = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            arrMaViDialog.add(cursor.getInt(cursor.getColumnIndex("mavi")));
            arrTenViDialog.add(cursor.getString(cursor.getColumnIndex("tenvi")));
            cursor.moveToNext();
        }
        adapterViDialog.notifyDataSetChanged();
    }

    public void ChonNgay() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_chonngay);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button button_ChonNgayXong = (Button) dialog.findViewById(R.id.button_ChonNgayXong);
        button_ChonNgayXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dateDialog = simpleDateFormatDialog.parse(datePicker.getDayOfMonth() + "/" +(datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button_NgayThuChiDialog.setText(simpleDateFormatDialog.format(dateDialog));
                dialog.cancel();
            }
        });
    }

    public void HienThiThoiGian() {
        int thang = todayDialog.get(Calendar.MONTH) + 1;
        gioDialog = todayDialog.get(Calendar.HOUR_OF_DAY) + ":"+ todayDialog.get(Calendar.MINUTE) + ":" +todayDialog.get(Calendar.SECOND);
        dateDialog = todayDialog.getTime();
        button_NgayThuChiDialog.setText(simpleDateFormatDialog.format(dateDialog));
        button_GioThuChiDialog.setText(gioDialog);
    }

    public boolean ThemThuChiDialog() {

            int mathuchi = 1;
            //int sotienthuchi = 0;
            cursor = data.rawQuery("select mathuchi from tblthuchi", null);
            if (cursor.moveToLast() == true) {
                mathuchi = cursor.getInt(cursor.getColumnIndex("mathuchi")) + 1;
            }
            String thongbao = "";
            ContentValues values = new ContentValues();
            values.put("mathuchi", mathuchi);
            values.put("mota", editText_MoTaThuChiDialog.getText().toString());
            values.put("loaithuchi", spinner_LoaiThuChiDialog.getSelectedItem().toString());

            values.put("sotienthuchi", sotienthuchi);
            values.put("mavi", arrMaViDialog.get(spinner_ViDialog.getSelectedItemPosition()));
            values.put("ngaythuchien", simpleDateFormatDialog.format(dateDialog));
            values.put("madanhmuc", arrMaDanhMucDialog.get(spinner_DanhMucDialog.getSelectedItemPosition()));
            values.put("tentaikhoan", taikhoan);

            if (data.insert("tblthuchi", null, values) == -1) {
                return false;
            }
            thongbao = "Lưu thành công";
            Toast.makeText(activity, thongbao, Toast.LENGTH_SHORT).show();
            TinhSoDu();
        return true;
    }

    public void KiemTraChiTieuMax()
    {
        int mavithem = arrMaViDialog.get(spinner_ViDialog.getSelectedItemPosition());
        Cursor cursor =  data.rawQuery("select * from tblvi" ,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false)
        {
            if (cursor.getInt(cursor.getColumnIndex("mavi")) == mavithem)
            {
                sotientuvi = cursor.getInt(cursor.getColumnIndex("sotienvi"));

            }
            cursor.moveToNext();
        }

        if (spinner_LoaiThuChiDialog.getSelectedItem().toString().equals("Khoản thu")) {
            sotienthuchi = Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
        } else {
            int sotienthu = Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
            if(sotientuvi < sotienthu){
                editText_SoTienThuChiDialog.setText(String.valueOf(sotientuvi));
                editText_SoTienThuChiDialog.startAnimation(animation);
                Toast.makeText(activity,"Số tiền chi vượt quá số tiền ví",Toast.LENGTH_SHORT).show();
                sotienthuchi = -Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
            }else {
                sotienthuchi = -Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
            }
        }
    }

    public void TinhSoDu()
    {
        int sodu = 0;
        int mavithem = arrMaViDialog.get(spinner_ViDialog.getSelectedItemPosition());
        //Tinh
        sodu = sotientuvi + sotienthuchi;

        // gan lai
        ContentValues values1 = new ContentValues();
        values1.put("sotienvi",sodu);
        data.update("tblvi",values1,"mavi = " + mavithem,null);
    }


    //Thu Chi Fragment
    public void setListview() {
        arr = new ArrayList<ArrayThuChi>();
        adapterThuChi = new AdapterThuChi(getActivity(), R.layout.activity_thuchi_item, arr);
        listView_LichSuThuChi.setAdapter(adapterThuChi);
    }

    public void setSpinner() {
        arrSpinner = new ArrayList<String>();
        adapterSpinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LocThuChi.setAdapter(adapterSpinner);
        spinner_LocThuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadThuChiTheoSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void LoadNgayLenSpinner() {
        arrSpinner.clear();
        Cursor cursor = data.rawQuery("select ngaythuchien from tblthuchi group by ngaythuchien", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrSpinner.add(cursor.getString(cursor.getColumnIndex("ngaythuchien")));
            cursor.moveToNext();
        }
        adapterSpinner.notifyDataSetChanged();
    }

    public void LoadThuChiTheoSpinner() {
        arr.clear();
        Cursor cursor = data.rawQuery("select tblthuchi.mathuchi, tblthuchi.ngaythuchien, tblthuchi.sotienthuchi, tbldanhmucthuchi.tendanhmuc, tblvi.tenvi, loaikhoan " +
                " from tblthuchi inner join tbldanhmucthuchi on tblthuchi.madanhmuc = tbldanhmucthuchi.madanhmuc " +
                " inner join tblvi on tbldanhmucthuchi.tentaikhoan = tblvi.tentaikhoan " +
                " where tblthuchi.tentaikhoan = '" + taikhoan + "' and tblvi.mavi = tblthuchi.mavi and tblthuchi.ngaythuchien = '" + spinner_LocThuChi.getSelectedItem().toString() + "' ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arr.add(new ArrayThuChi(cursor.getString(cursor.getColumnIndex("ngaythuchien")), cursor.getString(cursor.getColumnIndex("tendanhmuc")), cursor.getString(cursor.getColumnIndex("tenvi")), cursor.getDouble(cursor.getColumnIndex("sotienthuchi")), cursor.getInt(cursor.getColumnIndex("mathuchi")), cursor.getString(cursor.getColumnIndex("loaikhoan"))));
            cursor.moveToNext();
        }
        cursor.close();
        adapterThuChi.notifyDataSetChanged();
    }

    public void LocCoSoDuLieu() {
        arr.clear();
        Cursor cursor = data.rawQuery("select mathuchi, ngaythuchien, sotienthuchi, tendanhmuc, tenvi " +
                " from tblthuchi inner join tbldanhmucthuchi on tblthuchi.madanhmuc = tbldanhmucthuchi.madanhmuc " +
                " inner join tblvi on tbldanhmucthuchi.tentaikhoan = tblvi.tentaikhoan " +
                " where tblthuchi.tentaikhoan = '" + taikhoan + "' and tblvi.mavi = tblthuchi.mavi  ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arr.add(new ArrayThuChi(cursor.getString(cursor.getColumnIndex("ngaythuchien")), cursor.getString(cursor.getColumnIndex("tendanhmuc")), cursor.getString(cursor.getColumnIndex("tenvi")), cursor.getDouble(cursor.getColumnIndex("sotienthuchi")), cursor.getInt(cursor.getColumnIndex("mathuchi")), cursor.getString(cursor.getColumnIndex("loaikhoan"))));
            cursor.moveToNext();
        }
        cursor.close();
        adapterThuChi.notifyDataSetChanged();
    }

    public void XoaThuChi() {
        listView_LichSuThuChi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HamXoaThuChi(position);
                return false;
            }
        });
    }

    public void HamXoaThuChi(final int mathuchi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn có chắc chắn muốn xóa khoản này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblthuchi where mathuchi = '" + arr.get(mathuchi).ma + "'", null).moveToFirst();
                Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
                //LoadNgayLenSpinner();
                //Delay ham xuat du lieu de tranh bi crash app
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadThuChiTheoSpinner();
                    }
                }, 100 );
                //LoadThuChiTheoSpinner();
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

//    public void LayNgayLenTab() {
//        arrTabNgay = new ArrayList<String>();
////        Cursor cursor = data.rawQuery("select ngaythuchien from tblthuchi " +
////                " inner join tbldanhmucthuchi on tblthuchi.madanhmuc = tbldanhmucthuchi.madanhmuc" +
////                " where tentaikhoan = '" + taikhoan +"' group by ngaythuchien", null);
//        Cursor cursor = data.rawQuery("select ngaythuchien from tblthuchi " +
//                " where tentaikhoan = '" + taikhoan +"' group by ngaythuchien", null);
//        if (cursor.moveToFirst() != true) {
//            arrTabNgay.add("Nothing");
//        } else {
//            while (cursor.isAfterLast() == false) {
//                arrTabNgay.add(cursor.getString(cursor.getColumnIndex("ngaythuchien")));
//                cursor.moveToNext();
//            }
//        }
//    }
}