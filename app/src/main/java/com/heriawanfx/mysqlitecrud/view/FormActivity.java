package com.heriawanfx.mysqlitecrud.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.heriawanfx.mysqlitecrud.R;
import com.heriawanfx.mysqlitecrud.db.CatatanHelper;
import com.heriawanfx.mysqlitecrud.model.Catatan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormActivity extends AppCompatActivity {

    public static String EXTRA_CATATAN = "extra_catatan";
    public static String EXTRA_POSITION = "extra_position";
    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtDeskripsi)
    EditText edtDeskripsi;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private Catatan mCatatan;
    private int position;
    private CatatanHelper mCatatanHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);

        mCatatanHelper = new CatatanHelper(this);
        mCatatanHelper.open();

        mCatatan = getIntent().getParcelableExtra(EXTRA_CATATAN);

        //mengecek catatan yang tidak kosong
        if (mCatatan != null){
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        }

        String strJudulActionBar;
        String strTeksButton;

        //jika edit
        if (isEdit){
            strJudulActionBar = "Ubah";
            strTeksButton = "Update";
            edtJudul.setText(mCatatan.getJudul());
            edtDeskripsi.setText(mCatatan.getDeskrsipsi());
        }else{
            strJudulActionBar = "Tambah";
            strTeksButton = "Simpan";
        }

        getSupportActionBar().setTitle(strJudulActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setText(strTeksButton);
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        String strJudul = edtJudul.getText().toString().trim();
        String strDeskripsi = edtDeskripsi.getText().toString().trim();

        boolean isEmpty = false;
        
        if (TextUtils.isEmpty(strJudul)){
            isEmpty = true;
            edtJudul.setError("Judul harus diisi");
        }

        if (!isEmpty){
            Catatan catatanBaru = new Catatan();
            catatanBaru.setJudul(strJudul);
            catatanBaru.setDeskrsipsi(strDeskripsi);

            Intent intent = new Intent();
 
            //jika edit maka setResult nya Update
            if (isEdit){
                catatanBaru.setTanggal(mCatatan.getTanggal());
                catatanBaru.setId(mCatatan.getId());
                mCatatanHelper.update(catatanBaru);

                intent.putExtra(EXTRA_POSITION, position);
                setResult(RESULT_UPDATE, intent);
                finish();
            } else{
                //jika bukan maka ADD
                catatanBaru.setTanggal(getCurrentDate());
                mCatatanHelper.insert(catatanBaru);

                setResult(RESULT_ADD);
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    final int ALERT_DIALOG_CLOSE = 10;
    final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type){
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String strJudulDialog, strPesanDialog;

        if (isDialogClose){
            strJudulDialog = "Kembali";
            strPesanDialog = "Apakah anda ingin membatalkan perubahan ini?";
        }else{
            strJudulDialog = "Hapus";
            strPesanDialog = "Apakah anda yakin ingin menghapus catatan ini?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(strJudulDialog);
        alertDialogBuilder
                .setMessage(strPesanDialog)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if (isDialogClose){
                            finish();
                        }else{
                            mCatatanHelper.delete(mCatatan.getId());
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);

                            //mengambil nilai untuk ditampilkan di depan onActivity Result
                            setResult(RESULT_DELETE, intent);
                            finish();
                        }
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCatatanHelper != null){
            mCatatanHelper.close();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }
}
