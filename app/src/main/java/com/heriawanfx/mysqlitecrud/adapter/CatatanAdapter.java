package com.heriawanfx.mysqlitecrud.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heriawanfx.mysqlitecrud.CustomOnItemClickListener;
import com.heriawanfx.mysqlitecrud.R;
import com.heriawanfx.mysqlitecrud.model.Catatan;
import com.heriawanfx.mysqlitecrud.view.FormActivity;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.NoteViewholder> {
    private LinkedList<Catatan> listCatatans;
    private Activity activity;

    public CatatanAdapter(Activity activity) {
        this.activity = activity;
    }

    public LinkedList<Catatan> getListCatatans() {
        return listCatatans;
    }

    public void setListCatatans(LinkedList<Catatan> listCatatans) {
        this.listCatatans = listCatatans;
    }

    @Override
    public NoteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catatan, parent, false);
        return new NoteViewholder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewholder holder, int position) {
        holder.txtJudul.setText(getListCatatans().get(position).getJudul());
        holder.txtTanggal.setText(getListCatatans().get(position).getTanggal());
        holder.txtDeskripsi.setText(getListCatatans().get(position).getDeskrsipsi());
        holder.cardCatatan.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_POSITION, position);
                intent.putExtra(FormActivity.EXTRA_CATATAN, getListCatatans().get(position));
                activity.overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListCatatans().size();
    }

    public class NoteViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTanggal)
        TextView txtTanggal;
        @BindView(R.id.txtJudul)
        TextView txtJudul;
        @BindView(R.id.txtDeskripsi)
        TextView txtDeskripsi;
        @BindView(R.id.cardCatatan)
        CardView cardCatatan;

        public NoteViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}