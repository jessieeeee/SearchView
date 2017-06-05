# SearchView 
a libiary for android that makes developers fast create custom searchview 
- ![image](https://github.com/jessieeeee/SearchView/blob/master/example.gif)
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

    <searchview.jessie.com.searchviewlib.SearchView
    
        android:id="@+id/sc_content"
        
        android:layout_width="match_parent"
        
        android:layout_height="wrap_content"
        
        jessie:intervalTime="2000"
        
        jessie:backIcon="@drawable/ic_title_back_white"
        
        jessie:backgroundColor="@color/default_blue"
        
        jessie:tagIcon="@drawable/ic_content_search"
        
        jessie:delIcon="@drawable/sl_del_content"
        
        jessie:searchText="@string/text_search"
        
        jessie:searchTextColor="@color/white"
        
        jessie:searchHintText="@string/hint_input_search"/>
         
         
2. Java code set callback
 - you must extends SearchActivity , realize abstract method

     //搜索逻辑
     public void doSearch() {
         resultListAdapter.setData(search(getSearchContent()));
     }
     
     @Override
     protected RecyclerView.Adapter setResultListAdapter() {
         //bind data 绑定数据和适配器
         resultListAdapter=new ResultListAdapter();
         resultListAdapter.setData(newsDTOs);
         return resultListAdapter;
     }

 - next, init result fragment , bind searchview , set search content Completion and custom ui
  
     initResultFragment(R.id.ll_content);
     findSearchView(R.id.sc_content);
     setKeyListAdapter(setKeyList());
     setSearchView();
         
 - you can custom searchview ui
        public void setSearchView(){
            searchView.setIntervalTime(1000)
                      .setBackGroundColor(getResources().getColor(R.color.default_blue))
                      .setBackIcon(R.drawable.ic_title_back_white)
                      .setTagIcon(R.drawable.ic_content_search)
                      .setDelIcon(R.drawable.sl_del_content)
                      .setSearchText(getString(R.string.text_search))
                      .setSearchTextColor(getResources().getColor(R.color.white))
                      .setSearchBackground(getResources().getColor(android.R.color.transparent))
                      .setSearchBackgroundColor(getResources().getColor(android.R.color.transparent))
                      .setSearchHintText(getString(R.string.hint_input_search))
                      .setSearchHintTextColor(getResources().getColor(R.color.default_line));
        }
        

         


