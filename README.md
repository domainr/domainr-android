domainr-android
===============

### Requirements

- [Android Developer Tools](http://developer.android.com/sdk/index.html) — we used Eclipse
- [ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)
- [ActionBarSherlock](http://actionbarsherlock.com/)
- [HoloEverywhere](https://github.com/Prototik/HoloEverywhere)

### Misc

- first import the domainr-android repo into your Eclipse workspace, then import the three supporting libraries
- if you see an error in the Eclipse console like, `Found 3 versions of android-support-v4.jar in the dependency list…`, delete this file from the `lib/` dir in each of the three supporting libraries, then copy and paste the file from the main project into those three dirs (this worked for us)
- in Eclipse, the `Project` -> `Clean…` menu item is your friend
- when in doubt, restart Eclipse
