package searchview.jessie.com.searchview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author JessieK
 * @date 2017/5/22 0022
 * @email lyj1246505807@gmail.com
 * @description set result adapter
 */


public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder>{
    public List<NewsDTO> newsDTOs;

    public void setData(List<NewsDTO> newsDTOs) {
        this.newsDTOs = newsDTOs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (newsDTOs != null) {
            viewHolder.text_title.setText(newsDTOs.get(position).getTitle() );
            viewHolder.text_detail.setText(newsDTOs.get(position).getDetail());
        }
    }

    @Override
    public int getItemCount() {
        if(newsDTOs!=null){
            return newsDTOs.size();
        }else{
            return 0;
        }

    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_title;
        public TextView text_detail;

        public ViewHolder(View view) {
            super(view);
            text_title = (TextView) view.findViewById(R.id.text_title);
            text_detail = (TextView) view.findViewById(R.id.text_detail);
        }
    }

}
