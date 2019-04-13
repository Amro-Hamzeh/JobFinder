package com.Amro.jobfinder.Controller.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Amro.jobfinder.Controller.Interfaces.ListItemCallBack;


import com.Amro.jobfinder.Controller.Utils.Constants;
import com.Amro.jobfinder.Model.Responses.GitHubJobsResponse;

import com.Amro.jobfinder.Model.Responses.SearchJobsResponse;
import com.Amro.jobfinder.Model.Server.RetrofitInstance;
import com.Amro.jobfinder.Model.Webservices.WebServices;
import com.Amro.jobfinder.R;
import com.Amro.jobfinder.View.Adapters.BaseRecyclerAdapter;
import com.Amro.jobfinder.View.Decorations.CardDecoration;
import com.Amro.jobfinder.View.Items.ListItem;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amro 13/4/2019
 * the main fragment of the app, where most functions of the app are implemented.
 * divided into functions to simplify editing and changing
 */

public class MainFragment extends BaseFragment implements View.OnClickListener, ListItemCallBack,
        AdapterView.OnItemSelectedListener, PlaceSelectionListener {
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private List<ListItem> mListItemList;
    private List<ListItem> currentItemsList;
    private WebServices webServices;
    private ProgressDialog progressDialog;
    private PopupWindow mFilterPopupWindow;
    private TextView mFilterTextView;

    private String mFilterType;
    private SearchView searchView;
    private Spinner providerSpinner;
    private PlaceAutocompleteFragment placeAutoComplete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycler();
    }

    /**
     * setting up recycler view, checks if the adapter already has data or not.
     */
    private void setupRecycler() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new CardDecoration((int) getContext().getResources().getDimension(R.dimen.margin8)));
        if (mAdapter == null) {
            getGitHubJobs();
        } else {
            mRecyclerView.setAdapter(mAdapter);

        }
        initviews();


    }

    /**
     * initialising views, setting elevation and attaching listeners to perform functionality
     */
    private void initviews() {
        mFilterTextView = findViewById(R.id.tv_filter_type);
        mFilterTextView.setOnClickListener(this);
        ViewCompat.setElevation(mFilterTextView, 10);

        providerSpinner = findViewById(R.id.sp_job_type);
        ViewCompat.setElevation(providerSpinner, 10);

        searchView = findViewById(R.id.search_view);
        ViewCompat.setElevation(searchView, 10);


        //initialising of the place autocomplete fragment for location search function.
        placeAutoComplete = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.getView().setVisibility(View.GONE);
        placeAutoComplete.setOnPlaceSelectedListener(this);

    }

    /**
     * the method for calling the API for the github jobs and populate the recycler adapter with it
     */
    private void getGitHubJobs() {
        mListItemList = new ArrayList<>();
        webServices = null;
        webServices = RetrofitInstance.getRetrofitInstance(Constants.BASE_GITHUB_JOBS_URL).create(WebServices.class);
        if (progressDialog == null) { //initialising and showing progress dialog while the data is loading.
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle(getString(R.string.loading));
            progressDialog.setMessage(getString(R.string.loading_wait));
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(true);
        }
        progressDialog.show();
        Call<List<GitHubJobsResponse>> call = webServices.getGithubJobsData();
        call.enqueue(new Callback<List<GitHubJobsResponse>>() {
            @Override
            public void onResponse(Call<List<GitHubJobsResponse>> call, Response<List<GitHubJobsResponse>> response) {
                //handling on success response
                if (response.body() != null) {
                    mListItemList.addAll(response.body());
                    mAdapter = new BaseRecyclerAdapter(mListItemList, MainFragment.this);
                    mRecyclerView.setAdapter(mAdapter);
                    getSearchJobsData();

                }
            }

            //failure of API request is handling here.
            @Override
            public void onFailure(Call<List<GitHubJobsResponse>> call, Throwable t) {
                mAdapter = new BaseRecyclerAdapter(mListItemList, MainFragment.this);
                mRecyclerView.setAdapter(mAdapter);
                Toast.makeText(getContext(), "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                getSearchJobsData();
            }
        });


    }

    /**
     * Method for calling and handling API response from the search.gov jobs API
     */
    private void getSearchJobsData() {
        webServices = RetrofitInstance.getRetrofitInstance(Constants.BASE_SEARCH_JOBS_URL).create(WebServices.class);
        Call<List<SearchJobsResponse>> call = webServices.getSearchJobsData();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle(getString(R.string.loading));
            progressDialog.setMessage(getString(R.string.loading_wait));
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
        }

        call.enqueue(new Callback<List<SearchJobsResponse>>() {
            @Override
            public void onResponse(Call<List<SearchJobsResponse>> call, Response<List<SearchJobsResponse>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                   mListItemList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onFailure(Call<List<SearchJobsResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),
                        "Something went wrong...Error message: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * Method for showing popup for selecting the filter
     * I choose to use the popup window method for it, as data is static atm.
     * If there were more filters to be added or it's dynamic, a recycler view can be implemented.
     */
    @SuppressLint("InflateParams")
    private void showPopup() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View popupView = layoutInflater.inflate(R.layout.popup_filters, null);
        //attaching special onClick Listener for the views in popup window to handle clicks.
        popupView.findViewById(R.id.txt_filter1).setOnClickListener(onPopupClick);
        popupView.findViewById(R.id.txt_filter2).setOnClickListener(onPopupClick);
        popupView.findViewById(R.id.txt_filter3).setOnClickListener(onPopupClick);
        int width = (int) getResources().getDimension(R.dimen.margin200);
        mFilterPopupWindow = new PopupWindow(
                popupView,
                width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFilterPopupWindow.setFocusable(true);
        mFilterPopupWindow.setOutsideTouchable(true);
        mFilterPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFilterPopupWindow.showAsDropDown(mFilterTextView);
    }

    /**
     * Method for handling clicks on the popup window fields.
     */
    private View.OnClickListener onPopupClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            mFilterType = textView.getText().toString();
            mFilterTextView.setText(mFilterType);
            mFilterPopupWindow.dismiss();
            mAdapter.setListItems(mListItemList);
            if (mFilterType.equals(getString(R.string.provider))) { //if filter by provider was selected
                searchView.setVisibility(View.GONE);
                providerSpinner.setVisibility(View.VISIBLE);
                placeAutoComplete.getView().setVisibility(View.GONE);
                setupSpinner();
            } else if (mFilterType.equals(getString(R.string.title))) {//if filter by job title was selected
                searchView.setVisibility(View.VISIBLE);
                providerSpinner.setVisibility(View.GONE);
                placeAutoComplete.getView().setVisibility(View.GONE);
                setupSearch();
            } else if (mFilterType.equals(getString(R.string.location))) {//if filter by location was selected
                searchView.setVisibility(View.GONE);
                providerSpinner.setVisibility(View.GONE);
                placeAutoComplete.getView().setVisibility(View.VISIBLE);
            }
        }
    };

    private void setupSpinner() { //setting up spinner for providers.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providerSpinner.setAdapter(adapter);
        providerSpinner.setOnItemSelectedListener(this);
    }

    private void setupSearch() { //setting up the search via job title
        searchView.setIconified(false); //to make all the search bar clickable
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { //handles search on submit button on keyboard
                currentItemsList = new ArrayList<>();

                for (ListItem listItem : mListItemList) {
                    if (listItem instanceof GitHubJobsResponse) {
                        GitHubJobsResponse gitHubJobsResponse = (GitHubJobsResponse) listItem;
                        if (gitHubJobsResponse.title.toLowerCase().contains(query.toLowerCase())) {
                            currentItemsList.add(listItem);
                        }
                    } else if (listItem instanceof SearchJobsResponse) {
                        SearchJobsResponse searchJobsResponse = (SearchJobsResponse) listItem;
                        if (searchJobsResponse.title.toLowerCase().contains(query.toLowerCase())) {
                            currentItemsList.add(listItem);
                        }
                    }
                }
                mAdapter.setListItems(currentItemsList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //handles search as user is actively inputting text
                currentItemsList = new ArrayList<>();

                for (ListItem listItem : mListItemList) {
                    if (listItem instanceof GitHubJobsResponse) {
                        GitHubJobsResponse gitHubJobsResponse = (GitHubJobsResponse) listItem;
                        if (gitHubJobsResponse.title.toLowerCase().contains(newText.toLowerCase())) {
                            currentItemsList.add(listItem);
                        }
                    } else if (listItem instanceof SearchJobsResponse) {
                        SearchJobsResponse searchJobsResponse = (SearchJobsResponse) listItem;
                        if (searchJobsResponse.title.toLowerCase().contains(newText.toLowerCase())) {
                            currentItemsList.add(listItem);
                        }
                    }
                }


                mAdapter.setListItems(currentItemsList);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {//to empty search field if close(x) was clicked.
            @Override
            public boolean onClose() {
                mAdapter.setListItems(mListItemList);
                searchView.clearFocus();
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) { //handles on click events in the fragment
        switch (v.getId()) {
            case R.id.tv_filter_type:// if filter by textview was clicked
                showPopup();
                break;
        }

    }

    /**
     * an interface built to handle item click callback from inside view holders, the clicks are handled here in the fragment
     */
    @Override
    public void onItemClicked(View view, ListItem listItem, int position) {
        if (listItem instanceof GitHubJobsResponse) {
            GitHubJobsResponse gitHubJobsResponse = (GitHubJobsResponse) listItem;
            JobDetailsFragment jobDetailsFragment = JobDetailsFragment.newInstance(gitHubJobsResponse.url);
            moveToFragment(jobDetailsFragment, R.id.container, true); //moves to jobdetails fragment after supplying the job URL

        } else if (listItem instanceof SearchJobsResponse) {
            SearchJobsResponse searchJobsResponse = (SearchJobsResponse) listItem;
            JobDetailsFragment jobDetailsFragment = JobDetailsFragment.newInstance(searchJobsResponse.url);
            moveToFragment(jobDetailsFragment, R.id.container, true);
        }


    }

    @Override
    public void onItemAction(int action, int position, Bundle data) {

    }
    //spinner on item selected action handling for provider job filtering.
    //decided to go with spinner here for simplicity and using string, however adding IDs to the response classes and using it works too.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String provider = (String) parent.getItemAtPosition(position);

        if (provider.equals(getString(R.string.both))) {
            mAdapter.setListItems(mListItemList);
        } else if (provider.equals((getString(R.string.github)))) {
            currentItemsList = new ArrayList<>();
            for (ListItem listItem : mListItemList) {
                if (listItem instanceof GitHubJobsResponse) {
                    currentItemsList.add(listItem);
                }
                mAdapter.setListItems(currentItemsList);
            }
        } else if (provider.equals(getString(R.string.search_jobs))) {
            currentItemsList = new ArrayList<>();
            for (ListItem listItem : mListItemList) {
                if (listItem instanceof SearchJobsResponse) {
                    currentItemsList.add(listItem);
                }
                mAdapter.setListItems(currentItemsList);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    // handling place autocomplete response
    @Override
    public void onPlaceSelected(Place place) {
        if (place.getName() != null) {
            placeAutoComplete.setText(place.getName());
            filterRecyclerByLocation(place.getName());
        }
    }
    //filtering recyclerview results by location after receiving onplace selected data.
    private void filterRecyclerByLocation(CharSequence name) {
        currentItemsList = new ArrayList<>();
        for (ListItem listItem : mListItemList) {
            if (listItem instanceof GitHubJobsResponse) {
                GitHubJobsResponse gitHubJobsResponse = (GitHubJobsResponse) listItem;
                if (gitHubJobsResponse.location.toLowerCase().contains(name.toString().toLowerCase())) {
                    currentItemsList.add(listItem);
                }
            } else if (listItem instanceof SearchJobsResponse) {
                SearchJobsResponse searchJobsResponse = (SearchJobsResponse) listItem;
                for (String location : searchJobsResponse.locations)
                    if (location.toLowerCase().contains(name.toString().toLowerCase())) {
                        currentItemsList.add(listItem);
                    }
            }
        }
        mAdapter.setListItems(currentItemsList);
    }
    //to avoid a crash with place autocomplete fragment on back pressed button
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PlaceAutocompleteFragment f = (PlaceAutocompleteFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.place_autocomplete);
        if (f != null)
            getActivity().getFragmentManager().beginTransaction().remove(f).commit();
    }

    @Override
    public void onError(Status status) {
        Log.e("TEST", status.toString());
    }
}
