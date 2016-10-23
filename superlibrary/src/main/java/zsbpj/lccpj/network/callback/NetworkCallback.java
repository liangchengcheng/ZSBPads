package zsbpj.lccpj.network.callback;

public interface NetworkCallback<T> {

  /**
   * 请求开始
   */
  void startRequest();

  /**
   * 请求返回数据
   */
  void respondSuccess(T data);

  /**
   * 请求返回错误
   */
  void respondWithError(Throwable t);

  /**
   * 请求结束，成功或者失败都会调用 endRequest()
   */
  void endRequest();
}

