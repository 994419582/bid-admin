package cn.teleinfo.bidadmin.blockchain.es.service.impl;

import cn.teleinfo.bidadmin.blockchain.document.TranscationDocument;
import cn.teleinfo.bidadmin.blockchain.es.page.Page;
import cn.teleinfo.bidadmin.blockchain.es.service.TranscationSearchService;
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
public class TranscationSearchServiceImpl extends BaseSearchServiceImpl<TranscationDocument> implements TranscationSearchService {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private cn.teleinfo.bidadmin.blockchain.es.repository.TranscationDocumentRepository TranscationsRepository;

    @Override
    public void save(TranscationDocument... transcations) {
        elasticsearchTemplate.putMapping(TranscationDocument.class);
        if(transcations.length > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            log.info("【保存索引】：{}", JSON.toJSONString(TranscationsRepository.saveAll(Arrays.asList(transcations))));
        }
    }
    @Override
    public void save(List<TranscationDocument> transcations) {
        elasticsearchTemplate.putMapping(TranscationDocument.class);
        if(transcations.size() > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            log.info("【保存索引】：{}", JSON.toJSONString(TranscationsRepository.saveAll(transcations)));
        }
    }

    @Override
    public void delete(String id) {
        TranscationsRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        TranscationsRepository.deleteAll();
    }

    @Override
    public TranscationDocument getById(String id) {
        return TranscationsRepository.findById(id).get();
    }

    @Override
    public List<TranscationDocument> getAll() {
        List<TranscationDocument> list = new ArrayList<>();
        TranscationsRepository.findAll().forEach(list::add);
        return list;
    }
}
