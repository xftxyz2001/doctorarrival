package com.xftxyz.doctorarrival.user.service;

import com.xftxyz.doctorarrival.vo.user.WxLoginQrCodeParam;

public interface WxOpenService {
    WxLoginQrCodeParam getWxLoginQrCodeParam();

    String loginCallback(String code, String state);
}
