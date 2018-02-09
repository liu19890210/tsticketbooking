package guominchuxing.tsbooking.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/1/24.
 * token拦截
 */

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
       Request request = chain.request();
       Response response = chain.proceed(request);

       if (isTokenExpired(response)){
        //同步请求方式，获取最新的Token
       String newSession = "getNewToken()";
           //使用新的Token，创建新的请求
       Request newRequest =  chain.request()
                                  .newBuilder()
                                  .header("cookie",newSession)
                                  .build();
       //重新请求
       return chain.proceed(newRequest);
       }
        return response;
    }

    /**
     * 根据response 判断token失效
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.message() == "未登录" ){
              return true;

        }
    return false;
    }

//    private String getNewToken() {
//
//    }
}
