package com.Amro.jobfinder.View.ViewHolders;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Amro.jobfinder.Controller.Interfaces.ListItemCallBack;
import com.Amro.jobfinder.Model.Responses.GitHubJobsResponse;
import com.Amro.jobfinder.Model.Responses.SearchJobsResponse;
import com.Amro.jobfinder.R;
import com.Amro.jobfinder.View.Items.ListItem;
import com.bumptech.glide.Glide;

import java.text.ParseException;

/**
 * Created by Amro 13/4/2019
 * View holder to handle the recycler items with Search.gov jobs api response in.
 */
public class SearchJobsViewHolder extends BaseViewHolder {

    private TextView mJobTitleTextView;
    private TextView mCompanyNameTextView;
    private TextView mDateTextView;
    private TextView mLocationTextView;
    private ImageView mLogoImageView;
    private ConstraintLayout mJobLayout;

    public SearchJobsViewHolder(@NonNull View itemView, ListItemCallBack mCallback) {
        super(itemView, mCallback);
        mJobTitleTextView=findViewById(R.id.tv_job);
        mCompanyNameTextView=findViewById(R.id.tv_companyName);
        mDateTextView=findViewById(R.id.tv_date);
        mLocationTextView=findViewById(R.id.tv_location);
        mLogoImageView=findViewById(R.id.img_company);
        mJobLayout=findViewById(R.id.layout_job);
        attachClickListener(mJobLayout);

    }

    @Override
    public void draw(ListItem listItem)  {
        super.draw(listItem);
        SearchJobsResponse searchJobsResponse= (SearchJobsResponse) listItem;
        Glide.with(itemView.getContext())
                .load(R.drawable.image_placeholder_small)
                .placeholder(R.drawable.image_placeholder_small)
                .centerCrop()
                .into(mLogoImageView);
        mJobTitleTextView.setText(searchJobsResponse.title);
        mCompanyNameTextView.setText(searchJobsResponse.organizationName);
        mLocationTextView.setText(searchJobsResponse.locations.get(0));
        for(int i=1;i<searchJobsResponse.locations.size();i++) // can have multiple locations with this API
        {
            mLocationTextView.setText(mLocationTextView.getText().toString()+ "+"+searchJobsResponse.locations.get(i));
        }
        //handling dateviewing and formatting.
        String[] dateelements=searchJobsResponse.startDate.split("-");
        mDateTextView.setText(dateelements[2]+"/"+dateelements[1]+"/"+dateelements[0]);

    }
}