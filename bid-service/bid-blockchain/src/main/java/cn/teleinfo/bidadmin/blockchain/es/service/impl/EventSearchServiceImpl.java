package cn.teleinfo.bidadmin.blockchain.es.service.impl;

import cn.teleinfo.bidadmin.blockchain.document.EventDocument;
import cn.teleinfo.bidadmin.blockchain.es.page.Page;
import cn.teleinfo.bidadmin.blockchain.es.repository.EventDocumentRepository;
import cn.teleinfo.bidadmin.blockchain.es.service.EventSearchService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch 搜索引擎 service实现
 * @version 0.1
 */
@Service
@Slf4j
public class EventSearchServiceImpl extends BaseSearchServiceImpl<EventDocument> implements EventSearchService {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private EventDocumentRepository eventDocumentRepository;

    @Override
    public void save(EventDocument... events) {
        elasticsearchTemplate.putMapping(EventDocument.class);
        if(events.length > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            log.info("【保存索引】：{}", JSON.toJSONString(eventDocumentRepository.saveAll(Arrays.asList(events))));
        }
    }
    @Override
    public void save(List<EventDocument> events) {
        elasticsearchTemplate.putMapping(EventDocument.class);
        if(events.size() > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            log.info("【保存索引】：{}", JSON.toJSONString(eventDocumentRepository.saveAll(events)));
        }
    }

    @Override
    public void delete(String id) {
        eventDocumentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        eventDocumentRepository.deleteAll();
    }

    @Override
    public EventDocument getById(String id) {
        return eventDocumentRepository.findById(id).get();
    }

    @Override
    public List<EventDocument> getAll() {
        List<EventDocument> list = new ArrayList<>();
        eventDocumentRepository.findAll().forEach(list::add);
        return list;
    }

}
