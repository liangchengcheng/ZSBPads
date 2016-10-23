package com.lcc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.lcc.msdq.R;
import java.util.List;

@SuppressLint("InflateParams")
public class ChoiceAdapter extends BaseAdapter {
	private int  selectItem=-1;
	private Context context;
	private LayoutInflater layoutInflater;
	private List<String> list;

	public ChoiceAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.choice_item, null);
			viewHolder = new ViewHolder();
			viewHolder.btn_name = (TextView) convertView.findViewById(R.id.btn_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position == selectItem) {
			convertView.setBackgroundColor(Color.rgb(26, 145, 133));
		}
		else {
			convertView.setBackgroundColor(getColorPrimary());
		}
		viewHolder.btn_name.setText(list.get(position));
		return convertView;
	}

	public static class ViewHolder {
		public TextView btn_name;
	}

	public void setSelectItem(int p) {
		selectItem=p;
	}

	public int getColorPrimary() {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
		return typedValue.data;
	}
}
