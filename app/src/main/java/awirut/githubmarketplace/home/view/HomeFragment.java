package awirut.githubmarketplace.home.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import awirut.githubmarketplace.App;
import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.MarketPlaceListingsByCategoryQuery;
import awirut.githubmarketplace.R;
import awirut.githubmarketplace.home.adapter.listing.all.MarketListingAllAdapter;
import awirut.githubmarketplace.home.adapter.listing.category.MarketListingByCategoryAdapter;
import awirut.githubmarketplace.home.presenter.HomePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends MvpFragment<HomeView, HomePresenter> implements HomeView {

    public static String TAG = "HOME_FRAGMENT";

    @BindView(R.id.home_tool_bar)
    Toolbar homeToolbar;

    @BindView(R.id.market_listing_all_recycler_view)
    RecyclerView marketListingAllRecyclerView;
    private MarketListingAllAdapter marketListingAllAdapter;

    @BindView(R.id.market_listing_by_category_recycler_view)
    RecyclerView marketListingByCategoryRecyclerView;
    private MarketListingByCategoryAdapter marketListingByCategoryAdapter;

    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

    @BindView(R.id.failed_loading_layout)
    RelativeLayout failedLoadingLayout;

    @BindView(R.id.filter_image_view)
    ImageView filterImageView;

    @Inject
    Context context;

    public static HomeFragment create()
    {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        marketListingAllAdapter = new MarketListingAllAdapter(this);
        marketListingByCategoryAdapter = new MarketListingByCategoryAdapter(this);

        App.getInstance().getAppComponent().inject(this);
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return App.getInstance().getAppComponent().homePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        marketListingAllRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        marketListingAllRecyclerView.setAdapter(marketListingAllAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(marketListingAllRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        marketListingAllRecyclerView.addItemDecoration(dividerItemDecoration);

        marketListingByCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        marketListingByCategoryRecyclerView.setAdapter(marketListingByCategoryAdapter);

        dividerItemDecoration = new DividerItemDecoration(marketListingByCategoryRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        marketListingByCategoryRecyclerView.addItemDecoration(dividerItemDecoration);

        filterImageView.setOnClickListener(v -> presenter.onClickCategory());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.queryMarketLists();
        presenter.queryMarketListings();
    }

    @Override
    public void hideMarketListingAll() {
        marketListingAllRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showMarketListingAll(List<MarketPlaceListingsAllQuery.Node> nodes) {
        marketListingAllAdapter.setNodes(nodes);
        marketListingAllRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFailedLoading() {
        failedLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showFailedLoading() {
        failedLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCategoryDialog(ArrayList<String> categoryList) {
        MarketCategoryDialog marketCategoryDialog = MarketCategoryDialog.create(categoryList);
        marketCategoryDialog.show(getFragmentManager(), MarketCategoryDialog.TAG);
    }

    @Override
    public void selectCategory(int index) {
        presenter.onSelectCategory(index);
    }

    @Override
    public void hideMarketListingByCategory() {
        marketListingByCategoryRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showMarketListingByCategory(List<MarketPlaceListingsByCategoryQuery.Node> nodes) {
        marketListingByCategoryAdapter.setNodes(nodes);
        marketListingByCategoryRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResetFilter() {
        homeToolbar.setNavigationIcon(null);
        homeToolbar.setNavigationOnClickListener(null);
    }

    @Override
    public void showResetFilter() {
        homeToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        homeToolbar.setNavigationOnClickListener(v -> presenter.onClickResetFilter());
    }

    @Override
    public void resetMarketListingCategoryName() {
        homeToolbar.setTitle(getString(R.string.home_screen_market_place));
    }

    @Override
    public void updateMarketListingCategoryName(String categoryName) {
        homeToolbar.setTitle(categoryName);
    }

    @Override
    public boolean hasSelectedCategoryFilter() {
        return presenter.hasSelectedCategoryFilter();
    }

    @Override
    public void resetCategoryFilter() {
        presenter.onClickResetFilter();
    }

    @Override
    public void clickMarketListingItem(String url) {
        presenter.onClickMarketListingItem(url);
    }

    @Override
    public void openMarketListingItemWebPage(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }
}
