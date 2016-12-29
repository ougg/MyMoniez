package oug.com.mymoniez;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class AddNewFragment extends Fragment {


    public AddNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO use TextWatcher.onTextChanged() method to watch the value input
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }

}
