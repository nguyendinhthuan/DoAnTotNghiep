package com.example.doantotnghiep.ui.QuanLyVi;

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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doantotnghiep.Database;
import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterVi;
import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;
import java.util.List;

public class QuanLyViFragment extends Fragment {

    private QuanLyViViewModel galleryViewModel;
    private Activity activity;
    private ImageButton button_ThemVi;
    private Button button_LuuCapNhatVi, button_HuyCapNhatVi;
    private ListView listView_Vi;
    private View myFragment;
    private ArrayList<ArrayVi> arrayVi;
    private AdapterVi adapterVi;
    private SQLiteDatabase data;
    private Database database;
    private ArrayAdapter arrayAdapter;
    private Animation animation;
    private List<ArrayVi> list = null;
    private String taikhoan;
    private SharedPreferences sharedPreferences;
    private EditText editText_NhapTenViCapNhat, editText_NhapMoTaViCapNhat, editText_NhapSoTienViCapNhat,editText_TenVi,editText_MoTaVi,editText_SoTienVi;
    private Button button_LuuVi,button_ThoatVi;
    private int vitri = 0;
    private Cursor cursor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_quanlyvi, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);

        AnhXa();
        ThemVi();
        LayTenTaiKhoan();
        LayDanhSachVi();
        //TaiDanhSachVi();

        listView_Vi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                vitri = position;
                return false;
            }
        });
        registerForContextMenu(listView_Vi);



    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        activity.getMenuInflater().inflate(R.menu.menu_vi, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_Sua:{
                SuaVi();
                return true;
            }
            case R.id.option_Xoa:{
                XoaVi(vitri);
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void LayTenTaiKhoan()
    {
        sharedPreferences = getActivity().getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");
    }

    public void ThemVi() {
        button_ThemVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getContext());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.activity_vi);
                d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                d.show();

                //AnhXa
                button_LuuVi = (Button) d.findViewById(R.id.button_LuuVi);
                button_ThoatVi = (Button) d.findViewById(R.id.button_ThoatVi);
                editText_TenVi = (EditText) d.findViewById(R.id.editText_TenVi);
                editText_MoTaVi = (EditText) d.findViewById(R.id.editText_MoTaVi);
                editText_SoTienVi = (EditText) d.findViewById(R.id.editText_SoTienVi);

                //Xuly
               button_LuuVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean tenvi = true;
                        String thongbao = "";
                        cursor = data.rawQuery("select * from tblvi", null);
                        cursor.moveToFirst();
                        while (cursor.isAfterLast()==false) {
                            if (cursor.getString(cursor.getColumnIndex("tenvi")).equals(editText_TenVi.getText().toString())) {
                                tenvi = false;
                            }
                            cursor.moveToNext();
                        }
                        if (tenvi == false) {
                            Toast.makeText(activity, "Tên ví này đã tồn tại", Toast.LENGTH_SHORT).show();
                            editText_TenVi.startAnimation(animation);
                        } else if (editText_TenVi.getText().toString().equals("")) {
                            editText_TenVi.startAnimation(animation);
                            Toast.makeText(activity, "Bạn chưa nhập tên ví", Toast.LENGTH_SHORT).show();
                        } else if (editText_MoTaVi.getText().toString().equals("")) {
                            editText_MoTaVi.startAnimation(animation);
                            Toast.makeText(activity, "Bạn chưa nhập mô tả cho ví", Toast.LENGTH_SHORT).show();
                        } else if (editText_SoTienVi.getText().toString().equals("")) {
                            editText_SoTienVi.startAnimation(animation);
                            Toast.makeText(activity, "Bạn chưa nhập số tiền ban đầu cho ví", Toast.LENGTH_SHORT).show();
//        } else if (DinhDangsoTien() < 0) {
//            editText_SoTienVi.setAnimation(animation);
//            Toast.makeText(this, "Số tiền không được âm", Toast.LENGTH_SHORT).show();
                        } else {
                            int mavi = 1;
                            cursor = data.rawQuery("select mavi from tblvi", null);
                            if (cursor.moveToLast() == true) {
                                mavi = cursor.getInt(cursor.getColumnIndex("mavi")) + 1;
                            }

                            ContentValues values = new ContentValues();
                            values.put("mavi", mavi);
                            values.put("tenvi", editText_TenVi.getText().toString());
                            values.put("motavi", editText_MoTaVi.getText().toString());
                            values.put("sotienvi", editText_SoTienVi.getText().toString());
                            values.put("tentaikhoan", taikhoan);
                            if(data.insert("tblvi", null, values)!= -1){
                                thongbao = "Thêm ví thành công";
                                d.dismiss();
                            }else {
                                thongbao = "Thêm ví không thành công";
                            }
                            Toast.makeText(activity, thongbao, Toast.LENGTH_LONG).show();
                        }

                            LayDanhSachVi();
                    }
                });
                button_ThoatVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
            }
        });
    }

    public void AnhXa() {
        button_ThemVi = (ImageButton) myFragment.findViewById(R.id.button_ThemVi);

        listView_Vi = (ListView) myFragment.findViewById(R.id.listView_Vi);
    }

    public void LayDanhSachVi() {
        //Cursor cursor = data.query("tblvi", null, null, null, null, null, null);
        Cursor cursor = data.rawQuery("select * from tblvi where tentaikhoan = '" + taikhoan + "'", null);
        cursor.moveToFirst();
        list = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            ArrayVi a = new ArrayVi();
            a.setMavi(cursor.getInt(0));
            a.setTenvi(cursor.getString(1));
            a.setMotavi(cursor.getString(2));
            a.setSotienvi(cursor.getDouble(3));
            a.setSodu(cursor.getDouble(4));
            list.add(a);

            cursor.moveToNext();
        }
        cursor.close();
        adapterVi = new AdapterVi(getContext(), R.layout.fragment_quanlyvi_item, list);
        listView_Vi.setAdapter(adapterVi);
    }

    public void SuaVi() {
                final String tenviht = list.get(vitri).tenvi; // lay ten vi de cap nhat
                final Dialog d = new Dialog(getContext());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.activity_capnhatvi);
                d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                d.show();
                //AnhXa
                editText_NhapTenViCapNhat = (EditText) d.findViewById(R.id.editText_NhapTenViCapNhat);
                editText_NhapMoTaViCapNhat = (EditText) d.findViewById(R.id.editText_NhapMoTaViCapNhat);
                editText_NhapSoTienViCapNhat = (EditText) d.findViewById(R.id.editText_NhapSoTienCapNhat);
                editText_NhapSoTienViCapNhat.setEnabled(false);
                button_LuuCapNhatVi = (Button) d.findViewById(R.id.button_LuuCapNhatVi);
                button_HuyCapNhatVi = (Button) d.findViewById(R.id.button_HuyCapNhatVi);
                //LayThongTinViLenDialog
                Cursor cursor = data.rawQuery("select * from tblvi where tenvi like '"+ tenviht +"'" , null);
                cursor.moveToFirst();
                String tenvi1 = cursor.getString(1);
                String motavi1 = cursor.getString(2);
                String sotienvi1 = String.valueOf(cursor.getDouble(3));

                editText_NhapTenViCapNhat.setText(tenvi1);
                editText_NhapMoTaViCapNhat.setText(motavi1);
                editText_NhapSoTienViCapNhat.setText(sotienvi1);

                //XuLy
                button_LuuCapNhatVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(CapNhatVi())
                        {
                            d.dismiss();
                            LayDanhSachVi();
                        }
                    }
                });
                button_HuyCapNhatVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
            }
