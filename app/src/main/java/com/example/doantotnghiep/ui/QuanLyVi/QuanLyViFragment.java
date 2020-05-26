package com.example.doantotnghiep.ui.QuanLyVi;

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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
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
import com.example.doantotnghiep.ThuChiActivity;
import com.example.doantotnghiep.ViActivity;
import com.example.doantotnghiep.adapter.AdapterVi;
import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;
import java.util.List;

public class QuanLyViFragment extends Fragment {

    private QuanLyViViewModel galleryViewModel;
    private Activity activity;
    private ImageButton button_ThemVi;
    private Button button_Reload, button_LuuCapNhatVi, button_HuyCapNhatVi;
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
    private EditText editText_NhapTenViCapNhat, editText_NhapMoTaViCapNhat, editText_NhapSoTienViCapNhat;
    private int vitri = 0;

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
        TaiDanhSachVi();

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
                Intent i = new Intent(getActivity(), ViActivity.class);
                startActivity(i);
            }
        });
    }

    public void TaiDanhSachVi() {
        button_Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayDanhSachVi();
                Toast.makeText(activity, "Tải danh sách ví thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AnhXa() {
        button_ThemVi = (ImageButton) myFragment.findViewById(R.id.button_ThemVi);

        button_Reload = (Button) myFragment.findViewById(R.id.button_Reload);

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