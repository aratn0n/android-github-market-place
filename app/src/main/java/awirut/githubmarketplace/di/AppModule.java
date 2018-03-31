package awirut.githubmarketplace.di;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.CustomTypeAdapter;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Named;

import awirut.githubmarketplace.githubmarket.GitHubMarketPlace;
import awirut.githubmarketplace.githubmarket.GitHubMarketPlaceImpl;
import awirut.githubmarketplace.type.CustomType;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class AppModule {

    private final String GIT_HUB_TOKEN_TAG = "GIT_HUB_TOKEN";
    private final String GIT_HUB_GRAPH_QL_END_POINT_TAG = "GIT_HUB_GRAPH_QL_END_POINT";

    private Context appContext;

    public AppModule(Context context)
    {
        appContext = context;
    }

    @Provides
    @DaggerScope.ApplicationScope
    @Named(GIT_HUB_TOKEN_TAG)
    public String provideGitHubToken()
    {
        return "write your token here";
    }

    @Provides
    @DaggerScope.ApplicationScope
    @Named(GIT_HUB_GRAPH_QL_END_POINT_TAG)
    public String provideGitHubGraphQLEndPoint()
    {
        return "https://api.github.com/graphql";
    }

    @Provides
    @DaggerScope.ApplicationScope
    public HttpLoggingInterceptor provideLoggingInterceptor()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @DaggerScope.ApplicationScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logging, @Named(GIT_HUB_TOKEN_TAG) String token)
    {
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization", "Bearer " + token);
                    return chain.proceed(builder.build());
                })
                .build();
    }

    @Provides
    @DaggerScope.ApplicationScope
    public ApolloClient provideApolloClient(OkHttpClient okHttpClient,
                                            CustomTypeAdapter<URI> customTypeAdapter,
                                            @Named(GIT_HUB_GRAPH_QL_END_POINT_TAG) String endPoint)
    {
        return ApolloClient.builder()
                .serverUrl(endPoint)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.URI, customTypeAdapter)
                .build();
    }

    @Provides
    @DaggerScope.ApplicationScope
    public CustomTypeAdapter<URI> provideCustomTypeAdapterOfURI()
    {
        return new CustomTypeAdapter<URI>() {
            @Override
            public URI decode(@NonNull String value) {
                try {
                    return new URI(value);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            @NonNull
            @Override
            public String encode(@NonNull URI value) {
                try {
                    return value.toURL().toString();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Provides
    @DaggerScope.ApplicationScope
    public GitHubMarketPlace provideGitHubMarketPlace(ApolloClient apolloClient)
    {
        return new GitHubMarketPlaceImpl(apolloClient);
    }
}
