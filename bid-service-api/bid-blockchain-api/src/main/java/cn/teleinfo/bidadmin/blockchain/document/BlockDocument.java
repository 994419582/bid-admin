package cn.teleinfo.bidadmin.blockchain.document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 区块实体
 * @author shiweijun
 * @version 0.1
 */
@Data
@Document(indexName = "block", type = "block")
//@Mapping(mappingPath = "productIndex.json") // 解决IK分词不能使用问题
public class BlockDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @Id
    private String id;
    /**
     * 区块高度
     */
    @ApiModelProperty(value = "区块高度")
    private Long blockNumber;
    /**
     * 区块哈希
     */
    @ApiModelProperty(value = "区块哈希")
    private String blockHash;
    /**
     * 父哈希
     */
    @ApiModelProperty(value = "父哈希")
    private String parentHash;
    /**
     * 随机值
     */
    @ApiModelProperty(value = "随机值")
    private String nonce;
    /**
     * 叔区块加密值
     */
    @ApiModelProperty(value = "叔区块加密值")
    private String sha3Uncles;
    /**
     * 过滤器
     */
    @ApiModelProperty(value = "过滤器")
    private String logsBloom;
    /**
     * 交易根哈希
     */
    @ApiModelProperty(value = "交易根哈希")
    private String transactionsRoot;
    /**
     * 状态根哈希
     */
    @ApiModelProperty(value = "状态根哈希")
    private String stateRoot;
    /**
     * 收据根哈希
     */
    @ApiModelProperty(value = "收据根哈希")
    private String receiptsRoot;
    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;
    /**
     * 矿工地址
     */
    @ApiModelProperty(value = "矿工地址")
    private String miner;
    /**
     * 最大值哈希
     */
    @ApiModelProperty(value = "最大值哈希")
    private String mixHash;
    /**
     * 挖矿难度
     */
    @ApiModelProperty(value = "挖矿难度")
    private String difficulty;
    /**
     * 总难度
     */
    @ApiModelProperty(value = "总难度")
    private String totalDifficulty;
    /**
     * 其他字段
     */
    @ApiModelProperty(value = "其他字段")
    private String extraData;
    /**
     * 区块大小
     */
    @ApiModelProperty(value = "区块大小")
    private String size;
    /**
     * gasLimit
     */
    @ApiModelProperty(value = "gasLimit")
    private String gasLimit;
    /**
     * gasUsed
     */
    @ApiModelProperty(value = "gasUsed")
    private String gasUsed;
    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String timestamp;
    @ApiModelProperty(value = "交易数")
    private Integer transactionCount ;

}
