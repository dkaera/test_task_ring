Master branch includes release version of test app.

1. Architecture of app based on VIPER architecture pattern, in Android community it famous as Clean Architecture. 
2. MVP presentation was implemented by Counductor librrary. Router is a main controller for manipulate stack of screens, handle back stack and launch animations.
3. Controller child classes are base implametation of View layer.
4. Interactor layer is released by Janet Command framework. Each action pipe has separete module which implemented in command child class.
5. For dependency injection I used Dagger 2.
6. For memory leaks handling - LeakCanary.
7. For picture loading - Picasso.
8. For API requests used Janet Http. Framework is very simmilar to Retrofit.

Future improvements
1. Implemet Unit tests.
2. Migrate to RxJava2.
3. Implement Room Persistence cache
