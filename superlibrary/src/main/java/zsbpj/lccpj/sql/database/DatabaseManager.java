package zsbpj.lccpj.sql.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.Collection;
import java.util.List;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.sql.DaoMaster;
import zsbpj.lccpj.sql.DaoSession;
import zsbpj.lccpj.sql.T_JC_LOGININFO;
import zsbpj.lccpj.sql.dao.IDatabase;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    DatabaseManager
 */
public abstract   class DatabaseManager <M,K> implements IDatabase<M,K>{

    private static final String DEFAULT_DATABASE_NAME="xx.db";

    private static DaoMaster.DevOpenHelper mHelper;
    private static DaoSession daoSession;
    private static SQLiteDatabase database;
    private static DaoMaster daoMaster;
    protected String dbName;
    private Context context;

    /**
     * 默认的构造函数
     */
    public DatabaseManager(){
        this.context= FrameManager.getAppContext();
        this.dbName=DEFAULT_DATABASE_NAME;
        getOpenHelper(context,dbName);
    }

    public DatabaseManager(@NonNull String dataBaseName){
        this.context= FrameManager.getAppContext();
        this.dbName=dataBaseName;
        getOpenHelper(context,dbName);
    }

    protected void openReadableDb()throws SQLiteException{
        getReadableDatabase();
        getDaoMaster();
        getDaoSession();
    }

    protected void openWritableDb()throws SQLiteException{
        getWritableDatabase();
        getDaoMaster();
        getDaoSession();
    }

    /**
     * 获取WritableDatabase
     */
    protected SQLiteDatabase getWritableDatabase(){
        database=getOpenHelper(context,dbName).getWritableDatabase();
        return database;
    }

    /**
     * 获取ReadableDatabase
     */
    protected SQLiteDatabase getReadableDatabase(){
        database=getOpenHelper(context,dbName).getReadableDatabase();
        return database;
    }

    /**
     * 获取DevOpenHelper
     * @param context 上下文对象
     * @param dataBaseName 数据库的名字
     * @return DevOpenHelper
     */
    protected  DaoMaster.DevOpenHelper getOpenHelper(@NonNull Context context,@NonNull String dataBaseName){
        if (mHelper==null){
            mHelper=new DaoMaster.DevOpenHelper(context,dataBaseName,null);
        }
        return mHelper;
    }
    @Override
    public boolean insert(M m) {
        try{
            getWritableDatabase();
            getAbstractDao().insert(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(M m) {
        try{
            openWritableDb();
            getAbstractDao().delete(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K m) {
        try{
            if (TextUtils.isEmpty(m.toString()))
                return false;
            openWritableDb();
            getAbstractDao().deleteByKey(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTex(K... key) {
        try{
            openWritableDb();
            getAbstractDao().deleteByKeyInTx(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<M> list) {
        try{
            if (list==null||list.size()==0)return false;
            openWritableDb();
            getAbstractDao().deleteInTx(list);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try{
            openWritableDb();
            getAbstractDao().deleteAll();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(M m) {
        try{
            getWritableDatabase();
            getAbstractDao().insertOrReplace(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean update(M m) {
        try{
            openWritableDb();
            getAbstractDao().update(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(M... m) {
        try{
            openWritableDb();
            getAbstractDao().updateInTx(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(List<M> mList) {
        try{
            if (mList==null||mList.size()==0)return  false;
            openWritableDb();
            getAbstractDao().updateInTx(mList);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public M selectByPrimaryKey(K key) {
        try{
            openReadableDb();
            return getAbstractDao().load(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<M> loadAll() {
        List<M> list=null;
        try{
            openReadableDb();
            list= getAbstractDao().loadAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public boolean refresh(M m) {
        try{
            openWritableDb();
            getAbstractDao().refresh(m);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void clearDaoSession() {
        if (daoSession!=null){
            daoSession.clear();
            daoSession=null;
        }
    }

    @Override
    public void closeDbConnections() {
        if (mHelper!=null){
            mHelper.close();
            mHelper=null;
        }
        if (daoSession!=null){
            daoSession.clear();
            daoSession=null;
        }
    }

    @Override
    public boolean dropDataBase() {
        try{
            openWritableDb();
            daoSession.deleteAll(T_JC_LOGININFO.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try{
            openWritableDb();
            getDaoSession().runInTx(runnable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public AbstractDao<M, K> getAbstractDao() {
        return null;
    }

    @Override
    public boolean insertList(List<M> mList) {
        try{
            if (mList==null||mList.size()==0)
                return false;
            openWritableDb();
            getAbstractDao().insertInTx(mList);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplaceList(List<M> mList) {
        try{
            if (mList==null||mList.size()==0)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplaceInTx(mList);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public QueryBuilder<M> getQueryBuilder() {
        openReadableDb();
        return getAbstractDao().queryBuilder();
    }

    @Override
    public List<M> queryRaw(String where, String... selectionArg) {
       openReadableDb();
        return getAbstractDao().queryRaw(where,selectionArg);
    }

    @Override
    public Query<M> queryRawCreate(String where, Object... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreate(where,selectionArg);
    }

    @Override
    public Query<M> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
       openReadableDb();
        return getAbstractDao().queryRawCreateListArgs(where,selectionArg);
    }

    /**
     * 获取DaoMaster
     */
    private DaoMaster getDaoMaster(){
        daoMaster=new DaoMaster(database);
        return daoMaster;
    }

    protected DaoSession getDaoSession(){
        if (daoSession==null){
            daoSession=daoMaster.newSession();
        }
        return daoSession;
    }
}
