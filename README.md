# SearchView 
a libiary for android that makes developers fast create custom searchview 

## Using library in your application
### Step 1. Add the JitPack repository to your build file
### Step 2. Add the dependency
### How to Use
1. xml layout reference

`<searchview.jessie.com.searchviewlib.SearchView
         android:id="@+id/sc_content"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"/>`
2. Java code set callback

` searchView = (SearchView) this.findViewById(sc_content);
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
         }); `

