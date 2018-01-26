package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/6/16.
 * describe：
 * modify:
 * modify date:
 */
public class LoginResponse {


    /**
     * token : zS0uN96jsU63pods7YW2pfCtXUi52cdcQQp5wRPCE4qR7Yw6tXzDlJHu25Jwf-9CqFx-mUkuk8kullnWdfek_BkCmh-pAx47jmgS1DA-PRegjM9e3ph3d6HW2wi91PfcblQusAJjxY48eKSqXSMq26bXchYDtpwSS6_em5WMpTVLctT67IAM73uIngqTaNMARup44ONbgg5Z09OK0Ylv5B5rVqWLXtdX5icZkWWSF58DYVuRUFrvw9McUGo7dg8-3YCvKyBU11S05rJ64gDczK1vpTi4Q7EHlgP8mZjf6tZn9Yiut-UbmXDKzmmUPEmzoWjCukDNOpIv27tcsy99QA0AaglkTpqL6VS7TTryu6ZIIBOW6A62IcjOZmnyJZV2vdBZF-TxbdZzWdTDCo7PpQklgw8zU-MPZUeVj-W6HHXvS6KlcG2u9jDElYL1g3Pb_6USXdXzWWFNdkCBDg5dAQ
     * positionid : 3
     */

    private String token;
    private int positionid;
    private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPositionid() {
        return positionid;
    }

    public void setPositionid(int positionid) {
        this.positionid = positionid;
    }
}
