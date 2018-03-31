package awirut.githubmarketplace.githubmarket;

import android.util.Log;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx2.Rx2Apollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import awirut.githubmarketplace.MarketCategoryQuery;
import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.MarketPlaceListingsByCategoryQuery;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GitHubMarketPlaceImpl implements GitHubMarketPlace {

    ApolloClient apolloClient;
    private List<MarketPlaceCategory> marketPlaceCategories;
    public GitHubMarketPlaceImpl(ApolloClient apolloClient){
        this.apolloClient = apolloClient;
    }

    @Override
    public List<MarketPlaceCategory> getMarketPlaceCategories() {
        return marketPlaceCategories;
    }

    @Override
    public ArrayList<String> getMarketPlaceCategoryDisplayTexts() {
        ArrayList<String> displayTexts = new ArrayList<>();
        for(MarketPlaceCategory marketPlaceCategory : marketPlaceCategories)
            displayTexts.add(marketPlaceCategory.getName());

        return displayTexts;
    }

    @Override
    public MarketPlaceCategory getMarketCategory(int index) {
        return marketPlaceCategories.get(index);
    }

    @Override
    public void setMarketPlaceCategories(List<MarketCategoryQuery.MarketplaceCategory> categories) {
        marketPlaceCategories = new ArrayList<>();
        for(MarketCategoryQuery.MarketplaceCategory category : categories)
        {
            MarketPlaceCategory marketPlaceCategory = new MarketPlaceCategory(category.name(), category.slug());
            marketPlaceCategories.add(marketPlaceCategory);
        }
    }

    @Override
    public Observable<Response<MarketCategoryQuery.Data>> queryMarketCategory() {

        MarketCategoryQuery query = MarketCategoryQuery.builder()
                .exclude_empty(true)
                .build();

        return Rx2Apollo.from(apolloClient.query(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<MarketPlaceListingsAllQuery.Data>> queryMarketListingAll(int count) {

        MarketPlaceListingsAllQuery query = MarketPlaceListingsAllQuery.builder()
                .number_of_repos(count)
                .build();

        return Rx2Apollo.from(apolloClient.query(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<MarketPlaceListingsByCategoryQuery.Data>> queryMarketListingByCategory(String category, int count) {

        MarketPlaceListingsByCategoryQuery query = MarketPlaceListingsByCategoryQuery.builder()
                .category(category)
                .number_of_repos(count)
                .build();

        return Rx2Apollo.from(apolloClient.query(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
