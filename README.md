# Android - GitHub Market Place Listing + MVP
This repository is an example of Android application with MVP pattern for listing applications from GitHub Market Place.

Libraries used on this project
------------------------------------
* RxJava & RxAndroid
* Dagger2
* Apollo
* OkHttp
* Mosby
* ButterKnife
* Glide
* Material Dialog

GitHub Access Token
-------------------
Provide your `GitHub access token` from `provideGitHubToken()` inside `AppModule.java`

How does it work?
-------------------
* The application uses generated codes from Apollo which generates Java code from GitHub's GraphQL.
* The numbers of results (Currently are 10) can be changed from `GitHubMarketPlaceImpl.java`
