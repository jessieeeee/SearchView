package searchview.jessie.com.searchview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * @author JessieK
 * @date 2017/5/10 0010
 * @email lyj1246505807@gmail.com
 * @description
 */


public class ResultFragment extends Fragment {
    public int page = 1;//当前页数
    private List<NewsDTO> newsDTOs;
    private RecyclerView rv_main;
    private ResultListAdapter resultListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView=inflater.inflate(R.layout.fragment_result, container, false);
        rv_main= (RecyclerView) contentView.findViewById(R.id.rv_main);
        initRecycleView();
        buildData();
        setResult(newsDTOs);
        return contentView;
    }

    //设置行程列表
    private void initRecycleView() {
        rv_main.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_main.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.bg)));
        resultListAdapter = new ResultListAdapter();
        rv_main.setAdapter(resultListAdapter);
    }

    /**
     * 模拟数据
     */
    private void buildData(){
        for (int i=0;i<20;i++){
            NewsDTO newsDTO=new NewsDTO();
            newsDTO.setTitle("title content关键字内容"+i);
            newsDTO.setDetail("detail content详情内容"+i);
            newsDTOs.add(newsDTO);
        }
    }

    //刷新列表
    public void setResult(List<NewsDTO> newsDTOs){
        this.newsDTOs=newsDTOs;
        resultListAdapter.setData(newsDTOs);
    }
}
