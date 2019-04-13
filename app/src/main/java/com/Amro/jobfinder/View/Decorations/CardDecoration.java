package com.Amro.jobfinder.View.Decorations;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
    //Recyclerview item decoration to handle adding margins to recycler view items
public class CardDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public CardDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=space;
        outRect.top=space;
        outRect.right=space;
        outRect.left=space;
    }


}
