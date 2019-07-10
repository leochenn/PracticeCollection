package com.leo.demo.code_collection;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by Leo on 2019/7/8.
 */
public class FragmentUtil {

    private void showFragmentDialog(Activity activity) {
        FragmentTransaction ft = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ft = activity.getFragmentManager().beginTransaction();
            Fragment fragment = activity.getFragmentManager().findFragmentByTag("tag");
            if (fragment != null) {
                ft.remove(fragment).commit();
            } else {
                Class cls = null;
                try {
                    fragment = (Fragment) cls.asSubclass(Fragment.class).newInstance();
                } catch (Exception e) {
                }
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.show(ft, "tag");
            }
        }
    }

    private void showFragment(Activity activity) {
        try {
            FragmentTransaction ft = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                ft = activity.getFragmentManager().beginTransaction();
                Fragment fragment = activity.getFragmentManager().findFragmentByTag("tag");
                if (fragment != null) {
                    ft.remove(fragment).commit();
                } else {
                    fragment = new Fragment();
                    ft.add(android.R.id.content, fragment, "tag").commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
