Overview:
	The app begins with the MainActivity and displays the first four options in the Navigation Drawer using four appropriately 
	named fragments (eg: legis_fragment) These fragments consist of a listview populated with the help of custom adapters for 
	legislators, bills and Committees (eg: LegisAdapter). Layout for individual rows are used (eg: legis_row). Classes for 
	legislators, bills and committees are made to store the required data for the Listview (eg: LegiRowObject). OnIntemClickListeners
	are set on the list elements to call the required activity (eg:LegisDetails) which serves to display further details and allow 
	adding of elements to the list of Favorites. FavTracker Class was created to access data across activities. Comparators were created 
	to sort the Listviews as required. AboutMe Activity was created to display personal details with a photo.

Notification Drawer:
	Notification Drawer Activity in Android Studio was used

Image Caching:
	Picasso was used

HTTP Requests:
	Volley was used

Dependencies:
	dependencies {
 		compile fileTree(dir: 'libs', include: ['*.jar'])
   		androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        		exclude group: 'com.android.support', module: 'support-annotations'
    		})
    		compile 'com.android.volley:volley:1.0.0'
    		compile 'com.squareup.picasso:picasso:2.5.2'
    		compile 'com.android.support:appcompat-v7:24.2.1'
    		compile 'com.android.support:design:24.2.1'
    		testCompile 'junit:junit:4.12'
	}

Sources which were used: 

List Item OnClick Listener:
	https://android--code.blogspot.com/2015/08/android-listview-item-click.html

Custom Adapter:
	https://www.youtube.com/watch?v=_sStCBdJkQg&index=47&list=PL6gx4Cwl9DGBsvRxJJOzG4r4k_zLKrnxl

Global Variable:
	http://stackoverflow.com/questions/21810240/how-to-create-a-global-variable-in-android

TabHost:
	http://www.viralandroid.com/2015/09/simple-android-tabhost-and-tabwidget-example.html?m=1

Using Volley:
	http://www.androidhive.info/2014/07/android-custom-listview-with-image-and-text-using-volley/

Setting an OnItemClickListener:
	https://android--code.blogspot.com/2015/08/android-listview-item-click.html