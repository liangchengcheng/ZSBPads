package zsbpj.lccpj.network.callback;

public interface ErrorCallback {

  /**
   * 没有数据或者请求的路径找不到
   */
  void errorNotFound(ErrorModel errorModel);

  /**
   * 因为参数不对，导致无法继续处理后面逻辑
   */
  void errorUnprocessable(ErrorModel errorModel);

  /**
   * 当前请求需要用户验证
   */
  void errorUnauthorized(ErrorModel errorModel);

  /**
   * 权限校验不通过
   */
  void errorForbidden(ErrorModel errorModel);

  /**
   * 无网络错误
   */
  void eNetUnreach(Throwable t);

  /**
   * 链接超时错误
   */
  void errorSocketTimeout(Throwable t);

  /**
   * 此方法不能同时和其他 errorXXX() 方法使用
   */
  void error(ErrorModel errorModel);

}
