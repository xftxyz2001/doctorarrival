package com.xftxyz.doctorarrival.vo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WxLoginQrCodeParam {

    /**
     * true：手机点击确认登录后可以在 iframe 内跳转到 redirect_uri，
     * false：手机点击确认登录后可以在 top window 跳转到 redirect_uri。默认为 false。
     */
    @JsonProperty("self_redirect")
    private Boolean selfRedirect;

    /**
     * 第三方页面显示二维码的容器id
     */
    private String id;

    /**
     * 应用唯一标识，在微信开放平台提交应用审核通过后获得
     */
    private String appid;

    /**
     * 应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
     */
    private String scope;

    /**
     * 重定向地址，需要进行UrlEncode
     */
    @JsonProperty("redirect_uri")
    private String redirectUri;

    /**
     * 用于保持请求和回调的状态，授权请求后原样带回给第三方。
     * 该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     */
    private String state;

    /**
     * 提供"black"、"white"可选，默认为黑色文字描述。
     */
    private String style;

    /**
     * 自定义样式链接，第三方可根据实际需求覆盖默认样式。
     */
    private String href;
}
