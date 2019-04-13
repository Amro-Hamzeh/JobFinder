package com.Amro.jobfinder.Model.Responses;

import com.Amro.jobfinder.View.Items.ListItem;
import com.Amro.jobfinder.View.Items.ListItemType;
import com.Amro.jobfinder.View.Items.ListItemTypes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amro 13/4/2019
 * Response class to handle the API response from github jobs API
 */
public class GitHubJobsResponse extends ListItem {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("company_url")
    @Expose
    public String companyUrl;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("how_to_apply")
    @Expose
    public String howToApply;
    @SerializedName("company_logo")
    @Expose
    public String companyLogo;

    public int jobFinderID=-1;

    @Override //List item type related to the recycler adapter implementation.
    public ListItemType getListItemType() {
        return ListItemTypes.GITHUB_JOBS;
    }
}
