package awirut.githubmarketplace.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtil {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int layoutId, String tag, boolean addToBackStack)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.add (layoutId, fragment, tag);

        if(addToBackStack)
            fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commit ();
    }

    public static Fragment getFragment(FragmentManager fragmentManager, String tag)
    {
        return fragmentManager.findFragmentByTag(tag);
    }

}
