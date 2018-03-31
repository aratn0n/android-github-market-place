package awirut.githubmarketplace.home.adapter.listing.all;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.R;
import awirut.githubmarketplace.home.view.HomeView;

public class MarketListingAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private HomeView homeView;
    private List<MarketPlaceListingsAllQuery.Node> nodes = Collections.emptyList();

    public MarketListingAllAdapter(HomeView homeView)
    {
        this.homeView = homeView;
    }

    public void setNodes(List<MarketPlaceListingsAllQuery.Node> nodes)
    {
        this.nodes = nodes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_listing_item, parent, false);
        return new MarketListingAllViewHolder(view, homeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MarketListingAllViewHolder marketListingAllViewHolder = (MarketListingAllViewHolder) holder;
        marketListingAllViewHolder.bind(nodes.get(position));
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }
}
