package com.example.doantotnghiep.ui.QuanLyVi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Button button_Reload;
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

//    public QuanLyViFragment(String taikhoan) {
//        this.taikhoan = taikhoan;
//    }

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
        taikhoan = getActivity().getIntent().getStringExtra("taikhoan");

        AnhXa();
        ThemVi();
        TaiDanhSachVi();
        SuaVi();
        XoaVi();
        LayDanhSachVi();
        //TruyenTenTaiKhoanSangVi();
    }

//    public void TruyenTenTaiKhoanSangVi() {
//        Cursor c = data.rawQuery("select * from tbltaikhoan", null);
//        c.moveToFirst();
//        while (c.isAfterLast() == false) {
//            Intent i = new Intent(getContext(), ViActivity.class);
//            i.putExtra("taikhoan", c.getString(c.getColumnIndex("tentaikhoan")));
//            startActivityForResult(i, 2);
//        }
//    }

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
        Cursor cursor = data.rawQuery("select mavi, tenvi, motavi, sotienvi, sodu from tblvi where tentaikhoan = '" + taikhoan +"'", null);
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
        listView_Vi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getContext(), ThuChiActivity.class);
//                startActivity(i);
            }
        });
    }

    public void XoaVi() {
        listView_Vi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HamXoaVi(list.get(position).tenvi);
                return false;
            }
        });
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