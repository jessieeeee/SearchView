package searchview.jessie.com.searchviewlib;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;
/**
 * @date :2017/6/5 0005
 * @author :JessieK
 * @email :lyj1246505807@gmail.com
 * @description :softkeyboard util
 */ 

public class InputMethodUtils {
    //强制显示键盘
    public static void showKeyBoard(final EditText txtSearchKey)
    {

        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run()
            {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(INPUT_METHOD_SERVICE);

                m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
            }
        }, 300);
    }

    //强制隐藏键盘
    public static void hideKeyBoard(final EditText txtSearchKey){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run()
            {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(INPUT_METHOD_SERVICE);

                m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
            }
        }, 300);
    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideKeyBoard(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;

        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
    }
}
