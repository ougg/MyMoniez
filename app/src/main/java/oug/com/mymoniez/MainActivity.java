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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

class TimeGetter{
    public enum Dates{WEEK_START,MONTH_START}
    public static long getMillis(Dates date){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int monthDay = now.get(Calendar.DAY_OF_MONTH);
        //in Calendar class Calendar.SUNDAY = 1, MONDAY = 2 etc.
        int weekDay = now.get(Calendar.DAY_OF_WEEK)-1;

        if(date==Dates.MONTH_START){
            now.set(year,month,1,0,0,0);
            return now.getTimeInMillis();
        }else{
            now.set(year,month,monthDay-weekDay+1,0,0,0);
            return now.getTimeInMillis();
        }
    }
}
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    public static final String EVENT_ID = "oug.com.mymoniez.eventID";
    public static final String VALUE = "oug.com.mymoniez.value";
    public static final String DATE = "oug.com.mymoniez.date";
    public static final String CATEGORY = "oug.com.mymoniez.category";
    public static final String DESCRIPTION = "oug.com.mymoniez.description";
    Button filterButton;
    ItemListFragment weekList,monthList,allList;
    DBHandler handler;
    ViewPager pager;
    TextView totalValueText;
    ItemListFragment currentPage;

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
        handler = new DBHandler(this,null,null,1);

        filterButton  = (Button) findViewById(R.id.filterButton);
        totalValueText = (TextView) findViewById(R.id.totalValueText);

        pager = (ViewPager) findViewById(R.id.pager);

        setItemListFragments();
        ListFragmentPagerAdapter adapter = new ListFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addItem(weekList,getString(R.string.week));
        adapter.addItem(monthList,getString(R.string.month));
        adapter.addItem(allList,getString(R.string.all));
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(this);

    }

    public void filterButtonClicked(View v){

        //if button text is "show category" display category list
        if(((Button)v).getText().equals(getString(R.string.show_category))){
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogTheme);
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
            filterButton.setText(getString(R.string.show_category));
            weekList.setFilterCategory(null);
            monthList.setFilterCategory(null);
            allList.setFilterCategory(null);
            refreshLists();
        }
    }

    public void setItemListFragments(){
        weekList = new ItemListFragment();
        weekList.setMaxTimeMillis(TimeGetter.getMillis(TimeGetter.Dates.WEEK_START));

        monthList = new ItemListFragment();
        monthList.setMaxTimeMillis(TimeGetter.getMillis(TimeGetter.Dates.MONTH_START));

        allList = new ItemListFragment();
        allList.setMaxTimeMillis(0);
    }

    public void refreshLists(){
        weekList.refreshList();
        monthList.refreshList();
        allList.refreshList();
        refreshTotal();
    }

    public void refreshTotal(){
        double total = currentPage.getTotal();
        totalValueText.setText(MoneyEvent.getValueString(total));
    }


    public DBHandler getHandler() {
        return handler;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPage = (ItemListFragment) ((ListFragmentPagerAdapter) pager.getAdapter()).getItem(position);
        refreshTotal();
    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

}
