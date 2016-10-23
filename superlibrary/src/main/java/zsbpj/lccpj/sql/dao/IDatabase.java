package zsbpj.lccpj.sql.dao;

import java.util.Collection;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    IDatabase
 */
public interface IDatabase <M,K> {

    /**
     * 增加
     */
    boolean insert(M m);

    /**
     * 删除
     */
    boolean delete(M m);

    /**
     * 删除通过key
     */
    boolean deleteByKey(K key);

    /**
     * 删除通过intx
     */
    boolean deleteByKeyInTex(K... key);

    /**
     * 删除集合
     */
    boolean deleteList(List<M> list);

    /**
     * 删除全部
     */
    boolean deleteAll();

    /**
     * 增加或者替换
     */
    boolean insertOrReplace(M key);

    /**
     * 更新操作
     */
    boolean update(M m);

    /**
     * 更新通过InText
     */
    boolean updateInTx(M... m);

    /**
     * 更新集合
     */
    boolean updateList(List<M> mList);

    /**
     * 通过主键查询
     */
    M selectByPrimaryKey(K key);

    /**
     * 查询全部
     */
    List<M> loadAll();

    boolean refresh(M m);

    /**
     * 关闭数据库连接
     */
    void closeDbConnections();

    /**
     * 清除缓存
     */
    void clearDaoSession();

    /**
     * 删除掉数据库
     */
    boolean dropDataBase();

    /**
     * 事物
     */
    void runInTx(Runnable runnable);

    /**
     * 获取Dao
     */
    AbstractDao<M,K> getAbstractDao();

    /**
     * 添加集合
     */
    boolean insertList(List<M> mList);

    boolean insertOrReplaceList(List<M> mList);

    /**
     * 自定义查询
     */
    QueryBuilder<M> getQueryBuilder();

    List<M> queryRaw(String where,String... selectionArg);

    Query<M> queryRawCreate(String where ,Object... selectionArg);

    Query<M> queryRawCreateListArgs(String where ,Collection<Object> selectionArg);


}
