package com.shoppingmall.alipay;

import lombok.Data;

/**
 * @author Bert Q
 * ClassName : AlipayCallBackBean
 * Description TODO
 */
@Data
public class AlipayCallback {
    private String charset;
    // 订单
    private String out_trade_no;
    private String method;
    // 总金额
    private String total_amount;
    // 签名
    private String sign;
    private String trade_no;
    private String auth_app_id;
    private String version;
    private String app_id;
    private String sign_type;
    private String seller_id;
    private String timestamp;


}
