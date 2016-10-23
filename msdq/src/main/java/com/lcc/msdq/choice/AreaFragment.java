package com.lcc.msdq.choice;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcc.entity.AreaInfo;
import com.lcc.msdq.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AreaFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "parentCode";
    @Bind(R.id.refresh_list_view)
    ListView mRefreshListView;
    @Bind(R.id.loadingBar)
    ProgressBar mLoadingBar;

    private String mParam1;

    OkHttpClient mOkHttpClient = new OkHttpClient();

    private OnFragmentInteractionListener mListener;

    private AreaAdapter adapter;

    public AreaFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static AreaFragment newInstance(String param1) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        ButterKnife.bind(this, view);
        mRefreshListView.setOnItemClickListener(this);

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(ARG_PARAM1, mParam1);
        mLoadingBar.setVisibility(View.VISIBLE);
        final Request request = new Request.Builder()
                .url("http://123.184.16.19:8008/area/list")
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Looper.prepare();
                mLoadingBar.setVisibility(View.GONE);
                Looper.loop();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String res = response.body().string();
                if (res != null) {
                    Gson gson = new Gson();
                    JsonResult jsonResult = gson.fromJson(res, JsonResult.class);
                    if (jsonResult.isSuccess()) {
                        List list = (List) jsonResult.getResult();

                        List newList = new ArrayList();
                        Iterator iterator = list.iterator();
                        while (iterator.hasNext()) {
                            Map map = (Map) iterator.next();
                            AreaInfo areaInfo = gson.fromJson(gson.toJson(map), AreaInfo.class);
                            newList.add(areaInfo);
                        }

                        adapter = new AreaAdapter(getContext(), newList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshListView.setAdapter(adapter);
                                mLoadingBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AreaInfo areaInfo = (AreaInfo) parent.getAdapter().getItem(position);
        if (areaInfo == null) return;
        if (mListener != null) {
            mListener.onFragmentInteraction(areaInfo);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(AreaInfo areaInfo);
    }

    class AreaAdapter extends BaseAdapter {

        private List list;

        private int lastPosition;

        public AreaAdapter(Context context, List<AreaInfo> list) {
            this.list = list;
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.area_list_item,
                        parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            AreaInfo item = (AreaInfo) list.get(position);
            viewHolder.textView.setText(item.getAreaName());
            if (lastPosition < position && lastPosition != 0) {
                ObjectAnimator.ofFloat(convertView, "translationY", convertView.getHeight() * 2, 0)
                        .setDuration(500).start();
            }
            lastPosition = position;
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
