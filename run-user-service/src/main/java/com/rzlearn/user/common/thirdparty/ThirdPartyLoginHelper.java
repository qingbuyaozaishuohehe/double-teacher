package com.rzlearn.user.common.thirdparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.base.support.Resources;
import com.rzlearn.base.util.HttpUtil;
import com.rzlearn.user.common.config.CustomerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>ClassName:ThirdPartyLoginHelper</p>
 * <p>Description:第三方登录辅助类</p>
 *
 * @author JiPeigong
 * @date 2018 -08-11 15:45:34
 */
@Slf4j
public final class ThirdPartyLoginHelper {

    public static final String RET = "ret";
    public static final String FEMALE = "女";
    public static final String ERRCODE = "errcode";
    public static final String ZERO = "0";
    public static final String F = "f";
    public static final String GENDER = "gender";
    public static final String ACCESS_TOKEN = "access_token";
    @Autowired
    private CustomerProperties customerProperties;

    public static void main(String[] args) {
//        ThirdPartyLoginHelper thirdPartyLoginHelper = new ThirdPartyLoginHelper();

    }

    /**
     * 获取QQ用户信息
     *
     * @param token  the token
     * @param openid the openid
     * @return the qq userinfo
     * @throws Exception the exception
     */
    public final ThirdPartyUser getQQUserinfo(String token, String openid) throws Exception {
        ThirdPartyUser user = new ThirdPartyUser();
        String url = customerProperties.getQq().getUserInfoUrl();
        url = url + "?format=json&access_token=" + token + "&oauth_consumer_key="
                + customerProperties.getQq().getAppId() + "&openid=" + openid;
        String res = HttpUtil.get(url);
        JSONObject json = JSON.parseObject(res);
        if (json.getIntValue(RET) == 0) {
            user.setUserName(json.getString("nickname"));
            String img = json.getString("figureurl_qq_2");
            if (img == null || "".equals(img)) {
                img = json.getString("figureurl_qq_1");
            }
            user.setAvatarUrl(img);
            String sex = json.getString("gender");
            if (FEMALE.equals(sex)) {
                user.setGender("0");
            } else {
                user.setGender("1");
            }
        } else {
            throw new IllegalArgumentException(json.getString("msg"));
        }
        return user;
    }

    /**
     * 获取微信用户信息
     *
     * @param token  the token
     * @param openid the openid
     * @return the wx userinfo
     * @throws Exception the exception
     */
    public final ThirdPartyUser getWxUserinfo(String token, String openid) throws Exception {
        ThirdPartyUser user = new ThirdPartyUser();
        String url = customerProperties.getWeixin().getUserInfoUrl();
        url = url + "?access_token=" + token + "&openid=" + openid;
        String res = HttpUtil.get(url);
        JSONObject json = JSON.parseObject(res);
        if (json.getString(ERRCODE) == null) {
            user.setUserName(json.getString("nickname"));
            String img = json.getString("headimgurl");
            if (img != null && !"".equals(img)) {
                user.setAvatarUrl(img);
            }
            String sex = json.getString("sex");
            if (ZERO.equals(sex)) {
                user.setGender("0");
            } else {
                user.setGender("1");
            }
        } else {
            throw new IllegalArgumentException(json.getString("errmsg"));
        }
        return user;
    }

    /**
     * 获取微博用户信息
     *
     * @param token the token
     * @param uid   the uid
     * @return weibo userinfo
     * @throws Exception the exception
     */
    public final ThirdPartyUser getWeiboUserinfo(String token, String uid) throws Exception {
        ThirdPartyUser user = new ThirdPartyUser();
        String url = customerProperties.getWeibo().getUserInfoUrl();
        url = url + "?access_token=" + token + "&uid=" + uid;
        String res = HttpUtil.get(url);
        JSONObject json = JSON.parseObject(res);
        String name = json.getString("name");
        String nickName = StringUtils.isBlank(json.getString("screen_name")) ? name : json.getString("screen_name");
        user.setAvatarUrl(json.getString("avatar_large"));
        user.setUserName(nickName);
        if (F.equals(json.getString(GENDER))) {
            user.setGender("0");
        } else {
            user.setGender("1");
        }
        user.setToken(token);
        user.setOpenid(uid);
        user.setProvider("sina");
        return user;
    }

