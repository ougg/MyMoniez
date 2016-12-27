package oug.com.mymoniez;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Wojtek on 27.12.2016.
 */
public class ListFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private ArrayList<String> titlesList = new ArrayList<>();
    public ListFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    public void addItem(Fragment fragment,String title){
        fragmentsList.add(fragment);
        titlesList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }
}
