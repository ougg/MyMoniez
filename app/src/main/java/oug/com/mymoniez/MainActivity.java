package oug.com.mymoniez;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String EVENT_ID = "oug.com.mymoniez.eventID";
    public static final String VALUE = "oug.com.mymoniez.value";
    public static final String DATE = "oug.com.mymoniez.date";
    public static final String CATEGORY = "oug.com.mymoniez.category";
    public static final String DESCRIPTION = "oug.com.mymoniez.description";

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

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ListFragmentPagerAdapter adapter = new ListFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new ItemListFragment(),getString(R.string.week));
        adapter.addItem(new ItemListFragment(),getString(R.string.month));
        adapter.addItem(new ItemListFragment(),getString(R.string.all));
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
