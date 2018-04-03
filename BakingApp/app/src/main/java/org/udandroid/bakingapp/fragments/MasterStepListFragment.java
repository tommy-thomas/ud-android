package org.udandroid.bakingapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by tommy-thomas on 4/3/18.
 */

public class MasterStepListFragment extends Fragment {

    OnStepClickListener mCallBack;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int selectedIndex);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallBack = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public MasterStepListFragment(){}
}
