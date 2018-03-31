package awirut.githubmarketplace.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

public class DaggerScope {

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ApplicationScope {
    }
}
