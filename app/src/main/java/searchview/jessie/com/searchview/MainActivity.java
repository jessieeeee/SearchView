package searchview.jessie.com.searchview;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import searchview.jessie.com.searchviewlib.SearchView;

import static searchview.jessie.com.searchview.R.id.sc_content;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private ResultFragment resultFragment;
    private List<NewsDTO> newsDTOs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initFragment();
        newsDTOs =new ArrayList<>();
        buildData();
    }

    private void findView() {
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
    }

    public void doSearch() {
        String searchContent = searchView.getText().toString().trim();
        resultFragment.setResult(search(searchContent));
    }

    private List<NewsDTO> search(String key) {
        List<NewsDTO> result=new ArrayList<>();
        if(TextUtils.isEmpty(key)){
            return newsDTOs;
        }
        Iterator<NewsDTO> iterator=newsDTOs.iterator();
        while(iterator.hasNext()){
            NewsDTO newsDTO=iterator.next();
            if(newsDTO.getTitle().contains(key)){
                result.add(newsDTO);
            }
            else if(newsDTO.getDetail().contains(key)){
                result.add(newsDTO);
            }
        }
        return result;
    }

    /**
     * 模拟数据
     */
    public List<NewsDTO> buildData() {
        if(newsDTOs.size()==0){
            for (int i = 0; i < 100; i++) {
                NewsDTO newsDTO = new NewsDTO();
                newsDTO.setTitle("title content关键字内容" + i);
                newsDTO.setDetail("detail content详情内容" + i);
                newsDTOs.add(newsDTO);
            }
        }
        return newsDTOs;
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
