package searchview.jessie.com.searchview;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import searchview.jessie.com.searchviewlib.SearchView;

import static searchview.jessie.com.searchview.R.id.sc_content;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
     private ResultFragment resultFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }


    private void findView(){
        searchView= (SearchView) this.findViewById(sc_content);
        searchView.setSearchEvent(new SearchView.searchEvent() {
            @Override
            public void onSearch() {
                doSearch(true);
            }

            @Override
            public void back() {
                finish();
            }
        });
    }

    public void doSearch(boolean isClear) {
        String searchContent = searchView.getText().toString().trim();
        resultFragment.setResult();
    }

    public void initFragment() {
        resultFragment = new ResultFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);

        transaction.replace(R.id.ll_content, resultFragment);
        transaction.commit();
    }

}
