package searchview.jessie.com.searchviewlib;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * @author JessieK
 * @date 2017/6/2 0002
 * @email lyj1246505807@gmail.com
 * @description searchview activity
 */


public abstract class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private ResultFragment resultFragment;

    //search to do 搜索逻辑
    protected abstract void doSearch();
    //set result list adapter 设置结果列表适配器
    protected abstract RecyclerView.Adapter setResultListAdapter();

    //return search content input
    public String getSearchContent(){
        return searchView.getText().toString().trim();
    }

    //bind searchview 绑定searchview
    protected void findSearchView(int searchViewId) {
        searchView = (SearchView) this.findViewById(searchViewId);
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
    }

    //set search keylist Completion adapter 设置自动补全搜索关键字适配器
    protected void setKeyListAdapter (ArrayList<String> keyList){
        searchView.setKeyListAdapter(keyList);
    }

    //set result list divider 设置结果列表分割线,可重写
    protected RecyclerView.ItemDecoration setResultListDivider(){
        return new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.bg));
    }

    //init result fragment 初始化结果分页
    public void initResultFragment(int SearchFragmentId) {
        resultFragment = new ResultFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);

        transaction.replace(SearchFragmentId, resultFragment);
        transaction.commit();
    }

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param views 输入框
     */
    public void clearViewFocus(View v, View... views) {
        if (null != v && null != views && views.length > 0) {
            for (View view : views) {
                if (v.getId() == view.getId()) {
                    v.setEnabled(false);
                    break;
                }
            }
        }
    }

    public void getViewFocus(View v, View... views) {
        if (null != v && null != views && views.length > 0) {
            for (View view : views) {
                if (v.getId() == view.getId()) {
                    v.setEnabled(true);
                    break;
                }
            }
        }
    }


    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param views 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, View... views) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (View view : views) {
                if (tmp_et.getId() == view.getId()) {
                    return true;
                }
            }
        }
        return false;
    }


    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isTouchView(filterViewByIds(), ev))
                return super.dispatchTouchEvent(ev);
            if(isTouchView(hideSoftByEditViewIds(), ev)){
                getViewFocus(v,hideSoftByEditViewIds());
                return super.dispatchTouchEvent(ev);
            }
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);

            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                clearViewFocus(v, hideSoftByEditViewIds());
                //隐藏键盘
                InputMethodUtils.hideKeyBoard(this);
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public View[] hideSoftByEditViewIds() {
        View[] views = {searchView.getEditView()};
        return views;
    }


    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }

}
