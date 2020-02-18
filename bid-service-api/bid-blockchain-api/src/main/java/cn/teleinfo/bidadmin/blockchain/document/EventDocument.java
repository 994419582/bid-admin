package cn.teleinfo.bidadmin.blockchain.document;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 交易实体
 * @author shiweijun
 * @version 0.1
 */
@Data
@Document(indexName = "events", type = "events")
//@Mapping(mappingPath = "productIndex.json") // 解决IK分词不能使用问题
public class EventDocument implements Serializable {

//    @Id
//    private Integer id;
private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @Id
    private String  id;
    /**
     * 交易hash
     */
    @ApiModelProperty(value = "交易hash")
    private String transactionHash;
    /**
     * 交易索引
     */
    @ApiModelProperty(value = "交易索引")
    private Integer transactionIndex;
    /**
     * 区块hash
     */
    @ApiModelProperty(value = "区块hash")
    @TableField("blockHash")
    private String blockHash;
    /**
     * 区块高度
     */
    @ApiModelProperty(value = "区块高度")
    private Integer blockNumber;
    /**
     * gas
     */
    @ApiModelProperty(value = "gas")
    private String cumulativeGasUsed;
    /**
     * gas使用量
     */
    @ApiModelProperty(value = "gas使用量")
    private String gasUsed;
    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;
    /**
     * 收据根哈希
     */
    @ApiModelProperty(value = "收据根哈希")
    private String root;
    /**
     * 发送者
     */
    @ApiModelProperty(value = "发送者")
    private String txfrom;
    /**
     * 接受者
     */
    @ApiModelProperty(value = "接受者")
    private String txto;
    /**
     * 交易事件
     */
    @ApiModelProperty(value = "交易事件")
    private String events;
    /**
     * log布隆过滤
     */
    @ApiModelProperty(value = "log布隆过滤")
    private String logsBloom;
    @ApiModelProperty(value = "状态")
    private String status;

}
