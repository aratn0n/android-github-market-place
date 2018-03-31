package awirut.githubmarketplace.home.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.MarketPlaceListingsByCategoryQuery;

public interface HomeView extends MvpView {

    boolean hasSelectedCategoryFilter();
    void resetCategoryFilter();

    void hideMarketListingAll();
    void showMarketListingAll(List<MarketPlaceListingsAllQuery.Node> nodes);

    void hideMarketListingByCategory();
    void showMarketListingByCategory(List<MarketPlaceListingsByCategoryQuery.Node> nodes);

    void hideResetFilter();
    void showResetFilter();

    void resetMarketListingCategoryName();
    void updateMarketListingCategoryName(String categoryName);

    void hideLoading();
    void showLoading();

    void hideFailedLoading();
    void showFailedLoading();

    void showCategoryDialog(ArrayList<String> categoryList);
    void selectCategory(int index);

    void clickMarketListingItem(String url);
    void openMarketListingItemWebPage(String url);
}
