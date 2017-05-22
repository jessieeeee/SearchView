package searchview.jessie.com.searchview;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Description: 搜索组件
 * <p>
 * Author: yi.zhang
 * Time: 2016/10/20 0012 15:15
 * E-mail: yi.zhang@rato360.com
 */
public class SearchView extends LinearLayout implements  View.OnKeyListener {

    public ImageView ib;
    public ImageView iback;
    public EditText et;
    public Button search;
    public searchEvent searchEvent;
    private long lastTime;//上一次时间记录
    public void setSearchEvent(SearchView.searchEvent searchEvent) {
        this.searchEvent = searchEvent;
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
        initView();
    }


    public void setHint(String str) {
        et.setHint(str);
    }

    public void setBackGone() {
        iback.setVisibility(GONE);
    }

    private void initView() {
        ib = (ImageView) findViewById(R.id.iv_del_content);
        iback = (ImageView) findViewById(R.id.iv_back);
        iback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvent.back();
            }
        });
        et = (EditText) findViewById(R.id.et_content_search);
        et.setFilters(new InputFilter[]{emojiFilter});
        setOnKeyListener(this);
        et.addTextChangedListener(tw);// 为输入框绑定一个监听文字变化的监听器
        // 添加按钮点击事件
        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBtn();// 隐藏按钮
                et.setText("");// 设置输入框内容为空
            }
        });
        // 添加按钮焦点事件
        et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final boolean isFocus = hasFocus;
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) et.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (isFocus) {
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                        }
                    }
                }, 100);
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
            if(System.currentTimeMillis()-lastTime>1000){
                searchEvent.onSearch();
            }

            lastTime= System.currentTimeMillis();
        }
    };

    // 设置按钮不可见
    public void hideBtn() {

        if (ib.isShown())
            ib.setVisibility(View.GONE);
    }

    // 设置按钮可见
    public void showBtn() {
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

