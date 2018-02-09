package guominchuxing.tsbooking.api;

import java.util.List;

import guominchuxing.tsbooking.bean.BoardBean;
import guominchuxing.tsbooking.bean.CheckBean;
import guominchuxing.tsbooking.bean.CheckNumBean;
import guominchuxing.tsbooking.bean.HistoryBeans;
import guominchuxing.tsbooking.bean.HotelInfoBean;
import guominchuxing.tsbooking.bean.HotelSumBean;
import guominchuxing.tsbooking.bean.LoginBean;
import guominchuxing.tsbooking.bean.NoticeBean;
import guominchuxing.tsbooking.bean.ResetCodeBean;
import guominchuxing.tsbooking.bean.ResultBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/3/6.
 */
public interface ApiManagerService {
    /**
     * 登录
     * @param mobile
     * @param pw
     * @param app
     * @param method
     * @return
     */
    @GET("login.do")
    Observable<LoginBean> getLogin(
            @Query("loginId") String mobile,
            @Query("password") String pw,
            @Query("user") int user,
            @Query("app") int app,
            @Query("method") int method,
            @Query("idType") int idType
    );

    /**
     *
     * @param forType
     * @param odyCreateTime
     * @param type
     * @param token
     * @param app
     * @return
     */
    @GET("notice/list.do")
    Observable<NoticeBean> getNoticeList(
             @Query("forType") int forType,
             @Query("odyCreateTime") int odyCreateTime,
             @Query("type") int type,
             @Query("token") String token,
             @Query("app") int app
             );

    /**
     *
     * @param forType
     * @param odyCreateTime
     * @param type
     * @param token
     * @param app
     * @return
     */
    @GET("notice/list.do")
    Observable<NoticeBean> getNoticeDetails(
            @Query("q") String title,
            @Query("forType") int forType,
            @Query("odyCreateTime") int odyCreateTime,
            @Query("type") int type,
            @Query("token") String token,
            @Query("app") int app);
    /**
     * 检票详情
     * @param num
     * @param token
     * @param method
     * @param app
     * @return
     */
    @GET("voucher/num/{num}.do")
    Observable<ResultBean<CheckNumBean>> getCheckNum(
            @Path("num") String num,
            @Query("token") String token,
            @Query("method") int method,
            @Query("app") int app);

    /**
     * 检票
     * @param token
     * @param num
     * @param app
     *
     * @return
     */
    @GET("voucher/check.do")
    Observable<CheckBean> getCheck(
            @Query("token") String token,
            @Query("num") String num,
            @Query("app") int app
           );
//    /**
//     * 订房
//     * @param token
//     * @param q
//     * @param billNo
//     * @param obyExpectTime
//     * @param app
//     * @param method
//     * @return
//     */
//    @GET("bill/list.do")
//    Observable<BookingBean> getBooking(@Query("token") String token,
//                                       @Query("q") String q,
//                                       @Query("billNo") String billNo,
//                                       @Query("obyExpectTime") int obyExpectTime,
//                                       @Query("app") int app,
//                                       @Query("method") int method);

    /**
     * 历史验证数据
     * @param token
     * @param
     * @param method
     * @param app

     */
    @GET("voucher/list.do")
    Observable<ResultBean<List<HistoryBeans>>> getHistoryVoucher(@Query("token") String token,
                                                                @Query("checkedTimeFrom") String checkedTimeFrom,
                                                                @Query("checkedTimeTo") String checkedTimeTo,
//                                              @Query("pageSize")  int pageSize,
//                                              @Query("pageNum") int pageNum,
                                                                @Query("odyCreateTime") int odyCreateTime,
                                                                @Query("app") int app,
                                                                @Query("method") int method);

    /**
     *
     * @param token
     * @param mobile
     * @param type 消息类型(1: 注册验证码；2:登录动态码; 3: 找回密码/重置密码; 4:手机与账号绑定；5: 解绑手机; 6:更换手机号[原手机短信]； 7: 更换手机号[新手机短信])
     * @param app
     * @return
     */
    @GET("sms.do")
    Observable<ResetCodeBean> getSmsDo(@Query("token") String token,
                                      @Query("m") String mobile,
                                      @Query("type") int type,
                                      @Query("app") int app);

