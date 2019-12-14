package com.shoppingmall.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

/**
 * @author Bert Q
 * ClassName : Alipay
 * Description zfb接口
 */
public class Alipay {

    public static final String DEFAULT_FORMAT = "json";

    /**
     * 支付接口
     * @param alipayBean
     * @return 付款信息
     * @throws AlipayApiException
     */
    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayProperties.GATEWAY_URL
                , AlipayProperties.APP_ID, AlipayProperties.PRIVARY_KEY
                , DEFAULT_FORMAT
                , AlipayProperties.CHARSET
                , AlipayProperties.PUBLIC_KEY
                , AlipayProperties.SIGN_TYPE);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl( AlipayProperties.RETURN_URL);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(AlipayProperties.NOTIFY_URL);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 3、请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        // 返回付款信息
        return result;
    }
}