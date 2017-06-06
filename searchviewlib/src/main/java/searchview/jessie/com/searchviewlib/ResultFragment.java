package searchview.jessie.com.searchviewlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private List data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView=inflater.inflate(R.layout.fragment_result, container, false);
        rv_main= (RecyclerView) contentView.findViewById(R.id.rv_main);
        initRecycleView();
        return contentView;
    }

    //设置结果列表
    private void initRecycleView() {
        rv_main.setLayoutManager(((SearchActivity)getActivity()).setLayoutManager());
        rv_main.addItemDecoration(((SearchActivity)getActivity()).setResultListDivider());
        rv_main.setAdapter(((SearchActivity)getActivity()).setResultListAdapter());
    }


}
