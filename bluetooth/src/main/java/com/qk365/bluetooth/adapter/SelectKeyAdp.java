package com.qk365.bluetooth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qk365.bluetooth.R;

import java.util.List;


/**
 */
public class SelectKeyAdp extends RecyclerView.Adapter<SelectKeyAdp.SimpleHolder> {

    List<String> list;
    Context context;

    SelectKeyAdpClick selectKeyAdpClick;

    public SelectKeyAdp(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyDataSetChanged(List<String> list) {
        this.list = list;
        super.notifyDataSetChanged();
    }

    public void setAddressSearchClick(SelectKeyAdpClick selectKeyAdpClick) {
        this.selectKeyAdpClick = selectKeyAdpClick;
    }

    @Override
    public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleHolder(LayoutInflater.from(context).inflate(R.layout.item_select_key, parent, false));


    }

    @Override
    public void onBindViewHolder(SimpleHolder holder, int position) {
        final String key = list.get(position);
        if(!TextUtils.isEmpty(key)){
            holder.tvKey.setText(key);
        }
        holder.tvKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectKeyAdpClick != null) {
                    selectKeyAdpClick.clickItem(key);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class SimpleHolder extends RecyclerView.ViewHolder {
        TextView tvKey;

        public SimpleHolder(View itemView) {
            super(itemView);
            tvKey = (TextView)itemView.findViewById(R.id.tvKey);
        }
    }

    public interface SelectKeyAdpClick {
        void clickItem(String key);
    }
}
