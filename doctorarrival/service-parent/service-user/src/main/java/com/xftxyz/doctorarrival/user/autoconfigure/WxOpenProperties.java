package com.xftxyz.doctorarrival.user.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.open")
public class WxOpenProperties {

    private String appId;
    private String appSecret;
    private String redirectUri;
    private String siteOrigin;
}
