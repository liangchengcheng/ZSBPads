package com.lcc.mvp.view;

import com.lcc.entity.CodeEntity;
import java.util.List;

/**
 * Created by lcc on 16/5/5.
 */
public interface CompanyView extends BaseView{
    /**
     * 重置密码成功
     */
    void showSuccess();

    /**
     * 重置密码失败
     */
    void showError(String msg);

    void refreshView(List<CodeEntity> entities);

    void loadMoreView(List<CodeEntity> entities);
}
