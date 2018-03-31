package awirut.githubmarketplace.githubmarket;

import com.apollographql.apollo.api.Response;

import java.util.ArrayList;
import java.util.List;

import awirut.githubmarketplace.MarketCategoryQuery;
import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.MarketPlaceListingsByCategoryQuery;
import io.reactivex.Observable;

public interface GitHubMarketPlace {

    List<MarketPlaceCategory> getMarketPlaceCategories();
    ArrayList<String> getMarketPlaceCategoryDisplayTexts();
    MarketPlaceCategory getMarketCategory(int index);

    void setMarketPlaceCategories(List<MarketCategoryQuery.MarketplaceCategory> marketPlaceCategories);

    Observable<Response<MarketCategoryQuery.Data>> queryMarketCategory();
    Observable<Response<MarketPlaceListingsAllQuery.Data>> queryMarketListingAll(int count);
    Observable<Response<MarketPlaceListingsByCategoryQuery.Data>> queryMarketListingByCategory(String category, int count);
}
