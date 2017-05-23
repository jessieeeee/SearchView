# SearchView 
a libiary for android that makes developers fast create custom searchview 
- ![image](https://github.com/jessieeeee/SearchView/blob/87c9495da179a955e0172944b13e01c3df5d393a/example.gif)
## Using library in your application
### Step 1. Add the JitPack repository to your build file
<pre>
 	  repositories {
 			...
 			maven { url 'https://jitpack.io' }
 		}
 	 }
</pre>
### Step 2. Add the dependency
<pre>
	dependencies {
	        compile 'com.github.jessieeeee:SearchView:v1.0'
	}
</pre>
### How to Use
1. xml layout reference

`<searchview.jessie.com.searchviewlib.SearchView
      android:id="@+id/sc_content"
      android:layout_width="match_parent"
     android:layout_height="wrap_content"/>`
         
2. Java code set callback

<pre>

searchView = (SearchView) this.findViewById(sc_content);
        searchView.setDelBtn(R.drawable.sl_del_content);
        searchView.setSearchEvent(new SearchView.searchEvent() {
            @Override
            public void onSearch() {
                doSearch();
            }
            @Override
             public void back() {
                 finish();
             }
         }); 
         
  </pre>


