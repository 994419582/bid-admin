package cn.teleinfo.bidadmin.blockchain.es.service;

import cn.teleinfo.bidadmin.blockchain.document.TranscationDocument;

import java.util.List;

/**
 * @author 史维君
 * @version 0.1
 */
public interface TranscationSearchService extends BaseSearchService<TranscationDocument> {
    /**
     * 保存
     * @date: 2018/12/13 16:02
     */
    void save(TranscationDocument... transcations);

    /**
     * 保存
     * @date: 2018/12/13 16:02
     */
    void save(List<TranscationDocument> transcations);


    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 清空索引
     */
    void deleteAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TranscationDocument getById(String id);

    /**
     * 查询全部
     * @return
     */
    List<TranscationDocument> getAll();
}
