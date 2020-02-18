package cn.teleinfo.bidadmin.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人脸比对
 *
 * @author tbc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityBO {

    /**
     * 请求异常
     */
    public static final Integer REQUEST_SUCCESS = 1;
    public static final Integer REQUEST_FAILURE = -1;


    private Integer code;

    private String message;

    /**
     * 第一字符: DN及保留数据匹配比对
     */
    private Character firstAuth;

    private String firstMsg;

    /**
     * 第二字符: 人像比对
     */
    private Character secondAuth;

    private String secondMsg;


    /**
     * 第三字符: 网上凭证及认证码匹配比对
     */
    private Character thirdAuth;

    private String thirdMsg;


    /**
     * 第四字符: 安全策略结果
     */
    private Character fourthAuth;

    private String fourthMsg;


    public IdentityBO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
