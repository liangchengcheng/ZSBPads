package com.lcc.msdq.compony;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lapism.searchview.adapter.SearchAdapter;
import com.lapism.searchview.adapter.SearchItem;
import com.lapism.searchview.history.SearchHistoryTable;
import com.lapism.searchview.view.SearchCodes;
import com.lapism.searchview.view.SearchView;
import com.lcc.adapter.CompanyAdapter;
import com.lcc.entity.CompanyDescription;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.area.AreaDialogFragment;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.msdq.choice.AreaSelectActivity;
import com.lcc.msdq.choice.ChoiceAreaSelectActivity;
import com.lcc.msdq.compony.question.CompanyContentActivity;
import com.lcc.msdq.description.com.CompanyDesMain;
import com.lcc.mvp.presenter.CompanyDescriptionPresenter;
import com.lcc.mvp.presenter.impl.CompanyDescriptionPresenterImpl;
import com.lcc.mvp.view.CompanyDescriptionView;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.view.loadview.LoadingLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.recyclerview.S_RefreshAndLoadFragment;

public class CompanyIndexFragment extends S_RefreshAndLoadFragment implements SearchView.OnQueryTextListener,
        SearchView.SearchViewListener, SearchAdapter.OnItemClickListener, CompanyAdapter.OnItemClickListener,
        CompanyDescriptionView, View.OnClickListener,CompanyAdapter.OnImageClickListener ,
        AreaDialogFragment.CodeListener ,AreaDialogFragment.StringListener{
    private SearchView mSearchView;
    private SearchHistoryTable mHistoryDatabase;
    private View iv_more;
    private LoadingLayout loading_layout;
    private TextView tv_tip;

    private CompanyDescriptionPresenter mPresenter;
    private CompanyAdapter mAdapter;
    private String company_name = "";
    private String area = "";

    public static Fragment newInstance() {
        return new CompanyIndexFragment();
    }

    @Override
    protected void onFragmentCreate() {
        currentPage = 1;
        super.onFragmentCreate();
        mPresenter = new CompanyDescriptionPresenterImpl(this);
        View view = getView();
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);

        RecyclerView mRecyclerView = getRecyclerView();
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CompanyAdapter(getActivity());
        setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnImageClickListener(this);
        mAdapter.setHasMoreData(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loading_layout = (LoadingLayout) view.findViewById(R.id.loading_layout);
        loading_layout.setOnClickListener(this);
        initSearchView(view);
        view.findViewById(R.id.iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.show(true);
            }
        });

        iv_more = view.findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);
        mPresenter.getData(currentPage, company_name, area);
    }

    @Override
    protected void onFragmentLoadMore() {
        mPresenter.loadMore(getCurrentPage(), company_name, area);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.company_fragment;
    }

    @Override
    public void onRefreshData() {
        mPresenter.refresh(company_name, area);
    }

    private void initSearchView(View view) {
        mSearchView = (SearchView) view.findViewById(R.id.searchView);
        if (mSearchView == null) {
            return;
        }
        mSearchView.setVersion(SearchCodes.VERSION_MENU_ITEM);
        mSearchView.setStyle(SearchCodes.STYLE_MENU_ITEM_CLASSIC);
        mSearchView.setTheme(SearchCodes.THEME_LIGHT);
        mSearchView.setDivider(false);
        mSearchView.setHint("输入查询公司名称(输入全部查询全部)");
        mSearchView.setHintSize(getResources().getDimension(R.dimen.search_text_medium));
        mSearchView.setVoice(false);
        mSearchView.setAnimationDuration(300);
        mSearchView.setShadowColor(ContextCompat.getColor(getActivity(), R.color.search_shadow_layout));
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnSearchViewListener(this);

        mHistoryDatabase = new SearchHistoryTable(getActivity());
        mHistoryDatabase.addItem(new SearchItem("全部"));

        List<SearchItem> list = new ArrayList<>();
        SearchAdapter adapter = new SearchAdapter(getActivity(), list, mHistoryDatabase.getAllItems(),
                SearchCodes.THEME_LIGHT);
        adapter.setOnItemClickListener(this);
        mSearchView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
        String text = textView.getText().toString();
        loadDataFromServer(text);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadDataFromServer(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {

    }

    private void loadDataFromServer(String text) {
        mSearchView.hide(false);
        mHistoryDatabase.addItem(new SearchItem(text));
        if (TextUtils.isEmpty(text) || text.equals("全部")) {
            company_name = "";
            currentPage = 1;
            mPresenter.getData(currentPage, company_name, area);
        } else {
            company_name = text;
            currentPage = 1;
            mPresenter.getData(currentPage, company_name, area);
        }
    }

    @Override
    public void OnItemClick(CompanyDescription entity) {
        CompanyContentActivity.startCompanyContentActivity(entity, getActivity());
    }

    @Override
    public void getLoading() {
        loading_layout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
    }

    @Override
    public void getDataEmpty() {
        loading_layout.setLoadingLayout(LoadingLayout.NO_DATA);
        tv_tip.setText("暂时没有数据点击添加");
    }

    @Override
    public void getDataFail(String msg) {
        loading_layout.setLoadingLayout(LoadingLayout.LOADDATA_ERROR);
    }

    @Override
    public void refreshOrLoadFail(String msg) {
        if (getSwipeRefreshWidget().isRefreshing()) {
            getSwipeRefreshWidget().setRefreshing(false);
            loading_layout.setLoadingLayout(LoadingLayout.LOADDATA_ERROR);
        } else {
            FrameManager.getInstance().toastPrompt(msg);
        }
    }

    @Override
    public void refreshView(List<CompanyDescription> entities) {
        showRefreshData(entities);
        if (entities == null || entities.size() == 0) {
            getDataEmpty();
        } else {
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        }
    }

    @Override
    public void loadMoreView(List<CompanyDescription> entities) {
        showMoreData(entities);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                showLoginDialog();
                break;

            case R.id.loading_layout:
                Intent intent = new Intent(getActivity(), CompanyAddActivity.class);
                intent.putExtra(CompanyAddActivity.NAME, company_name);
                startActivity(intent);
                break;
        }
    }

    private Pattern intPattern = Pattern.compile("^[-\\+]?[\\d]*\\.0*$");

    public String getString(Map map, String key, String defaultValue) {
        Object obj = map.get(key);
        return obj == null ? defaultValue : (obj instanceof Number && intPattern.matcher(obj.toString())
                .matches() ? String.valueOf(Long.valueOf(((Number) obj).longValue())) : obj.toString());
    }

    @Override
    public void onImageClick(CompanyDescription companyDescription) {
        CompanyDesMain.startCompanyDesMain(companyDescription, getActivity());
    }

    public void showLoginDialog() {
        AreaDialogFragment dialog = new AreaDialogFragment();
        dialog.setOnCodeListener(this);
        dialog.setOnStringListener(this);
        dialog.show(getActivity().getFragmentManager(), "loginDialog");
    }

    @Override
    public void onCodeInputComplete(String message) {
        area = message;
        currentPage = 1;
        mPresenter.getData(currentPage, company_name, area);
    }

    @Override
    public void onStringInputComplete(String message) {

    }

    @Override
    public void checkToken() {
        DataManager.deleteAllUser();
        SharePreferenceUtil.setUserTk("");
        FrameManager.getInstance().toastPrompt("身份失效请重现登录");
        LoginDialogFragment dialog = new LoginDialogFragment();
        dialog.show(getActivity().getFragmentManager(), "loginDialog");
    }
}
