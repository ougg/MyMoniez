package oug.com.mymoniez;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ItemListFragment extends ListFragment {

    public ItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<MoneyEvent> list= new ArrayList();
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        list.add(new MoneyEvent(1,11,System.currentTimeMillis(), MoneyEvent.Category.ALCOHOL,"xD"));
        EventAdapter adapter = new EventAdapter(getActivity(),list);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        MoneyEvent event = (MoneyEvent) getListAdapter().getItem(position);
        Bundle arguments = new Bundle();
        arguments.putInt(MainActivity.EVENT_ID,event.getEventId());
        arguments.putSerializable(MainActivity.CATEGORY,event.getCategory());
        arguments.putString(MainActivity.VALUE,event.getValueString());
        arguments.putString(MainActivity.DATE,event.getDateString());
        arguments.putString(MainActivity.DESCRIPTION,event.getDescription());

        DetailsFragment details = new DetailsFragment();
        details.setArguments(arguments);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        details.show(fm,"Dialog");
    }
}
