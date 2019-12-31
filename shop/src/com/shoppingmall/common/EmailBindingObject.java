package com.shoppingmall.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bert Q
 * ClassName : EmailBindingObject
 * Description TODO
 */
@Getter@Setter
public class EmailBindingObject {
    private Integer userId;
    private String emailAddr;
    private String token;

    public EmailBindingObject() {

    }

    public EmailBindingObject(Integer userId, String emailAddr, String token) {
        this.userId = userId;
        this.emailAddr = emailAddr;
        this.token = token;
    }
}
