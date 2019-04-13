package com.Amro.jobfinder.Model.Responses;

import com.Amro.jobfinder.View.Items.ListItem;
import com.Amro.jobfinder.View.Items.ListItemType;
import com.Amro.jobfinder.View.Items.ListItemTypes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchJobsResponse extends ListItem {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("position_title")
    @Expose
    public String title;
    @SerializedName("organization_name")
    @Expose
    public String organizationName;
    @SerializedName("rate_interval_code")
    @Expose
    public String rateIntervalCode;
    @SerializedName("minimum")
    @Expose
    public Integer minimum;
    @SerializedName("maximum")
    @Expose
    public Integer maximum;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("locations")
    @Expose
    public List<String> locations = null;
    @SerializedName("url")
    @Expose
    public String url;

    public int jobFinderID=-1;

    @Override
    public ListItemType getListItemType() {
        return ListItemTypes.SEARCH_JOBS;
    }
}
