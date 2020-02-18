package cn.teleinfo.bidadmin.blockchain.es.service;


import cn.teleinfo.bidadmin.blockchain.document.EventDocument;

import java.util.List;

/**
 * @author 史维君
 * @version 0.1
 */
public interface EventSearchService extends BaseSearchService<EventDocument> {
    /**
     * 保存
     * @date: 2018/12/13 16:02
     */
    void save(EventDocument... events);
    /**
     * 保存
     * @date: 2018/12/13 16:02
     */
    void save(List<EventDocument> events);

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
    EventDocument getById(String id);

    /**
     * 查询全部
     * @return
     */
    List<EventDocument> getAll();
}
