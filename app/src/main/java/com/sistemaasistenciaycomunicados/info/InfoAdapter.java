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

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private final Context context;
    private final List<InfoMy> infoMyList;

    public InfoAdapter(Context context, List<InfoMy> infoMyList) {
        this.context = context;
        this.infoMyList = infoMyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoMy infoMy = infoMyList.get(position);

        holder.tvText.setText(infoMy.getText());
    }

    @Override
    public int getItemCount() {
        return infoMyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.item_info_tv_text);
        }
    }
}
