package searchview.jessie.com.searchview;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import searchview.jessie.com.searchviewlib.SearchActivity;

//must extends SearchActivity,realize abstract method 必须继承SearchActivity,实现其中的抽象方法
public class MainActivity extends SearchActivity {

    private List<NewsDTO> newsDTOs;
    private ResultListAdapter resultListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //模拟数据
        buildData();
        //初始化结果分页
        initResultFragment(R.id.ll_content);
        //绑定searchview
        findSearchView(R.id.sc_content);
        //设置搜索关键字自动补全
        setKeyListAdapter(setKeyList());
    }

    //设置搜索关键字自动补全
    public ArrayList<String> setKeyList(){
        ArrayList<String> keys=new ArrayList<>();
        Iterator<NewsDTO> it=newsDTOs.iterator();
        while(it.hasNext()){
            NewsDTO newsDTO=it.next();
            keys.add(newsDTO.getTitle());
        }
        return keys;
    }

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

    //模拟搜索
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
     * build data 模拟数据
     */
    public List<NewsDTO> buildData() {
        if(newsDTOs==null){
            newsDTOs =new ArrayList<>();
        }
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

}
