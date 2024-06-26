package com.xftxyz.doctorarrival.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.user.autoconfigure.WxOpenProperties;
import com.xftxyz.doctorarrival.user.mapper.UserInfoMapper;
import com.xftxyz.doctorarrival.user.service.WxOpenService;
import com.xftxyz.doctorarrival.vo.user.WxAccessToken;
import com.xftxyz.doctorarrival.vo.user.WxLoginQrCodeParam;
import com.xftxyz.doctorarrival.vo.user.WxUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class WxOpenServiceImpl implements WxOpenService {

    private static final String WX_OPEN_API_PREFIX = "https://api.weixin.qq.com/sns";

    private final WxOpenProperties wxOpenProperties;

    private final RestTemplate restTemplate;

    private final UserInfoMapper userInfoMapper;

    @Override
    public WxLoginQrCodeParam getWxLoginQrCodeParam() {
        String encodedRedirectUri = URLEncoder.encode(wxOpenProperties.getRedirectUri(), StandardCharsets.UTF_8);

        WxLoginQrCodeParam wxLoginQrCodeParam = new WxLoginQrCodeParam();
        wxLoginQrCodeParam.setSelfRedirect(false);
        wxLoginQrCodeParam.setId("wxlogin_container");
        wxLoginQrCodeParam.setAppid(wxOpenProperties.getAppId());
        wxLoginQrCodeParam.setScope("snsapi_login");
        wxLoginQrCodeParam.setRedirectUri(encodedRedirectUri);
        wxLoginQrCodeParam.setState(String.valueOf(System.currentTimeMillis()));
        wxLoginQrCodeParam.setStyle("black");
        wxLoginQrCodeParam.setHref("");
        return wxLoginQrCodeParam;
    }

    // 通过code获取access_token
    private WxAccessToken getAccessToken(String code) {
        return restTemplate.getForObject(WX_OPEN_API_PREFIX + "/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code",
                WxAccessToken.class, wxOpenProperties.getAppId(), wxOpenProperties.getAppSecret(), code);
    }

    // 刷新access_token有效期
    // private WxAccessToken refreshAccessToken(String refreshToken) {
    //     return restTemplate.getForObject(WX_OPEN_API_PREFIX + "/oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refreshToken}",
    //             WxAccessToken.class, wxOpenProperties.getAppId(), refreshToken);
    // }

    // 获取用户个人信息
    private WxUserInfo getUserInfo(String accessToken, String openid) {
        return restTemplate.getForObject(WX_OPEN_API_PREFIX + "/userinfo?access_token={accessToken}&openid={openid}",
                WxUserInfo.class, accessToken, openid);
    }


    @Override
    public String loginCallback(String code, String state) {
        WxAccessToken accessToken = getAccessToken(code);
        // 查询用户是否存在
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getOpenid, accessToken.getOpenid());
        UserInfo userInfo = userInfoMapper.selectOne(lambdaQueryWrapper);

        if (ObjectUtils.isEmpty(userInfo)) {
            // 不存在则创建用户
            userInfo = new UserInfo();
            userInfo.setOpenid(accessToken.getOpenid());
            WxUserInfo wxUserInfo = getUserInfo(accessToken.getAccessToken(), accessToken.getOpenid());
            if (!ObjectUtils.isEmpty(wxUserInfo) && ObjectUtils.isEmpty(wxUserInfo.getErrcode())) {
                userInfo.setNickName(wxUserInfo.getNickname());
            }
            userInfoMapper.insert(userInfo);
        }
        // 生成token
        String token = JwtHelper.generateToken(userInfo.getId());
        String nickName = URLEncoder.encode(userInfo.getNickName(), StandardCharsets.UTF_8);
        return "redirect:" + wxOpenProperties.getSiteOrigin() + "/weixin/callback" + "?token=" + token + "&nickName=" + nickName;
    }

    @Override
    public String verifySignature(String signature, String timestamp, String nonce, String echostr) {
        // 按字典序排序
        String[] params = {wxOpenProperties.getToken(), timestamp, nonce};
        Arrays.sort(params);

        // 拼接字符串并进行SHA1加密
        StringBuilder tmpStrBuilder = new StringBuilder();
        for (String param : params) {
            tmpStrBuilder.append(param);
        }
        String tmpStr = tmpStrBuilder.toString();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = tmpStr.getBytes(StandardCharsets.UTF_8);
            byte[] digest = messageDigest.digest(bytes);
            String sha1Str = bytesToHex(digest);

            // 比较签名是否一致
            if (sha1Str.equals(signature)) {
                // 签名校验成功，返回echostr参数内容
                return echostr;
            } else {
                // 签名校验失败
                return "Invalid signature";
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify the signature", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
