package com.Amro.jobfinder.View.ViewHolders;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.Amro.jobfinder.Controller.Interfaces.ListItemCallBack;
import com.Amro.jobfinder.View.Items.ListItem;

import java.text.ParseException;

//Base view holder implementation to handle and simplify basic/shared functionality
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected ListItemCallBack mCallback;
    protected ListItem mListItem;
    protected int mPosition;


    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mPosition=getAdapterPosition();
    }

    public BaseViewHolder(@NonNull View itemView, ListItemCallBack mCallback) {
        super(itemView);
        this.mCallback = mCallback;
    }
    //to attach click listeners to the views in view holder, to attach listitemcallback to them eventually
    protected void attachClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) itemView.findViewById(id);
    }

    public void draw(ListItem listItem)  {
        mListItem = listItem;
        mPosition = getAdapterPosition();
    }

    public void onViewAttachedToWindow() {
    }

    public void onViewRecycled() {
    }

    public void onViewDetachedFromWindow() {
    }
    //attaching list item callback to the views.
    @Override
    public void onClick(View v) {
        if (mCallback != null) {
            mCallback.onItemClicked(v, mListItem, mPosition);
        }

        else
        {
            Log.e("callback error","No callback attached");
        }
    }
}
