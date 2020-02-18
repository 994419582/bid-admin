package cn.teleinfo.bidadmin.blockchain.es.service.impl;

import cn.teleinfo.bidadmin.blockchain.document.BlockDocument;
import cn.teleinfo.bidadmin.blockchain.es.page.Page;
import cn.teleinfo.bidadmin.blockchain.es.repository.BlockDocumentRepository;
import cn.teleinfo.bidadmin.blockchain.es.service.BlockSearchService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch 搜索引擎 service实现
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:33
 */
@Service
@Slf4j
public class BlockSearchServiceImpl extends BaseSearchServiceImpl<BlockDocument> implements BlockSearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private BlockDocumentRepository blockDocumentRepository;

    @Override
    public void save(BlockDocument... blockDocuments) {
        elasticsearchTemplate.putMapping(BlockDocument.class);
        if(blockDocuments.length > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            blockDocumentRepository.saveAll(Arrays.asList(blockDocuments));
//           log.info("【保存索引】：{}", JSON.toJSONString(blockRepository.saveAll(Arrays.asList(blockDocuments))));
        }
    }
    @Override
    public void save(List<BlockDocument> blockDocuments) {
        elasticsearchTemplate.putMapping(BlockDocument.class);
        if(blockDocuments.size() > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            log.info("【保存索引】：{}", JSON.toJSONString(blockDocumentRepository.saveAll(blockDocuments)));
        }
    }

    @Override
    public void delete(String id) {
        blockDocumentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        blockDocumentRepository.deleteAll();
    }

    @Override
    public BlockDocument getById(String id) {
        return blockDocumentRepository.findById(id).get();
    }

    @Override
    public List<BlockDocument> getAll() {
        List<BlockDocument> list = new ArrayList<>();
        blockDocumentRepository.findAll().forEach(list::add);
        return list;
    }


}
