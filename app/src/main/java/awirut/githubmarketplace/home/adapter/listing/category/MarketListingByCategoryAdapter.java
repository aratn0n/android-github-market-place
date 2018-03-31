package awirut.githubmarketplace.home.adapter.listing.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.MarketPlaceListingsByCategoryQuery;
import awirut.githubmarketplace.R;
import awirut.githubmarketplace.home.view.HomeView;
import timber.log.Timber;

public class MarketListingByCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HomeView homeView;
    private List<MarketPlaceListingsByCategoryQuery.Node> nodes = Collections.emptyList();

    public MarketListingByCategoryAdapter(HomeView homeView)
    {
        this.homeView = homeView;
    }

    public void setNodes(List<MarketPlaceListingsByCategoryQuery.Node> nodes)
    {
        this.nodes = nodes;
        Timber.d("nodes: %d", nodes.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_listing_item, parent, false);
        return new MarketListingByCategoryViewHolder(view, homeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MarketListingByCategoryViewHolder marketListingByCategoryViewHolder = (MarketListingByCategoryViewHolder) holder;
        marketListingByCategoryViewHolder.bind(nodes.get(position));
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }
}
