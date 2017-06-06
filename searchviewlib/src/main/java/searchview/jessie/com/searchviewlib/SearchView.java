package searchview.jessie.com.searchviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date :2017/5/22 0022
 * @author :JessieK
 * @email :lyj1246505807@gmail.com
 * @description : custom searchview
 */ 


public class SearchView extends LinearLayout implements  View.OnKeyListener {

    public ImageView ib;
    public ImageView iback;
    public AutoCompleteTextView et;
    public Button search;
    public searchEvent searchEvent;
    public LinearLayout layContentSearch;
    public ImageView ivTag;
    private long lastTime;//上一次时间记录
    public int intervalTime=1000;//搜索间隔时间

    //设置间隔时间
    public SearchView setIntervalTime(int intervalTime){
        this.intervalTime=intervalTime;
        return this;
    }

    //设置背景颜色
    public SearchView setBackGroundColor(int color){
        layContentSearch.setBackgroundColor(color);
        return this;
    }

    //设置返回按钮
    public SearchView setBackIcon(int resId){
        iback.setImageResource(resId);
        return this;
    }

    //设置搜索图标
    public SearchView setTagIcon(int resId){
        ivTag.setImageResource(resId);
        return this;
    }

    //设置删除图标
    public SearchView setDelIcon(int resId){
        ib.setImageResource(resId);
        return this;
    }

    //设置搜索文本
    public SearchView setSearchText(String text){
        search.setText(text);
        return this;
    }

    //设置搜索文本颜色
    public SearchView setSearchTextColor(int color){
        search.setTextColor(color);
        return this;
    }

    //设置搜索文本背景图片
    public SearchView setSearchBackground(int resId){
        search.setBackgroundResource(resId);
        return this;
    }

    //设置搜索文本背景颜色
    public SearchView setSearchBackgroundColor(int color){
        search.setBackgroundColor(color);
        return this;
    }

    //设置搜索框提示文本
    public SearchView setSearchHintText(String hintText){
        search.setHint(hintText);
        return this;
    }

    //设置搜索框提示文本颜色
    public SearchView setSearchHintTextColor(int color){
        search.setHintTextColor(color);
        return this;
    }

    //实现搜索和返回接口
    public SearchView setSearchEvent(SearchView.searchEvent searchEvent) {
        this.searchEvent = searchEvent;
        return this;
    }

    public interface OnTextChangedListener {
        void onTextChanged(Editable s, int length);
    }


    public interface searchEvent{
        void onSearch();
        void back();
    }



    public SearchView(Context context) {
        super(context);

    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_search_et_btn, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SearchView);
        initView();
        intervalTime=a.getInt(R.styleable.SearchView_intervalTime,1000);
        setBackGroundColor(a.getColor(R.styleable.SearchView_backgroundColor,getResources().getColor(R.color.default_blue)));
        setBackIcon(a.getResourceId(R.styleable.SearchView_backIcon,R.drawable.ic_title_back_white));
        setTagIcon(a.getResourceId(R.styleable.SearchView_tagIcon,R.drawable.ic_content_search));
        setDelIcon(a.getResourceId(R.styleable.SearchView_delIcon,R.drawable.sl_del_content));
        setSearchText(a.getString(R.styleable.SearchView_searchText));
        setSearchTextColor(a.getColor(R.styleable.SearchView_searchTextColor,getResources().getColor(R.color.white)));
        setSearchBackground(a.getResourceId(R.styleable.SearchView_searchBackground,android.R.color.transparent));
        setSearchBackgroundColor(a.getColor(R.styleable.SearchView_searchBackgroundColor,getResources().getColor(android.R.color.transparent)));
        setSearchHintText(a.getString(R.styleable.SearchView_searchHintText));
        setSearchHintTextColor(a.getColor(R.styleable.SearchView_searchHintTextColor,getResources().getColor(R.color.default_line)));

    }


    public EditText getEditView(){
        return  et;
    }

    //设置是否显示返回按钮
    public void setBackGone() {
        iback.setVisibility(GONE);
    }


    public void setKeyListAdapter(ArrayList<String> keyList){
        AutoComplateSearchAdapter autoComplateAdapter = new AutoComplateSearchAdapter(getContext(),keyList);
        et.setAdapter(autoComplateAdapter);
    }

    private void initView() {
        layContentSearch= (LinearLayout) findViewById(R.id.lay_content_search);
        ivTag= (ImageView) findViewById(R.id.iv_tag);
        ib = (ImageView) findViewById(R.id.iv_del_content);
        iback = (ImageView) findViewById(R.id.iv_back);
        iback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvent.back();
            }
        });
        et = (AutoCompleteTextView) findViewById(R.id.et_content_search);
        et.setFilters(new InputFilter[]{emojiFilter});
        setOnKeyListener(this);
        et.addTextChangedListener(tw);// 为输入框绑定一个监听文字变化的监听器

        et.setDropDownHeight(600);
        et.setDropDownWidth(600);
        et.setThreshold(3);

        // 添加按钮点击事件
        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBtn();// 隐藏按钮
                et.setText("");// 设置输入框内容为空
            }
        });
        search= (Button) findViewById(R.id.btn_content_search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                searchEvent.onSearch();
                InputMethodUtils.hideKeyBoard(et);
            }
        });
    }

    @Override
    public void setOnKeyListener(OnKeyListener l) {
        et.setOnKeyListener(l);
    }

    public String getText() {
        return et.getText().toString();
    }

    // 当输入框状态改变时，会调用相应的方法
    TextWatcher tw = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在文字改变后调用
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                hideBtn();// 隐藏按钮
            } else {
                showBtn();// 显示按钮
            }


            //增加输入文字变化的搜索
            if(System.currentTimeMillis()-lastTime>intervalTime){
                searchEvent.onSearch();
            }

            lastTime= System.currentTimeMillis();
        }
    };

    // 设置按钮不可见
    private void hideBtn() {

        if (ib.isShown())
            ib.setVisibility(View.GONE);
    }

    // 设置按钮可见
    private void showBtn() {
        if (!ib.isShown())
            ib.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event != null
                && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_ENVELOPE)
                && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_UP) {
            searchEvent.onSearch();
            InputMethodUtils.hideKeyBoard(et);
            return true;
        }
        return false;
    }

    //过滤emoji表情
    private static InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return "";
            }
            return null;
        }
    };
}