    /**
     * 校验
     * @param token
     * @param mobile
     * @param code
     * @param type
     * @param app
     * @return
     */
    @GET("checkCode.do")
    Observable<ResetCodeBean> getCheckCode(@Query("token") String token,
                                      @Query("m") String mobile,
                                      @Query("c") String code,
                                      @Query("t") int type,
                                      @Query("user") int user,
                                      @Query("app") int app);

    /**
     *修改密码or重置密码
     * @param token
     * @param mobile
     * @param
     * @param newPassword
     * @param method 2
     * @param code
     * @param app
     * @return
     */
    @GET("password.do")
    Observable<ResetCodeBean> getPassword(@Query("token") String token,
                                          @Query("mobile") String mobile,
                                          @Query("newPassword") String newPassword,
                                          @Query("method") int method,
                                          @Query("code") String code,
                                          @Query("userType") int userType,
                                          @Query("app") int app);

    /**
     * 判断手机号是否存在
     * @param id
     * @param type
     * @param token
     * @param app
     * @return
     */
    @GET("/fans/exists.do")
    Observable<ResetCodeBean> getExistsDo(@Query("id") String id,
                                          @Query("type") int type,
                                          @Query("token") String token,
                                          @Query("app") int app);


    /**
     * 更换手机号
     * @param oldCode
     * @param mobile
     * @param code
     * @param token
     * @param app
     * @return
     */
    @GET("change.do")
    Observable<ResetCodeBean> getChangeDo(@Query("oldCode") String oldCode,
                                          @Query("mobile") String mobile,
                                          @Query("code") String code,
                                          @Query("token") String token,
                                          @Query("app") int app

    );
    /**
     * 酒店信息
     * @param id
     * @param token
     * @param type
     * @param app
     * @return
     */
//    @GET("hotel/{id}.do")
//    Observable<HotelInfoBean> getHotelInfo(
//                                           @Path("id") String id,
//                                           @Query("token") String token,
//                                           @Query("type") int type,
//                                           @Query("method") int method,
//                                           @Query("app") int app);
//

    /**
     *
     * @param id
     * @param token
     * @param app
     * @return
     */

    @GET("scenic/{id}.do")
    Observable<ResultBean<HotelInfoBean>> getTicketInfo(
            @Path("id") String id,
            @Query("token") String token,
            @Query("app") int app);
    /**
     *库存，销售状况
     * @param token
     * @param app
     * @return
     */
    @GET("hotel/sum.do")
    Observable<HotelSumBean> getHotelSumDo(
                                           @Query("token") String token,
                                           @Query("type") int type,
                                           @Query("method") int method,
                                           @Query("app") int app);

    /**
     *周汇表
     * @param from
     * @param to
     * @param token
     * @param app
     * @return
     */
//    @GET("report/days.do")
//    Observable<WeekBean> getReportDaysDo(
//                                         @Query("from") String from,
//                                         @Query("to") String to,
//                                         @Query("token") String token,
//                                         @Query("app") int app);

    /***
     * 按天汇总
     * @param from
     * @param to
     * @param token
     * @param app
     * @return
     */
//    @GET("report/days/sum.do")
//    Observable<SumBean> getReportDaysSumDo(
//            @Query("from") String from,
//            @Query("to") String to,
//            @Query("token") String token,
//            @Query("app") int app);

    /**
     *月汇表
//     * @param from
//     * @param to
     * @param token
     * @param app
     * @return
     */
//    @GET("report/mons.do")
//    Observable<WeekBean> getReportMonsDo(
//            @Query("from") String from,
//            @Query("to") String to,
//            @Query("token") String token,
//            @Query("app") int app);

    /**
     * 游客看板
     * @param expectTimeFrom
     * @param expectTimeTo
     * @param payStatus
     * @param status
     * @param token
     * @param app
     * @return
     */
    @GET("bill/list.do")
    Observable<ResultBean<List<BoardBean>>> getBoard(
            @Query("expectTimeFrom") String expectTimeFrom,
            @Query("expectTimeTo") String expectTimeTo,
            @Query("payStatus") int payStatus,
            @Query("status") int status,
            @Query("token") String token,
            @Query("app") int app);

}
