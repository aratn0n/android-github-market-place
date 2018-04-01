package awirut.githubmarketplace.home.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import awirut.githubmarketplace.R;
import awirut.githubmarketplace.util.FragmentUtil;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements MarketCategoryDialog.MarketCategoryDialogListener {

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createHomeFragment();
    }

    private void createHomeFragment()
    {
        homeFragment = (HomeFragment) FragmentUtil.getFragment(getSupportFragmentManager(), HomeFragment.TAG);

        if(homeFragment == null)
        {
            Timber.d("create home fragment");
            homeFragment = HomeFragment.create();
            FragmentUtil.addFragment(getSupportFragmentManager(),
                    homeFragment,
                    R.id.home_fragment_layout,
                    HomeFragment.TAG,
                    false);
        }
    }

    @Override
    public void onBackPressed() {
        if(homeFragment != null && homeFragment.hasSelectedCategoryFilter())
            homeFragment.resetCategoryFilter();
        else
            super.onBackPressed();
    }

    @Override
    public void onClickCategory(int index) {
        if(homeFragment != null)
            homeFragment.selectCategory(index);
    }
}
