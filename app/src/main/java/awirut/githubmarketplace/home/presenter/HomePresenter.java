package awirut.githubmarketplace.home.presenter;

import com.apollographql.apollo.api.Response;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import awirut.githubmarketplace.MarketCategoryQuery;
import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.MarketPlaceListingsByCategoryQuery;
import awirut.githubmarketplace.githubmarket.GitHubMarketPlace;
import awirut.githubmarketplace.githubmarket.MarketPlaceCategory;
import awirut.githubmarketplace.home.view.HomeView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class HomePresenter extends MvpBasePresenter<HomeView> {

    @Inject
    GitHubMarketPlace gitHubMarketPlace;

    private MarketPlaceCategory currentSelectedMarketPlaceCategory;
    private boolean hasSelectedCategoryFilter;

    @Inject
    public HomePresenter(){}

    public boolean hasSelectedCategoryFilter()
    {
        return hasSelectedCategoryFilter;
    }

    public void queryMarketLists()
    {
        gitHubMarketPlace.queryMarketCategory().subscribe(new Observer<Response<MarketCategoryQuery.Data>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<MarketCategoryQuery.Data> dataResponse) {
                gitHubMarketPlace.setMarketPlaceCategories(dataResponse.data().marketplaceCategories());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void queryMarketListings()
    {
        hasSelectedCategoryFilter = false;

        ifViewAttached(view -> {
            view.showLoading();
            view.hideFailedLoading();
            view.hideMarketListingAll();
            view.hideMarketListingByCategory();
            view.hideResetFilter();
        });

        gitHubMarketPlace.queryMarketListingAll(15).subscribe(new Observer<Response<MarketPlaceListingsAllQuery.Data>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<MarketPlaceListingsAllQuery.Data> dataResponse) {
                ifViewAttached(view -> {
                    view.hideLoading();

                    MarketPlaceListingsAllQuery.MarketplaceListings listings = dataResponse.data().marketplaceListings();
                    view.showMarketListingAll(listings.nodes());
                    view.resetMarketListingCategoryName();
                });
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
                ifViewAttached(view -> {
                    view.hideLoading();
                    view.showFailedLoading();
                });
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void queryMarketListingsByCategory(String category)
    {
        ifViewAttached(view -> {
            view.showLoading();
            view.hideFailedLoading();
            view.hideMarketListingAll();
            view.hideMarketListingByCategory();
            view.hideResetFilter();
        });

        gitHubMarketPlace.queryMarketListingByCategory(category, 10).subscribe(new Observer<Response<MarketPlaceListingsByCategoryQuery.Data>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<MarketPlaceListingsByCategoryQuery.Data> dataResponse) {
                dataResponse.data().marketplaceListings().nodes();

                ifViewAttached(view -> {
                    view.hideLoading();

                    MarketPlaceListingsByCategoryQuery.MarketplaceListings listings = dataResponse.data().marketplaceListings();
                    view.showMarketListingByCategory(listings.nodes());
                    view.updateMarketListingCategoryName(currentSelectedMarketPlaceCategory.getName());
                    view.showResetFilter();
                });
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onClickCategory()
    {
        ifViewAttached(view -> view.showCategoryDialog(gitHubMarketPlace.getMarketPlaceCategoryDisplayTexts()));
    }

    public void onSelectCategory(int index)
    {
        hasSelectedCategoryFilter = true;
        currentSelectedMarketPlaceCategory = gitHubMarketPlace.getMarketCategory(index);
        queryMarketListingsByCategory(currentSelectedMarketPlaceCategory.getSlug());
    }

    public void onClickResetFilter()
    {
        queryMarketListings();
    }

    public void onClickMarketListingItem(String url)
    {
        ifViewAttached(view -> view.openMarketListingItemWebPage(url));
    }
}
