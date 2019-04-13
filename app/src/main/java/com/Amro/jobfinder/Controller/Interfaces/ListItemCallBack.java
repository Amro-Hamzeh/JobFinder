package com.Amro.jobfinder.Controller.Interfaces;

import android.os.Bundle;
import android.view.View;

import com.Amro.jobfinder.View.Items.ListItem;

/**
 * Created by Amro 13/4/2019
 * Interface to handle clicks on items inside the Recycler View holder.
 * To avoid cluttering viewholder code.
 */
public interface ListItemCallBack {

    void onItemClicked(View view, ListItem listItem, int position);

    void onItemAction(int action, int position, Bundle data);

}
