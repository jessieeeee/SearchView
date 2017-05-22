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
 * @description show result fragment
 */


public class ResultFragment extends Fragment {
    public int page = 1;//当前页数
    private RecyclerView rv_main;
    private ResultListAdapter resultListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView=inflater.inflate(R.layout.fragment_result, container, false);
        rv_main= (RecyclerView) contentView.findViewById(R.id.rv_main);
        initRecycleView();
        setResult(((MainActivity)getActivity()).buildData());
        return contentView;
    }

    //设置行程列表
    private void initRecycleView() {
        rv_main.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_main.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.bg)));
        resultListAdapter = new ResultListAdapter();
        rv_main.setAdapter(resultListAdapter);
    }

    //刷新列表
    public void setResult(List<NewsDTO> newsDTOs){
        resultListAdapter.setData(newsDTOs);
    }
}
