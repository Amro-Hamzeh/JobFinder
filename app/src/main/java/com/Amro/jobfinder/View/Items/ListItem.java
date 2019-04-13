package com.Amro.jobfinder.View.Items;

//related to recycler adapter implementation
public class ListItem {
    protected transient com.Amro.jobfinder.View.Items.ListItemType mListItemType;

    public long getStableId() {
        return 0;
    }

    public ListItemType getListItemType() {
        return mListItemType;
    }

    public void setListItemType(ListItemType listItemType) {
        this.mListItemType = listItemType;
    }
}