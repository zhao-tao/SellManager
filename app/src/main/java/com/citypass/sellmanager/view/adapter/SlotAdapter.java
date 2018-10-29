package com.citypass.sellmanager.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.SlotBean;

import java.util.ArrayList;

/**
 * Created by 赵涛 on 2018/8/23.
 */
public class SlotAdapter extends RecyclerView.Adapter<mViewHolder> {

    private final ArrayList<SlotBean> datas;
    private final LayoutInflater inflater;
    private onItemClickListener itemClick;
    private Context context;

    public SlotAdapter(Context context, ArrayList<SlotBean> datas) {
        this.datas = datas;

        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(onItemClickListener itemClick) {
        this.itemClick = itemClick;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.list_rv, parent, false);
        mViewHolder mViewHolder = new mViewHolder(inflate);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        holder.tvItem.setText(datas.get(position).getSlotBalance() + "");
        holder.tvNum.setText(datas.get(position).getLocalId() + "");

        if (!datas.get(position).isSell()) {
            holder.bg.setBackground(context.getResources().getDrawable(R.drawable.shape_red));
        } else if (datas.get(position).getSlotBalance() > 5) {
            holder.bg.setBackground(context.getResources().getDrawable(R.drawable.shape_green));
        } else {
            holder.bg.setBackgroundColor(Color.YELLOW);
        }

        View itemView = ((FrameLayout) holder.itemView).getChildAt(0);
        if (itemClick != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    itemClick.onItemClick(holder.itemView, layoutPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

class mViewHolder extends RecyclerView.ViewHolder {
    TextView tvNum;
    TextView tvItem;
    RelativeLayout bg;

    public mViewHolder(View itemView) {
        super(itemView);
        bg = itemView.findViewById(R.id.bg);
        tvNum = itemView.findViewById(R.id.tv_num);
        tvItem = itemView.findViewById(R.id.tv_amount);
    }
}
