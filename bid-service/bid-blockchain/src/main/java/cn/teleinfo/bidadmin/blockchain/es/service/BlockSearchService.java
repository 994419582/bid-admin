package cn.teleinfo.bidadmin.blockchain.es.service;

import cn.teleinfo.bidadmin.blockchain.document.BlockDocument;

import java.util.List;

/**
 * @author 史维君
 * @version 0.1
 */
public interface BlockSearchService extends BaseSearchService<BlockDocument> {
    /**
     * 保存
     * @date: 2018/12/13 16:02
     */
    void save(BlockDocument... blockDocuments);
    /**
     * 保存
     * @date: 2018/12/13 16:02
     */
    void save(List<BlockDocument> blockDocuments);

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
    BlockDocument getById(String id);

    /**
     * 查询全部
     * @return
     */
    List<BlockDocument> getAll();
}
