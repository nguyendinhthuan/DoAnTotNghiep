package com.example.doantotnghiep.ui.KeHoachTietKiem;

import androidx.lifecycle.ViewModelProviders;

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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterKeHoachTietKiem;
import com.example.doantotnghiep.adapter.AdapterThuChi;
import com.example.doantotnghiep.model.ArrayKeHoachTietKiem;
import com.example.doantotnghiep.model.ArrayThuChi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KeHoachTietKiemFragment extends Fragment {

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
    private ListView listView_KeHoachTietKiem;
    private Calendar calendar;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private ArrayList<ArrayKeHoachTietKiem> arr;
    private AdapterKeHoachTietKiem adapterKeHoachTietKiem;
    private boolean danhsachkehoachtietkiem = false;
    private TextView textView_DanhSachKeHoachTietKiemTrong;

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
        XoaKeHoachTietKiem();
        XuLyKhiDanhSachKeHoachTietKiemTrong(danhsachkehoachtietkiem);
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
                HienThiThoiGian();

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
                            contentValues.put("tentaikhoan", taikhoan);

                            if (data.insert("tblkehoachtietkiem", null, contentValues) != -1) {
                                thongbao = "Thêm kế hoạch tiết kiệm thành công";
                                d.dismiss();

                                //Load tat ca danh sach ke hoach tiet kiem
                                LoadTatCaKeHoachTietKiem();
                                XuLyKhiDanhSachKeHoachTietKiemTrong(danhsachkehoachtietkiem);
                            }
                            Toast.makeText(getActivity(), thongbao, Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getActivity(), thongbao, Toast.LENGTH_LONG).show();
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

    public void HienThiThoiGian() {
        date = calendar.getTime();
        button_NgayBatDauKeHoachTietKiem.setText(simpleDateFormat.format(date));
    }

    public void setListview() {
        arr = new ArrayList<ArrayKeHoachTietKiem>();
        adapterKeHoachTietKiem = new AdapterKeHoachTietKiem(getActivity(), R.layout.adapter_kehoachtietkiem_item, arr);
        listView_KeHoachTietKiem.setAdapter(adapterKeHoachTietKiem);
    }

    public void LoadTatCaKeHoachTietKiem() {
        arr.clear();
        Cursor cursor = data.rawQuery("select * from tblkehoachtietkiem where tentaikhoan = '" + taikhoan + "' ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arr.add(new ArrayKeHoachTietKiem(cursor.getString(cursor.getColumnIndex("tenkehoachtietkiem")), cursor.getString(cursor.getColumnIndex("ngaybatdaukehoachtietkiem")), cursor.getString(cursor.getColumnIndex("ngayketthuckehoachtietkiem")), cursor.getDouble(cursor.getColumnIndex("sotienkehoachtietkiem")), cursor.getInt(cursor.getColumnIndex("makehoachtietkiem"))));
            cursor.moveToNext();
        }
        cursor.close();
        adapterKeHoachTietKiem.notifyDataSetChanged();
    }

    public void XoaKeHoachTietKiem() {
        listView_KeHoachTietKiem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HamXoaKeHoachTietKiem(position);
                return false;
            }
        });
    }

    public void HamXoaKeHoachTietKiem(final int makehoachtietkiem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn có chắc chắn muốn xóa kế hoạch tiết kiệm này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblkehoachtietkiem where makehoachtietkiem = '" + arr.get(makehoachtietkiem).makehoachtietkiem + "'", null).moveToFirst();
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

    public boolean KiemTraCoKeHoachTietKiemHayChua() {
        return danhsachkehoachtietkiem;
    }

}
