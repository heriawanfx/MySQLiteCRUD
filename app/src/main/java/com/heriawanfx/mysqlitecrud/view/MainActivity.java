package com.heriawanfx.mysqlitecrud.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.heriawanfx.mysqlitecrud.R;
import com.heriawanfx.mysqlitecrud.adapter.CatatanAdapter;
import com.heriawanfx.mysqlitecrud.db.CatatanHelper;
import com.heriawanfx.mysqlitecrud.model.Catatan;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.heriawanfx.mysqlitecrud.view.FormActivity.REQUEST_UPDATE;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.rvCatatan)
    RecyclerView rvCatatan;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private LinkedList<Catatan> list;
    private CatatanAdapter adapter;
    private CatatanHelper catatanHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Catatan");


        rvCatatan.setLayoutManager(new LinearLayoutManager(this));
        rvCatatan.setHasFixedSize(true);

        catatanHelper = new CatatanHelper(this);
        catatanHelper.open();

        list = new LinkedList<>();

        adapter = new CatatanAdapter(this);
        adapter.setListCatatans(list);
        rvCatatan.setAdapter(adapter);

        //agat data ketika ada perubahan langsung terupdate/diberitahu
        new LoadCatatanAsync().execute();


    }

    @OnClick(R.id.fabAdd)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        startActivityForResult(intent, TambahActivity.REQUEST_ADD);
    }

    private class LoadCatatanAsync extends AsyncTask<Void, Void, ArrayList<Catatan>> {
        //sebelum dieksekusi
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<Catatan> doInBackground(Void... voids) {
            return catatanHelper.query();
        }

        //selesai dieksekusi
        @Override
        protected void onPostExecute(ArrayList<Catatan> mCatatans) {
            super.onPostExecute(mCatatans);
            progressBar.setVisibility(View.GONE);

            list.addAll(mCatatans);
            adapter.setListCatatans(list);
            adapter.notifyDataSetChanged();

            if (list.size() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TambahActivity.REQUEST_ADD){
            if (resultCode == TambahActivity.RESULT_ADD){
                new LoadCatatanAsync().execute();
                showSnackbarMessage("Satu catatan berhasil ditambahkan");
            }
        }
        else if (requestCode == REQUEST_UPDATE) {

            if (resultCode == UbahActivity.RESULT_UPDATE) {
                new LoadCatatanAsync().execute();
                showSnackbarMessage("Satu catatan berhasil diubah");
            }

            else if (resultCode == UbahActivity.RESULT_DELETE) {
                int position = data.getIntExtra(UbahActivity.EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setListCatatans(list);
                adapter.notifyDataSetChanged();
                showSnackbarMessage("Satu catatan berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (catatanHelper != null){
            catatanHelper.close();
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvCatatan, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    
    
}
