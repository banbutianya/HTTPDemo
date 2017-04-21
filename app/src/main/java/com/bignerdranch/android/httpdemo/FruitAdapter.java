package com.bignerdranch.android.httpdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by acer-1 on 2017/4/18.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;
        TextView fruitNeirong;
        TextView fruitPinglun;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
            fruitNeirong = (TextView) itemView.findViewById(R.id.fruit_neirong);
            fruitPinglun = (TextView) itemView.findViewById(R.id.fruit_pinglun);
        }
    }

    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitNeirong.setText(fruit.getNeirong());
        holder.fruitPinglun.setText(fruit.getPinglun());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


}
