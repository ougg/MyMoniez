package oug.com.mymoniez;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;


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

    //The default instantiateItem returns a Fragment returned by getItem(), but only if such Fragment
    //has never been created before - otherwise it returns a reference to existing Fragment from FragmentManager
    //which may cause problems when we try to do something on a Fragment returned by getItem() ex. after the screen rotation event
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment instantiatedFragment = (Fragment) super.instantiateItem(container, position);
        fragmentsList.set(position,instantiatedFragment);

        return instantiatedFragment;
    }
}
