package lol.chendong.funnovel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lol.chendong.funnovel.ui.BookcaseFragment;
import lol.chendong.funnovel.ui.LibraryFragment;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/7/29 0029 - 3:23
 * 注释：
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BookcaseFragment.newInstance();
            case 1:
                return LibraryFragment.newInstance();
            default:
                return new Fragment();
        }

    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "书架";
            case 1:
                return "书库";
        }
        return null;
    }
}
