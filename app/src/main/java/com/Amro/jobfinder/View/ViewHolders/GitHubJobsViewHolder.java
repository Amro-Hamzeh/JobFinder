package com.Amro.jobfinder.View.ViewHolders;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Amro.jobfinder.Controller.Interfaces.ListItemCallBack;

import com.Amro.jobfinder.Controller.Utils.Constants;
import com.Amro.jobfinder.Model.Responses.GitHubJobsResponse;

import com.Amro.jobfinder.R;
import com.Amro.jobfinder.View.Items.ListItem;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Amro 13/4/2019
 * Viewholder for handling recycler item rows with Githob jobs API response items data in them.
 */
public class GitHubJobsViewHolder extends BaseViewHolder {

    private TextView mJobTitleTextView;
    private TextView mCompanyNameTextView;
    private TextView mDateTextView;
    private TextView mLocationTextView;
    private ImageView mLogoImageView;
    private ConstraintLayout mJobLayout;

    //initialising views and click listeners.
    public GitHubJobsViewHolder(@NonNull View itemView, ListItemCallBack listItemCallBack) {
        super(itemView, listItemCallBack);
        mJobTitleTextView = findViewById(R.id.tv_job);
        mCompanyNameTextView = findViewById(R.id.tv_companyName);
        mDateTextView = findViewById(R.id.tv_date);
        mLocationTextView = findViewById(R.id.tv_location);
        mLogoImageView = findViewById(R.id.img_company);
        mJobLayout = findViewById(R.id.layout_job);
        attachClickListener(mJobLayout);
    }
    //main functionality of view holder
    @Override
    public void draw(ListItem listItem)  {
        super.draw(listItem);
        GitHubJobsResponse gitHubJobsResponse = (GitHubJobsResponse) listItem;
        //using Glide library for imageloading
        Glide.with(itemView.getContext())
                .load(gitHubJobsResponse.companyLogo)
                .placeholder(R.drawable.image_placeholder_small) //place holder incase of error.
                .fitCenter()
                .into(mLogoImageView);
        mJobTitleTextView.setText(gitHubJobsResponse.title);
        mCompanyNameTextView.setText(gitHubJobsResponse.company);
        mLocationTextView.setText(gitHubJobsResponse.location);
        //handling date formatting to convert Month in letters to numbers.
        String[] dateelements=gitHubJobsResponse.createdAt.split(" ");
        Date date = null;
        Calendar cal = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(dateelements[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date!=null)
        cal.setTime(date);
        int month=cal.get(Calendar.MONTH);
        month++;
        if(month<10)
        {
            mDateTextView.setText(dateelements[2]+"/0"+month+"/"+dateelements[5]);
        }
        else if(month==13) {
            mDateTextView.setText(dateelements[2] + "/01/" + dateelements[5]);
        }
        else
        {
            mDateTextView.setText(dateelements[2]+"/"+month+"/"+dateelements[5]);
        }
    }

}
