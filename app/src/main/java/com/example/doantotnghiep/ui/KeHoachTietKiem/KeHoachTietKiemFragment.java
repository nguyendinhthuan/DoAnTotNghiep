package com.example.doantotnghiep.ui.KeHoachTietKiem;

import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterKeHoachTietKiem;
import com.example.doantotnghiep.adapter.AdapterThuChi;
import com.example.doantotnghiep.adapter.AdapterThuChiChoKeHoachTietKiem;
import com.example.doantotnghiep.model.ArrayKeHoachTietKiem;
import com.example.doantotnghiep.model.ArrayThuChi;
import com.example.doantotnghiep.model.ArrayThuChiChoKeHoachTietKiem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KeHoachTietKiemFragment extends Fragment {
    private int vitri = 0;
    private View myFragment;
    private SQLiteDatabase data;
    private SharedPreferences sharedPreferences;
    private Activity activity;
    private String taikhoan;
    private Animation animation;
    private ImageButton imageButton_ThemKeHoachTietKiem;
    private Button button_LuuKeHoachTietKiem, button_HuyKeHoachTietKiem, button_NgayBatDauKeHoachTietKiem,
            button_NgayKetThucKeHoachTietKiem;
    private EditText editText_TenKeHoachTietKiem, editText_SoTienKeHoachTietKiem;
    private ListView listView_KeHoachTietKiem, listView_ThuChiChoKeHoachTietKiem;
    private Calendar calendar;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private ArrayList<ArrayKeHoachTietKiem> arrayKeHoachTietKiem;
    private List<ArrayKeHoachTietKiem> list = null;
    private AdapterKeHoachTietKiem adapterKeHoachTietKiem;
    private boolean danhsachkehoachtietkiem = false, danhsachthuchichokehoachtietkiem = false;
    private TextView textView_DanhSachKeHoachTietKiemTrong, textView_DanhSachThuChiChoKeHoachTietKiemTrong;
    private EditText editText_SoTienThuChiChoKeHoach, editText_MoTaThuChiChoKeHoach;
    private Spinner spinner_LoaiThuChiChoKeHoach;
    private TextView textView_NgayThucHienThuChiChoKeHoach, textView_Toolbar;
    private Button button_LuuThuChiChoKeHoach, button_HuyThuChiChoKeHoach, button_HuyThuChiChoKeHoachTietKiem;
    private String[] arrSpinner;
    private ArrayAdapter<String> adapterSpinner;
    private int sotienthuchichokehoach, sotiendatietkiemchokehoachtietkiem, sotienkehoachtietkiem;
    private ArrayList<ArrayThuChiChoKeHoachTietKiem> arrayThuChiChoKeHoachTietKiem;
    private AdapterThuChiChoKeHoachTietKiem adapterThuChiChoKeHoachTietKiem;
    private EditText editText_TenKeHoachTietKiem_ChiTiet, editText_SoTienKeHoachTietKiem_ChiTiet;
    private Button button_NgayBatDauKeHoachTietKiem_ChiTiet, button_NgayKetThucKeHoachTietKiem_ChiTiet, button_HuyKeHoachTietKiem_ChiTiet;


    public static KeHoachTietKiemFragment newInstance() {
        return new KeHoachTietKiemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_kehoachtietkiem, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        sharedPreferences = getActivity().getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");
        simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        date = new Date();

        AnhXa();
        ThemKeHoachTietKiem();
        setListview();
        LoadTatCaKeHoachTietKiem();
        XuLyKhiDanhSachKeHoachTietKiemTrong(danhsachkehoachtietkiem);
        KiemTraThoiHanCuaKeHoachTietKiem();

        listView_KeHoachTietKiem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vitri = position;
                return false;
            }
        });
        registerForContextMenu(listView_KeHoachTietKiem);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        activity.getMenuInflater().inflate(R.menu.menu_kehoachtietkiem, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_ThuChoKeHoachTietKiem: {
                ThuVaoChoKeHoachTietKiem();
                return true;
            }
            case R.id.option_XoaKeHoachTietKiem: {
                XoaKeHoachTietKiem(vitri);
                return true;
            }
            case R.id.option_XemLishSuThuChiChoKeHoachTietKiem: {
                XemLichSuThuChiChoKeHoachTietKiem();
                return true;
            }
            case R.id.option_XemChiTietKeHoachTietKiem: {
                XemChiTietKeHoachKiem();
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.option_ThuChoKeHoachTietKiem);
        item.setEnabled(false);
    }

    public void AnhXa() {
        listView_KeHoachTietKiem = (ListView) myFragment.findViewById(R.id.listView_KeHoachTietKiem);
        imageButton_ThemKeHoachTietKiem = (ImageButton) myFragment.findViewById(R.id.imageButton_ThemKeHoachTietKiem);
        textView_DanhSachKeHoachTietKiemTrong = (TextView) myFragment.findViewById(R.id.textView_DanhSachKeHoachTietKiemTrong);
    }

    public void ThemKeHoachTietKiem() {
        imageButton_ThemKeHoachTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getContext());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_themkehoachtietkiem);
                d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                d.show();

                //Anh xa
                button_LuuKeHoachTietKiem = (Button) d.findViewById(R.id.button_LuuKeHoachTietKiem);
                button_HuyKeHoachTietKiem = (Button) d.findViewById(R.id.button_HuyKeHoachTietKiem);
                editText_TenKeHoachTietKiem = (EditText) d.findViewById(R.id.editText_TenKeHoachTietKiem);
                button_NgayBatDauKeHoachTietKiem = (Button) d.findViewById(R.id.button_NgayBatDauKeHoachTietKiem);
                button_NgayKetThucKeHoachTietKiem = (Button) d.findViewById(R.id.button_NgayKetThucKeHoachTietKiem);
                editText_SoTienKeHoachTietKiem = (EditText) d.findViewById(R.id.editText_SoTienKeHoachTietKiem);

                calendar = Calendar.getInstance();

                //Xu Ly
                HienThiThoiGianThemKeHoachTietKiem();

                button_HuyKeHoachTietKiem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                button_LuuKeHoachTietKiem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tenkehoachtietkiem = true;
                        String thongbao = "";

                        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem", null);
                        cursor.moveToFirst();
                        while (cursor.isAfterLast() == false) {
                            if (cursor.getString(cursor.getColumnIndex("tenkehoachtietkiem")).equals(editText_TenKeHoachTietKiem.getText().toString())) {
                                tenkehoachtietkiem = false;
                            }
                            cursor.moveToNext();
                        }
                        if (tenkehoachtietkiem == false) {
                            thongbao = "Tên kế hoạch tiết kiệm này đã tồn tại";
                            editText_TenKeHoachTietKiem.startAnimation(animation);
                        } else if (editText_TenKeHoachTietKiem.getText().toString().equals("")) {
                            thongbao = "Bạn chưa nhập tên kế hoạch tiết kiệm";
                            editText_TenKeHoachTietKiem.startAnimation(animation);
                        } else if (button_NgayKetThucKeHoachTietKiem.getText().toString().equals("Chọn ngày")) {
                            thongbao = "Bạn chưa chọn ngày kết thúc cho kế hoạch tiết kiệm";
                            button_NgayKetThucKeHoachTietKiem.startAnimation(animation);
                        } else if (editText_SoTienKeHoachTietKiem.getText().toString().equals("")) {
                            thongbao = "Bạn chưa nhập số tiền cho kế hoạch tiết kiệm";
                            editText_SoTienKeHoachTietKiem.startAnimation(animation);
//                        } else if () {

                        } else {
                            int makehoachtietkiem = 1;
                            Cursor cursor1 = data.rawQuery("select makehoachtietkiem from tblkehoachtietkiem", null);
                            if (cursor1.moveToLast() == true) {
                                makehoachtietkiem = cursor1.getInt(cursor1.getColumnIndex("makehoachtietkiem")) + 1;
                            }

                            //Luu vao co so du lieu
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("makehoachtietkiem", makehoachtietkiem);
                            contentValues.put("tenkehoachtietkiem", editText_TenKeHoachTietKiem.getText().toString());
                            contentValues.put("ngaybatdaukehoachtietkiem", button_NgayBatDauKeHoachTietKiem.getText().toString());
                            contentValues.put("ngayketthuckehoachtietkiem", button_NgayKetThucKeHoachTietKiem.getText().toString());
                            contentValues.put("sotienkehoachtietkiem", editText_SoTienKeHoachTietKiem.getText().toString());
                            contentValues.put("sotiendatietkiem", 0);
                            contentValues.put("trangthai", "Chưa hoàn thành");
                            contentValues.put("tentaikhoan", taikhoan);

                            if (data.insert("tblkehoachtietkiem", null, contentValues) != -1) {
                                thongbao = "Thêm kế hoạch tiết kiệm thành công";
                                d.dismiss();

                                //Load tat ca danh sach ke hoach tiet kiem
                                LoadTatCaKeHoachTietKiem();
                                XuLyKhiDanhSachKeHoachTietKiemTrong(danhsachkehoachtietkiem);
                            }
                            Toast.makeText(getActivity(), thongbao, Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), thongbao, Toast.LENGTH_SHORT).show();
                    }
                });

                button_NgayKetThucKeHoachTietKiem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChonNgayKetThucKeHoachTietKiem();
                    }
                });
            }
        });
    }

    public void ChonNgayKetThucKeHoachTietKiem() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chonngay);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //Anh xa
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button button_ChonNgayXong = (Button) dialog.findViewById(R.id.button_ChonNgayXong);

        button_ChonNgayXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    date = simpleDateFormat.parse(datePicker.getDayOfMonth() + "/" +(datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button_NgayKetThucKeHoachTietKiem.setText(simpleDateFormat.format(date));
                dialog.cancel();
            }
        });
    }

    public void HienThiThoiGianThemKeHoachTietKiem() {
        date = calendar.getTime();
        button_NgayBatDauKeHoachTietKiem.setText(simpleDateFormat.format(date));
    }

    public void HienThiThoiGianThemThuChiChoKeHoachTietKiem() {
        date = calendar.getTime();
        textView_NgayThucHienThuChiChoKeHoach.setText(simpleDateFormat.format(date));
    }

    public void setListview() {
        arrayKeHoachTietKiem = new ArrayList<ArrayKeHoachTietKiem>();
        adapterKeHoachTietKiem = new AdapterKeHoachTietKiem(getActivity(), R.layout.adapter_kehoachtietkiem_item, arrayKeHoachTietKiem);
        listView_KeHoachTietKiem.setAdapter(adapterKeHoachTietKiem);
    }

    public void setListView_ThuChiChoKeHoachTietKiem() {
        arrayThuChiChoKeHoachTietKiem = new ArrayList<ArrayThuChiChoKeHoachTietKiem>();
        adapterThuChiChoKeHoachTietKiem = new AdapterThuChiChoKeHoachTietKiem(getActivity(), R.layout.adapter_thuchichokehoachtietkiem_item, arrayThuChiChoKeHoachTietKiem);
        listView_ThuChiChoKeHoachTietKiem.setAdapter(adapterThuChiChoKeHoachTietKiem);
    }

    public void LoadTatCaKeHoachTietKiem() {
        arrayKeHoachTietKiem.clear();
        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem where tentaikhoan = '" + taikhoan + "' ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrayKeHoachTietKiem.add(new ArrayKeHoachTietKiem(cursor.getString(cursor.getColumnIndex("tenkehoachtietkiem")), cursor.getString(cursor.getColumnIndex("ngaybatdaukehoachtietkiem")), cursor.getString(cursor.getColumnIndex("ngayketthuckehoachtietkiem")), cursor.getDouble(cursor.getColumnIndex("sotienkehoachtietkiem")), cursor.getDouble(cursor.getColumnIndex("sotiendatietkiem")), cursor.getInt(cursor.getColumnIndex("makehoachtietkiem")), cursor.getString(cursor.getColumnIndex("trangthai"))));
            cursor.moveToNext();
        }
        cursor.close();
        adapterKeHoachTietKiem.notifyDataSetChanged();
    }

    public void XoaKeHoachTietKiem(int vitri1) {
        HamXoaKeHoachTietKiem(arrayKeHoachTietKiem.get(vitri1).makehoachtietkiem);
    }

    public void HamXoaKeHoachTietKiem(final int makehoachtietkiem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn có chắc chắn muốn xóa kế hoạch tiết kiệm này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblkehoachtietkiem where makehoachtietkiem = '" + makehoachtietkiem + "' and tentaikhoan = '" + taikhoan + "'", null).moveToFirst();
                data.rawQuery("delete from tblthuchichokehoachtietkiem where makehoachtietkiem = '" + makehoachtietkiem + "' and tentaikhoan = '" + taikhoan + "'", null).moveToNext();
                Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();

                //Delay ham xuat du lieu de tranh bi crash app
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadTatCaKeHoachTietKiem();
                        XuLyKhiDanhSachKeHoachTietKiemTrong(danhsachkehoachtietkiem);
                    }
                }, 100 );
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

    public void XuLyKhiDanhSachKeHoachTietKiemTrong(boolean danhsachkehoachtietkiem) {
        this.danhsachkehoachtietkiem = danhsachkehoachtietkiem;
        if (danhsachkehoachtietkiem) {
            textView_DanhSachKeHoachTietKiemTrong.setVisibility(View.VISIBLE);
        } else {
            if (adapterKeHoachTietKiem.getCount() <= 0) {
                textView_DanhSachKeHoachTietKiemTrong.setVisibility(View.VISIBLE);
            } else {
                textView_DanhSachKeHoachTietKiemTrong.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void LoadSpinner() {
        //Spinner loai thu chi cho ke hoach tiet kiem
        arrSpinner = getResources().getStringArray(R.array.loaithuchichokehoachtietkiem);
        adapterSpinner = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChiChoKeHoach.setAdapter(adapterSpinner);
    }

    public void ThuVaoChoKeHoachTietKiem() {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
        final Dialog d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_thuchichokehoachtietkiem);
        d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        d.show();

        //Anh xa
        editText_SoTienThuChiChoKeHoach = (EditText) d.findViewById(R.id.editText_SoTienThuChiChoKeHoach);
        editText_MoTaThuChiChoKeHoach = (EditText) d.findViewById(R.id.editText_MoTaThuChiChoKeHoachTietKiem);
        textView_NgayThucHienThuChiChoKeHoach = (TextView) d.findViewById(R.id.textView_NgayThucHienThuChiChoKeHoachTietKiem);
        button_LuuThuChiChoKeHoach = (Button) d.findViewById(R.id.button_LuuThuChiChoKeHoach);
        button_HuyThuChiChoKeHoachTietKiem = (Button) d.findViewById(R.id.button_HuyThuChiChoKeHoachTietKiem);
        spinner_LoaiThuChiChoKeHoach = (Spinner) d.findViewById(R.id.spinner_LoaiThuChiChoKeHoach);

        calendar = Calendar.getInstance();

        //Xu ly
        HienThiThoiGianThemThuChiChoKeHoachTietKiem();
        LoadSpinner();


        button_LuuThuChiChoKeHoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_SoTienThuChiChoKeHoach.getText().toString().equals("")) {
                    editText_SoTienThuChiChoKeHoach.startAnimation(animation);
                    Toast.makeText(activity, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
                } else if (editText_MoTaThuChiChoKeHoach.getText().toString().equals("")) {
                    editText_MoTaThuChiChoKeHoach.startAnimation(animation);
                    Toast.makeText(activity, "Bạn chưa nhập mô tả", Toast.LENGTH_SHORT).show();
                } else {
                    int mathuchichokehoachtietkiem = 1;
                    Cursor cursor = data.rawQuery("select mathuchichokehoachtietkiem from tblthuchichokehoachtietkiem", null);
                    if (cursor.moveToLast() == true) {
                        mathuchichokehoachtietkiem = cursor.getInt(cursor.getColumnIndex("mathuchichokehoachtietkiem")) + 1;
                    }

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("mathuchichokehoachtietkiem", mathuchichokehoachtietkiem);
                    contentValues.put("loaithuchichokehoachtietkiem", spinner_LoaiThuChiChoKeHoach.getSelectedItem().toString());
                    contentValues.put("sotienthuchichokehoachtietkiem", editText_SoTienThuChiChoKeHoach.getText().toString());
                    contentValues.put("motathuchichokehoachtietkiem", editText_MoTaThuChiChoKeHoach.getText().toString());
                    contentValues.put("ngaythuchienthuchichokehoachtietkiem", textView_NgayThucHienThuChiChoKeHoach.getText().toString());
                    contentValues.put("makehoachtietkiem", arrayKeHoachTietKiem.get(vitri).makehoachtietkiem);
                    contentValues.put("tentaikhoan", taikhoan);

                    if (data.insert("tblthuchichokehoachtietkiem", null, contentValues) != -1) {
                        Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        LayTienTuKeHoachTietKiem();
                        XuLyChuyenTienChoKeHoach();
                        d.dismiss();
                        LoadTatCaKeHoachTietKiem();
                        KiemTraSoTienTietKiemBangSoTienBanDau();
                        LoadTatCaKeHoachTietKiem();
                    } else {
                        Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_HuyThuChiChoKeHoachTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    public void LayTienTuKeHoachTietKiem() {
        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getInt(cursor.getColumnIndex("makehoachtietkiem")) == arrayKeHoachTietKiem.get(vitri).makehoachtietkiem) {
                sotiendatietkiemchokehoachtietkiem = cursor.getInt(cursor.getColumnIndex("sotiendatietkiem"));
            }
            cursor.moveToNext();
        }
    }

    public void XuLyChuyenTienChoKeHoach() {
        int sotienthu = 0;
        int sotienchi = 0;
        sotienthuchichokehoach = Integer.parseInt(editText_SoTienThuChiChoKeHoach.getText().toString());

        sotienthu = sotiendatietkiemchokehoachtietkiem + sotienthuchichokehoach;
        sotienchi = sotiendatietkiemchokehoachtietkiem - sotienthuchichokehoach;

        if (spinner_LoaiThuChiChoKeHoach.getSelectedItem().toString().equals("Thu vào")) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("sotiendatietkiem", sotienthu);
            data.update("tblkehoachtietkiem", contentValues, "makehoachtietkiem = '" + arrayKeHoachTietKiem.get(vitri).makehoachtietkiem + "'", null);
        } else if (spinner_LoaiThuChiChoKeHoach.getSelectedItem().toString().equals("Chi ra")) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("sotiendatietkiem", sotienchi);
            data.update("tblkehoachtietkiem", contentValues, "makehoachtietkiem = '" + arrayKeHoachTietKiem.get(vitri).makehoachtietkiem + "'", null);
        }
    }

    public void KiemTraSoTienTietKiemBangSoTienBanDau() {
        String trangthai = "Đã hoàn thành";
        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getInt(cursor.getColumnIndex("makehoachtietkiem")) == arrayKeHoachTietKiem.get(vitri).makehoachtietkiem) {
                sotiendatietkiemchokehoachtietkiem = cursor.getInt(cursor.getColumnIndex("sotiendatietkiem"));
                sotienkehoachtietkiem = cursor.getInt(cursor.getColumnIndex("sotienkehoachtietkiem"));
                if (sotienkehoachtietkiem == sotiendatietkiemchokehoachtietkiem) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("trangthai", trangthai);
                    data.update("tblkehoachtietkiem", contentValues, "makehoachtietkiem = '" + arrayKeHoachTietKiem.get(vitri).makehoachtietkiem + "'", null);
                }
            }
            cursor.moveToNext();
        }
    }

    public void KiemTraThoiHanCuaKeHoachTietKiem() {
        String ngayketthuc, ngayhientai;
        String trangthai = "Đã kết thúc";
        calendar = Calendar.getInstance();
        date = calendar.getTime();

        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem where tentaikhoan = '" + taikhoan + "'", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getInt(cursor.getColumnIndex("makehoachtietkiem")) == arrayKeHoachTietKiem.get(vitri).makehoachtietkiem) {
                ngayketthuc = cursor.getString(cursor.getColumnIndex("ngayketthuckehoachtietkiem"));
                ngayhientai = simpleDateFormat.format(date);
                try {
                    Date dateketthuc = simpleDateFormat.parse(ngayketthuc);
                    Date datehientai = simpleDateFormat.parse(ngayhientai);

                    if (datehientai.after(dateketthuc)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("trangthai", trangthai);
                        data.update("tblkehoachtietkiem", contentValues, "makehoachtietkiem = '" + arrayKeHoachTietKiem.get(vitri).makehoachtietkiem + "' and tentaikhoan = '" + taikhoan + "'", null);

                        LoadTatCaKeHoachTietKiem();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            cursor.moveToNext();
        }
    }

    public void LoadLichSuThuChiChoKeHoachTietKiem() {
        arrayThuChiChoKeHoachTietKiem.clear();
        Cursor cursor = data.rawQuery("select * from tblthuchichokehoachtietkiem where tentaikhoan = '" + taikhoan + "' " +
                "and makehoachtietkiem = '" + arrayKeHoachTietKiem.get(vitri).makehoachtietkiem + "'", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrayThuChiChoKeHoachTietKiem.add(new ArrayThuChiChoKeHoachTietKiem(cursor.getInt(cursor.getColumnIndex("mathuchichokehoachtietkiem")), cursor.getDouble(cursor.getColumnIndex("sotienthuchichokehoachtietkiem")), cursor.getString(cursor.getColumnIndex("loaithuchichokehoachtietkiem")), cursor.getString(cursor.getColumnIndex("motathuchichokehoachtietkiem")), cursor.getString(cursor.getColumnIndex("ngaythuchienthuchichokehoachtietkiem"))));
            cursor.moveToNext();
        }
        cursor.close();
        adapterThuChiChoKeHoachTietKiem.notifyDataSetChanged();
    }

    public void XemLichSuThuChiChoKeHoachTietKiem() {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
        final Dialog d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_lichsuthuchichokehoachtietkiem);
        d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        d.show();

        //Anh xa
        button_HuyThuChiChoKeHoach = (Button) d.findViewById(R.id.button_HuyThuChiChoKeHoach);
        listView_ThuChiChoKeHoachTietKiem = (ListView) d.findViewById(R.id.listView_ThuChiChoKeHoachTietKiem);
        textView_DanhSachThuChiChoKeHoachTietKiemTrong = (TextView) d.findViewById(R.id.textView_DanhSachThuChiChoKeHoachTietKiemTrong);

        //Xu ly
        setListView_ThuChiChoKeHoachTietKiem();
        LoadLichSuThuChiChoKeHoachTietKiem();
        XuLyKhiDanhSachThuChiChoKeHoachTietKiemTrong(danhsachthuchichokehoachtietkiem);

        button_HuyThuChiChoKeHoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    public void XuLyKhiDanhSachThuChiChoKeHoachTietKiemTrong(boolean danhsachthuchichokehoachtietkiem) {
        this.danhsachthuchichokehoachtietkiem = danhsachthuchichokehoachtietkiem;
        if (danhsachthuchichokehoachtietkiem) {
            textView_DanhSachThuChiChoKeHoachTietKiemTrong.setVisibility(View.VISIBLE);
        } else {
            if (adapterThuChiChoKeHoachTietKiem.getCount() <= 0) {
                textView_DanhSachThuChiChoKeHoachTietKiemTrong.setVisibility(View.VISIBLE);
            } else {
                textView_DanhSachThuChiChoKeHoachTietKiemTrong.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void XemChiTietKeHoachKiem() {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
        final Dialog d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_xemchitietkehoachtietkiem);
        d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        d.show();

        //Anh xa
        editText_TenKeHoachTietKiem_ChiTiet = (EditText) d.findViewById(R.id.editText_TenKeHoachTietKiem_ChiTiet);
        editText_SoTienKeHoachTietKiem_ChiTiet = (EditText) d.findViewById(R.id.editText_SoTienKeHoachTietKiem_ChiTiet);
        button_NgayBatDauKeHoachTietKiem_ChiTiet = (Button) d.findViewById(R.id.button_NgayBatDauKeHoachTietKiem_ChiTiet);
        button_NgayKetThucKeHoachTietKiem_ChiTiet = (Button) d.findViewById(R.id.button_NgayKetThucKeHoachTietKiem_ChiTiet);
        button_HuyKeHoachTietKiem_ChiTiet = (Button) d.findViewById(R.id.button_HuyKeHoachTietKiem_ChiTiet);

        //Xu ly
        final int makehoachtietkiem = arrayKeHoachTietKiem.get(vitri).makehoachtietkiem;
        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem where makehoachtietkiem = '" + makehoachtietkiem + "' and tentaikhoan = '" + taikhoan + "'", null);
        cursor.moveToFirst();

        String tenkehoach = cursor.getString(1);
        String ngaybatdau = cursor.getString(2);
        String ngayketthuc = cursor.getString(3);
        String sotien = String.valueOf(cursor.getDouble(4));

        editText_TenKeHoachTietKiem_ChiTiet.setText(tenkehoach);
        button_NgayBatDauKeHoachTietKiem_ChiTiet.setText(ngaybatdau);
        button_NgayKetThucKeHoachTietKiem_ChiTiet.setText(ngayketthuc);
        editText_SoTienKeHoachTietKiem_ChiTiet.setText(sotien);

        button_HuyKeHoachTietKiem_ChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }
}
