package com.Amro.jobfinder.Controller.Fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created by Amro 13/4/2019
 * Base fragment class, to simply fragments switching and some methods inside it
 */
public class BaseFragment extends Fragment {

    protected View mRoot;
    //we assign view to mRoot variable, to make findViewById method simpler to use
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRoot = view;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) mRoot.findViewById(id);
    }

    //MoveFragments method, simplifies code in applications with multiple fragments.
    protected void moveToFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        if (getActivity() == null) return;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, null);
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
