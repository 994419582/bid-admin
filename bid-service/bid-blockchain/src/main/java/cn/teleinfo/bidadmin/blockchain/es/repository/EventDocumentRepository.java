package cn.teleinfo.bidadmin.blockchain.es.repository;

import cn.teleinfo.bidadmin.blockchain.document.EventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author 史维君
 * @version 0.1
 */
@Component
public interface EventDocumentRepository extends ElasticsearchRepository<EventDocument,String> {
}
