package com.Amro.jobfinder;

import com.Amro.jobfinder.Controller.Fragments.JobDetailsFragment;
import com.Amro.jobfinder.Controller.Utils.Constants;
import com.Amro.jobfinder.Model.Responses.GitHubJobsResponse;
import com.Amro.jobfinder.Model.Responses.SearchJobsResponse;
import com.Amro.jobfinder.Model.Server.RetrofitInstance;
import com.Amro.jobfinder.Model.Webservices.WebServices;
import com.Amro.jobfinder.View.Decorations.CardDecoration;
import com.Amro.jobfinder.View.Items.ListItem;
import com.Amro.jobfinder.View.Items.ListItemType;
import com.Amro.jobfinder.View.Items.ListItemTypes;
import com.google.android.gms.common.data.DataBufferObserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import java.io.IOException;

import static org.mockito.Mockito.when;
//simple unit test to check item types.
public class ResponsesTest {
    @Mock
    private GitHubJobsResponse gitHubJobsResponse;
    private SearchJobsResponse searchJobsResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void validListItemTypes() throws IOException {
        GitHubJobsResponse gitHubJobsResponse=Mockito.mock(GitHubJobsResponse.class);
        SearchJobsResponse searchJobsResponse=Mockito.mock(SearchJobsResponse.class);
        WebServices webServices = RetrofitInstance.getRetrofitInstance(Constants.BASE_SEARCH_JOBS_URL).create(WebServices.class);

        when(gitHubJobsResponse.getListItemType())
                .thenReturn(ListItemTypes.GITHUB_JOBS);
        when(searchJobsResponse.getListItemType())
                .thenReturn(ListItemTypes.SEARCH_JOBS);

    }
}
