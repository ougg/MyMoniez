package oug.com.mymoniez;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String EVENT_ID = "oug.com.mymoniez.eventID";
    public static final String VALUE = "oug.com.mymoniez.value";
    public static final String DATE = "oug.com.mymoniez.date";
    public static final String CATEGORY = "oug.com.mymoniez.category";
    public static final String DESCRIPTION = "oug.com.mymoniez.description";
    Button filterButton;
    ItemListFragment weekList,monthList,allList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewItemFragment addNew = new AddNewItemFragment();
                FragmentManager fm = getSupportFragmentManager();
                addNew.show(fm,"AddDialog");
            }
        });

        filterButton  = (Button) findViewById(R.id.filterButton);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        ListFragmentPagerAdapter adapter = new ListFragmentPagerAdapter(getSupportFragmentManager());
        weekList = new ItemListFragment();
        weekList.setMaxTimeMillis(0);
        monthList = new ItemListFragment();
        monthList.setMaxTimeMillis(0);
        allList = new ItemListFragment();
        allList.setMaxTimeMillis(0);
        adapter.addItem(weekList,getString(R.string.week));
        adapter.addItem(monthList,getString(R.string.month));
        adapter.addItem(allList,getString(R.string.all));
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        refreshLists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void filterButtonClicked(View v){

        //if button text is "show category" display category list
        if(((Button)v).getText().equals(getString(R.string.show_category))){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final String[] categories = new String[MoneyEvent.Category.values().length];
            for(int i=0;i<categories.length;i++){
                categories[i]=MoneyEvent.getCategoryName(MoneyEvent.Category.values()[i]);
            }

            builder.setItems(categories, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MoneyEvent.Category filterCategory = MoneyEvent.Category.values()[i];
                    weekList.setFilterCategory(filterCategory);
                    monthList.setFilterCategory(filterCategory);
                    allList.setFilterCategory(filterCategory);
                    filterButton.setText(getString(R.string.show_all));
                    refreshLists();
                }
            });
            builder.create().show();
        }else{
            weekList.setFilterCategory(null);
            monthList.setFilterCategory(null);
            allList.setFilterCategory(null);
            refreshLists();
        }
    }

    public void refreshLists(){
        weekList.refreshList();
        monthList.refreshList();
        allList.refreshList();
    }
}
