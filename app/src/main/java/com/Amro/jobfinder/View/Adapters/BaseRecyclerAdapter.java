package com.Amro.jobfinder.View.Adapters;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Amro.jobfinder.Controller.Interfaces.ListItemCallBack;
import com.Amro.jobfinder.View.Items.ListItem;
import com.Amro.jobfinder.View.Items.ListItemType;
import com.Amro.jobfinder.View.ViewHolders.BaseViewHolder;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amro 13/4/2019
 * My recycler adapter implementation, it uses reflection API with list item types to handle multiple view types.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter {
    protected ListItemCallBack listItemCallBack;
    protected List<ListItem> listItems= new ArrayList<>();
    protected ListItemType listItemType;
    //Constructor, including list item callback to handle the interface clicks.
    public BaseRecyclerAdapter(List<ListItem> listItems, ListItemCallBack listItemCallBack) {
        this.listItems = listItems;
        this.listItemCallBack=listItemCallBack;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView;
        itemView = inflater.inflate(listItemType.layoutResId, viewGroup, false);
        try {
            Constructor constructors[] = listItemType.viewHolder.getDeclaredConstructors();
            if (constructors.length > 0) {
                if (constructors[0].getParameterTypes().length == 1) {
                    return (BaseViewHolder) constructors[0].newInstance(itemView);
                } else {
                    return (BaseViewHolder) constructors[0].newInstance(itemView,listItemCallBack);
                }
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Log.e("TAG", sw.toString());
            Log.e("REFLECTION EXCEPTION", e.getCause().toString());
        }
        return new BaseViewHolder(itemView,listItemCallBack);
    }

    @Override
    public int getItemViewType(int position) {
        listItemType=listItems.get(position).getListItemType();
        return listItems.get(position).getListItemType().itemViewType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    ((BaseViewHolder) viewHolder).draw(listItems.get(i)); //the general method used for viewholder functionality and sending the list item object

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onViewRecycled();
    }
    // to handle research results data switching.
    public void setListItems(List<ListItem> listItems)
    {
        this.listItems=listItems;
        notifyDataSetChanged();
    }


    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }


    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }
}
