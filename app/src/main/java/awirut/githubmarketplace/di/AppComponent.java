package awirut.githubmarketplace.di;

import awirut.githubmarketplace.home.presenter.HomePresenter;
import awirut.githubmarketplace.home.view.HomeFragment;
import dagger.Component;

@Component(modules = { AppModule.class })
@DaggerScope.ApplicationScope
public interface AppComponent {
    HomePresenter homePresenter();
    void inject(HomeFragment homeFragment);
}
