package com.Amro.jobfinder.Model.Webservices;

import com.Amro.jobfinder.Model.Responses.GitHubJobsResponse;
import com.Amro.jobfinder.Model.Responses.SearchJobsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
    // list of APIs(webservices) to deal with.
public interface WebServices {
    @GET("?")
    Call<List<GitHubJobsResponse>> getGithubJobsData();
    @GET("?")
    Call<List<SearchJobsResponse>> getSearchJobsData();
}
