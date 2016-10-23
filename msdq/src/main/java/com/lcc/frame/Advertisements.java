package com.lcc.frame;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import com.lcc.AppConstants;
import com.lcc.adapter.ZoomOutPageTransformer;
import com.lcc.entity.ActivityEntity;
import com.lcc.msdq.R;

import org.json.JSONArray;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Advertisements implements OnPageChangeListener {

	private ViewPager vpAdvertise;
	private Context context;
	private LayoutInflater inflater;
	private boolean fitXY;
	private int timeDratioin;

	List<View> views;

	/**
	 * 底部小点图片
	 */
	private ImageView[] dots;

	/**
	 * 记录当前选中位置
	 */
	private int currentIndex;
	
	Timer timer;
	TimerTask task;
	int count = 0;

	private Handler runHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x01:
				int currentPage = (Integer) msg.obj;
				setCurrentDot(currentPage);
				vpAdvertise.setCurrentItem(currentPage);
				break;
			}
		};
	};
	
	public Advertisements(Context context, boolean fitXY, LayoutInflater inflater , int timeDratioin) {
		this.context = context;
		this.fitXY = fitXY;
		this.inflater = inflater;
		this.timeDratioin = timeDratioin;
	}

	public View initView(final List<ActivityEntity> list){
		View view = inflater.inflate(R.layout.advertisement_board, null);
		view.setPadding(5, 5, 5, 12);
		vpAdvertise = (ViewPager) view.findViewById(R.id.vpAdvertise);
		vpAdvertise.setOnPageChangeListener(this);
		views = new ArrayList<View>();
		//获取轮播图片的点的parent，用于动态添加要显示的点
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
		for(int i = 0 ; i < list.size() ; i ++){
			if(fitXY){
				views.add(inflater.inflate(R.layout.advertisement_item_fitxy, null));
			}else {
				views.add(inflater.inflate(R.layout.advertisement_item_fitcenter, null));
			}
			ll.addView(inflater.inflate(R.layout.advertisement_board_dot, null));
		}
		initDots(view , ll);
	
		AdvertisementAdapter adapter = new AdvertisementAdapter(context , views , list);
		vpAdvertise.setOffscreenPageLimit(3);
		vpAdvertise.setAdapter(adapter);
		vpAdvertise.setPageTransformer(true,new ZoomOutPageTransformer());
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				int currentPage = count%list.size();
				count++;
				Message msg = Message.obtain();
				msg.what = 0x01;
				msg.obj = currentPage;
				runHandler.sendMessage(msg);
			}
		};
		timer.schedule(task, 0, 3000);
		return view;
	}
	
	
	private void initDots(View view, LinearLayout ll) {

		dots = new ImageView[views.size()];
		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);
		}

		currentIndex = 0;
		// 设置为黄色，即选中状态
		dots[currentIndex].setEnabled(false);
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		count = position;
		setCurrentDot(position);
	}

	public class AdvertisementAdapter extends PagerAdapter {

		private Context context;
		private List<View> views;
		List<ActivityEntity> list;

		public AdvertisementAdapter() {
			super();
		}

		public AdvertisementAdapter(Context context, List<View> views, List<ActivityEntity> list) {
			this.context = context;
			this.views = views;
			this.list = list;
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position), 0);
			final int POSITION = position;
			View view = views.get(position);
			try {
				String head_img =list.get(position).getActivity_pic();
				ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
				Glide.with(ivAdvertise.getContext())
						.load(head_img)
						.centerCrop()
						.placeholder(R.drawable.loading1)
						.crossFade()
						.into(ivAdvertise);
				ivAdvertise.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (listener!=null){
							listener.onClick(POSITION);
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}
	}

	public onPictrueClickListener listener;

	public void setOnPictureClickListener(onPictrueClickListener listener){
		this.listener=listener;
	}

	public interface  onPictrueClickListener{
		void onClick(int position);
	}

}
