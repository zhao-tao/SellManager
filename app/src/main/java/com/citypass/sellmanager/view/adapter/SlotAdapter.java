package com.citypass.sellmanager.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.SlotBean;

import java.util.ArrayList;

/**
 * Created by 赵涛 on 2018/8/23.
 */
public class SlotAdapter extends RecyclerView.Adapter<mViewHolder> {

    private final Context context;
    private final ArrayList<SlotBean> datas;
    private final LayoutInflater inflater;
    private onItemClick itemClick;

    public SlotAdapter(Context context, ArrayList<SlotBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    public interface onItemClick {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(onItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.list_rv, parent, false);
        mViewHolder mViewHolder = new mViewHolder(inflate);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.tvItem.setText(datas.get(position).getSlotBalance());
        if (!datas.get(position).getIsSell()) {
            holder.bg.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

class mViewHolder extends RecyclerView.ViewHolder {
    TextView tvItem;
    RelativeLayout bg;

    public mViewHolder(View itemView) {
        super(itemView);
        bg = itemView.findViewById(R.id.bg);
        tvItem = itemView.findViewById(R.id.tv_amount);
    }
}
