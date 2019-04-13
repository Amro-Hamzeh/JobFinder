package com.Amro.jobfinder.View.Items;


//related to recycler adapter implementation
public class ListItemType {
    public static int type;
    public Class<?> viewHolder;
    public int layoutResId;
    public int itemViewType;

    public ListItemType(Class<?> viewHolderClass, int layoutResId) {
        this.viewHolder = viewHolderClass;
        this.layoutResId = layoutResId;
        this.itemViewType = type++;

    }
}
