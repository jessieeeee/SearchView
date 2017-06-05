package searchview.jessie.com.searchviewlib;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @date :2017/6/5 0005
 * @author :JessieK
 * @email :lyj1246505807@gmail.com
 * @description :search content Completion
 */

public class AutoComplateSearchAdapter extends BaseAdapter implements Filterable {
    private ArrayFilter mFilter;
    private ArrayList<String> keyList;//数据源列表
    private Context context;
    private ArrayList<String> mUnfilteredData;//待过滤数据集合

    public AutoComplateSearchAdapter(Context context,ArrayList<String> keyList) {
        this.context = context;
        this.keyList = keyList;
    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    @Override
    public Object getItem(int position) {
        return keyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.adapter_auto, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.item_auto_txt);
            holder.lineTop = (ImageView) view.findViewById(R.id.item_auto_line_top);
            holder.lineBottom = (ImageView) view.findViewById(R.id.item_auto_line_bottom);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        String key = keyList.get(position);
        holder.tv_name.setText(key);
        return view;
    }

    static class ViewHolder {
        public TextView tv_name;
        public ImageView lineTop;
        public ImageView lineBottom;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private final Object mLock = new Object();

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                synchronized (mLock) {
                    mUnfilteredData = new ArrayList<String>(keyList);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = mUnfilteredData;
                results.count = mUnfilteredData.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                ArrayList<String> newValues = new ArrayList<String>(mUnfilteredData.size());
                for (int i = 0; i < mUnfilteredData.size(); i++) {
                    String str = mUnfilteredData.get(i);
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(prefixString) && str.contains(prefixString)) {
                        newValues.add(str);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            keyList = (ArrayList<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}  