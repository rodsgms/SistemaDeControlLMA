package com.sistemaasistenciaycomunicados.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemaasistenciaycomunicados.R;

import java.util.List;

public class ComunicadoAdapter extends RecyclerView.Adapter<ComunicadoAdapter.ViewHolder> {
    private final Context context;
    private final List<Comunicado> comunicadoList;

    public ComunicadoAdapter(Context context, List<Comunicado> comunicadoList) {
        this.context = context;
        this.comunicadoList = comunicadoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comunicado comunicado = comunicadoList.get(position);

        holder.tvText.setText(comunicado.getText());
        holder.tvDate.setText(comunicado.getDate());
        holder.tvUsername.setText("Recursos humanos");
    }

    @Override
    public int getItemCount() {
        return comunicadoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvText, tvDate, tvUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.item_info_tv_text);
            tvDate = itemView.findViewById(R.id.item_info_tv_date);
            tvUsername = itemView.findViewById(R.id.item_info_tv_username);
        }
    }
}