//        });
//    }

    public boolean CapNhatVi()
    {
        String thongbao = "";
        final String tenviht = list.get(vitri).tenvi;
       if(editText_NhapTenViCapNhat.getText().toString().equals(""))
       {
           editText_NhapTenViCapNhat.startAnimation(animation);
           Toast.makeText(activity,"Bạn chưa nhập tên ví",Toast.LENGTH_LONG).show();
       }else if(editText_NhapMoTaViCapNhat.getText().toString().equals(""))
       {
           editText_NhapMoTaViCapNhat.startAnimation(animation);
           Toast.makeText(activity,"Bạn chưa nhập mô tả ví",Toast.LENGTH_LONG).show();
       }else {
           ContentValues values = new ContentValues();
           values.put("tenvi",editText_NhapTenViCapNhat.getText().toString());
           values.put("motavi",editText_NhapMoTaViCapNhat.getText().toString());
           data.update("tblvi",values,"tenvi like '"+ tenviht + "'",null);
           thongbao = "Cập nhật thành công";
           Toast.makeText(activity,thongbao,Toast.LENGTH_LONG).show();
           return true;
       }
       thongbao = "Cập nhật không thành công";
       Toast.makeText(activity,thongbao,Toast.LENGTH_LONG).show();
       return false;
    }

    public void XoaVi(int vitri1) {
        HamXoaVi(list.get(vitri1).tenvi);
    }

    public void HamXoaVi(final String tenvi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn có chắc chắn muốn xóa ví này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblvi where tenvi = '" + tenvi + "'", null).moveToFirst();
                LayDanhSachVi();
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