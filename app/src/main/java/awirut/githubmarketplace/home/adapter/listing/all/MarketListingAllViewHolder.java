package awirut.githubmarketplace.home.adapter.listing.all;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import awirut.githubmarketplace.GlideApp;
import awirut.githubmarketplace.MarketPlaceListingsAllQuery;
import awirut.githubmarketplace.R;
import awirut.githubmarketplace.home.view.HomeView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketListingAllViewHolder extends RecyclerView.ViewHolder {

    private HomeView homeView;

    @BindView(R.id.market_item_logo_image_view)
    ImageView logoImageView;

    @BindView(R.id.market_item_name_text_view)
    TextView nameTextView;

    @BindView(R.id.market_item_description_text_view)
    TextView descriptionTextView;

    public MarketListingAllViewHolder(View itemView, HomeView homeView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.homeView = homeView;
    }

    public void bind(MarketPlaceListingsAllQuery.Node node)
    {
        GlideApp.with(logoImageView)
                .load(node.logoUrl().toString())
                .placeholder(R.drawable.ic_photo_grey_48dp)
                .fitCenter()
                .into(logoImageView);


        nameTextView.setText(node.name());
        descriptionTextView.setText((node.normalizedShortDescription()));

        itemView.setOnClickListener(v -> homeView.clickMarketListingItem(node.url().toString()));
    }
}
