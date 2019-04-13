package com.Amro.jobfinder.View.Items;


import com.Amro.jobfinder.R;
import com.Amro.jobfinder.View.ViewHolders.BaseViewHolder;
import com.Amro.jobfinder.View.ViewHolders.GitHubJobsViewHolder;
import com.Amro.jobfinder.View.ViewHolders.SearchJobsViewHolder;
//list item types for the recycler adapter implementation.
public class ListItemTypes {

    public static ListItemType NONE = new ListItemType(
            BaseViewHolder.class, 0);

    public static ListItemType GITHUB_JOBS = new ListItemType(
            GitHubJobsViewHolder.class, R.layout.item_github_job);
    public static ListItemType SEARCH_JOBS = new ListItemType(
            SearchJobsViewHolder.class, R.layout.item_github_job);
}