    /**
     * 获取QQ的认证token和用户OpenID
     *
     * @param code the code
     * @param host the host
     * @return qq token and openid
     * @throws Exception the exception
     */
    public final Map<String, String> getQQTokenAndOpenid(String code, String host) throws Exception {
        Map<String, String> map = new HashMap<>(16);
        // 获取令牌
        String tokenUrl = customerProperties.getQq().getAccessTokenUrl();
        tokenUrl = tokenUrl + "?grant_type=authorization_code&client_id=" + customerProperties.getQq().getAppId()
                + "&client_secret=" + customerProperties.getQq().getAppKey() + "&code=" + code
                + "&redirect_uri=http://" + host + customerProperties.getQq().getRedirectUrl();
        String tokenRes = HttpUtil.get(tokenUrl);
        if (tokenRes != null && tokenRes.indexOf(ACCESS_TOKEN) > -1) {
            Map<String, String> tokenMap = toMap(tokenRes);
            map.put(ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
            // 获取QQ用户的唯一标识openID
            String openIdUrl = customerProperties.getQq().getGetOpenIdUrl();
            openIdUrl = openIdUrl + "?access_token=" + tokenMap.get(ACCESS_TOKEN);
            String openIdRes = HttpUtil.get(openIdUrl);
            int i = openIdRes.indexOf("(");
            int j = openIdRes.indexOf(")");
            openIdRes = openIdRes.substring(i + 1, j);
            JSONObject openidObj = JSON.parseObject(openIdRes);
            map.put("openId", openidObj.getString("openid"));
        } else {
            throw new IllegalArgumentException(Resources.getMessage("THIRDPARTY.LOGIN.NOTOKEN", "QQ"));
        }
        return map;
    }

    /**
     * 获取微信的认证token和用户OpenID
     *
     * @param code the code
     * @param host the host
     * @return wx token and openid
     * @throws Exception the exception
     */
    public final Map<String, String> getWxTokenAndOpenid(String code, String host) throws Exception {
        Map<String, String> map = new HashMap<>(16);
        // 获取令牌
        String tokenUrl = customerProperties.getWeixin().getAccessTokenUrl();
        tokenUrl = tokenUrl + "?appid=" + customerProperties.getWeixin().getAppId() + "&secret="
                + customerProperties.getWeixin().getAppKey() + "&code=" + code + "&grant_type=authorization_code";
        String tokenRes = HttpUtil.get(tokenUrl);
        if (tokenRes != null && tokenRes.indexOf(ACCESS_TOKEN) > -1) {
            Map<String, String> tokenMap = toMap(tokenRes);
            map.put("access_token", tokenMap.get(ACCESS_TOKEN));
            // 获取微信用户的唯一标识openid
            map.put("openId", tokenMap.get("openid"));
        } else {
            throw new IllegalArgumentException(Resources.getMessage("THIRDPARTY.LOGIN.NOTOKEN", "weixin"));
        }
        return map;
    }

    /**
     * 获取新浪登录认证token和用户id
     *
     * @param code the code
     * @param host the host
     * @return weibo token and uid
     */
    public final JSONObject getWeiboTokenAndUid(String code, String host) {
        JSONObject json = null;
        try {
            // 获取令牌
            String tokenUrl = customerProperties.getWeibo().getAccessTokenUrl();
            ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
            BasicNameValuePair params1 = new BasicNameValuePair("client_id", customerProperties.getWeibo().getAppId());
            list.add(params1);
            BasicNameValuePair params2 = new BasicNameValuePair("client_secret", customerProperties.getWeibo().getAppKey());
            list.add(params2);
            BasicNameValuePair params3 = new BasicNameValuePair("grant_type", "authorization_code");
            list.add(params3);
            BasicNameValuePair params4 = new BasicNameValuePair("redirect_uri", "http://" + host + customerProperties.getWeibo().getRedirectUrl());
            list.add(params4);
            BasicNameValuePair params5 = new BasicNameValuePair("code", code);
            list.add(params5);
            String tokenRes = HttpUtil.post(tokenUrl, list);
            // String tokenRes = httpClient(tokenUrl);
            // {"access_token":"2.00AvYzKGWraycB344b3eb242NUbiQB","remind_in":"157679999","expires_in":157679999,"uid":"5659232590"}
            if (tokenRes != null && tokenRes.indexOf(ACCESS_TOKEN) > -1) {
                json = JSON.parseObject(tokenRes);
            } else {
                throw new BusinessException(MessageCode.FAILED);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return json;
    }

    /**
     * 将格式为s1&s2&s3...的字符串转化成Map集合
     *
     * @param str
     * @return
     */
    private static final Map<String, String> toMap(String str) {
        Map<String, String> map = new HashMap<>(16);
        String[] strs = str.split("&");
        for (String str2 : strs) {
            String[] ss = str2.split("=");
            map.put(ss[0], ss[1]);
        }
        return map;
    }
}
