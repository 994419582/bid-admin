package cn.teleinfo.bidadmin.blockchain.es.repository;

import cn.teleinfo.bidadmin.blockchain.document.TranscationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author 史维君
 * @version 0.1
 */
@Component
public interface TranscationDocumentRepository extends ElasticsearchRepository<TranscationDocument,String> {
}
