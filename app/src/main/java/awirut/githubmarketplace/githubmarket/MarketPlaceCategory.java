package awirut.githubmarketplace.githubmarket;

public class MarketPlaceCategory {
    private String name;
    private String slug;

    public MarketPlaceCategory(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
