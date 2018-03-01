package com.adrian.basemodule.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qing on 2017/8/15 0015.
 */

public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> data;

    public BaseRVAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> datas) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.addAll(datas);
        notifyDataSetChanged();
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public T getItem(int pos) {
        if (data == null || pos >= data.size()) {
            return null;
        }
        return data.get(pos);
    }

    public void addItem(T t) {
        if (data == null) {
            data = new ArrayList<>();
        }
//        data.add(t);
//        notifyDataSetChanged();
        addItem(data.size(), t);
    }

    public void addItem(int pos, T t) {
//        if (data == null) {
//            data = new ArrayList<>();
//        }
        data.add(pos, t);
//        notifyDataSetChanged();\
        notifyItemInserted(pos);
    }

    public void removeItem(T t) {
//        if (data != null && data.contains(t)) {
//            data.remove(t);
//            notifyDataSetChanged();
//        }
        if (data != null && data.size() > 0) {
            int pos = data.indexOf(t);
            data.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void removeItem(int pos) {
        if (data != null && data.size() > pos) {
            data.remove(pos);
//            notifyDataSetChanged();
            notifyItemRemoved(pos);
        }
    }

    protected void showToast(@Nullable String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }
}
