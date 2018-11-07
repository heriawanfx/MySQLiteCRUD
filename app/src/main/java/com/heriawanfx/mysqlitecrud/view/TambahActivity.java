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

public class TambahActivity extends AppCompatActivity {

    public static String EXTRA_CATATAN = "extra_catatan";
    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtDeskripsi)
    EditText edtDeskripsi;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;


    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;

    private Catatan mCatatan;
    private CatatanHelper mCatatanHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);

        mCatatanHelper = new CatatanHelper(this);
        mCatatanHelper.open();

        mCatatan = getIntent().getParcelableExtra(EXTRA_CATATAN);


        getSupportActionBar().setTitle("Tambah Catatan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        String strJudul = edtJudul.getText().toString().trim();
        String strDeskripsi = edtDeskripsi.getText().toString().trim();

        boolean isEmpty = false;

        if (TextUtils.isEmpty(strJudul)) {
            isEmpty = true;
            edtJudul.setError("Judul harus diisi");
        }

        if (!isEmpty) {
            Catatan catatanBaru = new Catatan();
            catatanBaru.setJudul(strJudul);
            catatanBaru.setDeskrsipsi(strDeskripsi);

            catatanBaru.setTanggal(getCurrentDate());
            mCatatanHelper.insert(catatanBaru);

            setResult(RESULT_ADD);
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showAlertDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }


    private void showAlertDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Kembali");
        alertDialogBuilder
                .setMessage("Apakah anda ingin membatalkan perubahan ini?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                            finish();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCatatanHelper != null) {
            mCatatanHelper.close();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }
}