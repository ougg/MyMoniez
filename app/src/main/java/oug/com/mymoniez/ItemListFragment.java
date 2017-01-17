package oug.com.mymoniez;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class ItemListFragment extends ListFragment {
    private MoneyEvent.Category filterCategory=null;
    long maxTimeMillis=0;
    ArrayList<MoneyEvent> list;
    DBHandler handler;
    boolean activityCreated;

    public ItemListFragment() {
        // Required empty public constructor
        filterCategory=null;
        maxTimeMillis =0;
        activityCreated=false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityCreated=true;
        handler = ((MainActivity)getActivity()).getHandler();
        refreshList();
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
        arguments.putString(MainActivity.VALUE,MoneyEvent.getValueString(event.getValue()));
        arguments.putString(MainActivity.DATE,MoneyEvent.getDateString(event.getDateMillis()));
        arguments.putString(MainActivity.DESCRIPTION,event.getDescription());

        DetailsFragment details = new DetailsFragment();
        details.setArguments(arguments);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        details.show(fm,"Dialog");
    }

    public double getTotal(){
        double total=0;
        for (int i=0;i<getListAdapter().getCount();i++){
            double value = ((MoneyEvent) getListAdapter().getItem(i)).getValue();
            if(((MoneyEvent) getListAdapter().getItem(i)).getCategory()== MoneyEvent.Category.INCOME){
                total+=value;
            }else
                total-=value;
        }
        return total;
    }

    public void refreshList(){
        if (activityCreated) {
            list = handler.getItemsList(maxTimeMillis, filterCategory);
            ItemAdapter adapter = new ItemAdapter(getActivity(),list);
            setListAdapter(adapter);
        }
    }

    public void setFilterCategory(MoneyEvent.Category filterCategory) {
        this.filterCategory = filterCategory;
    }
    public void setMaxTimeMillis(long maxTimeMillis) {
        this.maxTimeMillis = maxTimeMillis;
    }

}
