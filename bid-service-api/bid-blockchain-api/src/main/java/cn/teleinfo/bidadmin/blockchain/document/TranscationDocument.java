package cn.teleinfo.bidadmin.blockchain.document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 交易实体
 * @author shiweijun
 * @version 0.1
 */
@Data
@Document(indexName = "transactions", type = "transactions")
//@Mapping(mappingPath = "productIndex.json") // 解决IK分词不能使用问题
public class TranscationDocument implements Serializable {

//    @Id
//    private Integer id;
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @Id
    private String id;
    /**
     * 交易哈希
     */
    @ApiModelProperty(value = "交易哈希")
    private String hash;
    /**
     * 交易nonce
     */
    @ApiModelProperty(value = "交易nonce")
    private String nonce;
    /**
     * 区块哈希
     */
    @ApiModelProperty(value = "区块哈希")
    private String blockHash;
    /**
     * 区块高度
     */
    @ApiModelProperty(value = "区块高度")
    private Long blockNumber;
    /**
     * 交易索引
     */
    @ApiModelProperty(value = "交易索引")
    private Integer transactionIndex;
    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private String txfrom;
    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private String txto;
    /**
     * 交易值
     */
    @ApiModelProperty(value = "交易值")
    private String value;
    /**
     * gasPrice
     */
    @ApiModelProperty(value = "gasPrice")
    private String gasPrice;
    /**
     * gas
     */
    @ApiModelProperty(value = "gas")
    private String gas;
    /**
     * 输入参数
     */
    @ApiModelProperty(value = "输入参数")
    private String input;
    /**
     * 交易创建时间
     */
    @ApiModelProperty(value = "交易创建时间")
    private String creates;
    /**
     * 用户公钥
     */
    @ApiModelProperty(value = "用户公钥")
    private String publicKey;
    /**
     * 其他字段
     */
    @ApiModelProperty(value = "其他字段")
    private String raw;
    /**
     * 签名字段r
     */
    @ApiModelProperty(value = "签名字段r")
    private String r;
    /**
     * 签名字段s
     */
    @ApiModelProperty(value = "签名字段s")
    private String s;
    /**
     * 签名字段v
     */
    @ApiModelProperty(value = "签名字段v")
    private String v;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private String createDate;


}
