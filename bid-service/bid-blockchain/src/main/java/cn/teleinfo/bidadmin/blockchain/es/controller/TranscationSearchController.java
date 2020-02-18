package cn.teleinfo.bidadmin.blockchain.es.controller;

import cn.teleinfo.bidadmin.blockchain.es.page.Page;
import cn.teleinfo.bidadmin.blockchain.document.TranscationDocument;
import cn.teleinfo.bidadmin.blockchain.es.service.TranscationSearchService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * elasticsearch 搜索
 * @version 0.1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/transcations_search")
@Api(value = "", tags = "交易信息es接口")
public class TranscationSearchController {

    private TranscationSearchService TranscationSearchService;

    /**
     * 新增 / 修改索引
     * @return
     */
    @RequestMapping("save")
    public String add(@RequestBody TranscationDocument transcationDocument) {
        TranscationSearchService.save(transcationDocument);
        return "success";
    }

    /**
     * 删除索引
     * @return
     */
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        TranscationSearchService.delete(id);
        return "success";
    }
    /**
     * 清空索引
     * @return
     */
    @RequestMapping("delete_all")
    public String deleteAll(@PathVariable String id) {
        TranscationSearchService.deleteAll();
        return "success";
    }

    /**
     * 根据ID获取
     * @return
     */
    @RequestMapping("get/{id}")
    public TranscationDocument getById(@PathVariable String id){
        return TranscationSearchService.getById(id);
    }

    /**
     * 根据获取全部
     * @return
     */
    @RequestMapping("get_all")
    public List<TranscationDocument> getAll(){
        return TranscationSearchService.getAll();
    }

    /**
     * 搜索
     * @param keyword
     * @return
     */
    @RequestMapping("query/{keyword}")
    public List<TranscationDocument> query(@PathVariable String keyword){
        return TranscationSearchService.query(keyword, TranscationDocument.class);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit?keyword=无印良品荣耀&indexName=orders&fields=productName,productDesc
     * @param keyword   关键字
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @RequestMapping("query_hit")
    public List<Map<String,Object>> queryHit(@RequestParam String keyword, @RequestParam String fields){
        String[] fieldNames = {};
        if(fields.contains(",")) fieldNames = fields.split(",");
        else fieldNames[0] = fields;
        return TranscationSearchService.queryHit(keyword,"transactions",fieldNames);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit_page?keyword=无印良品荣耀&indexName=orders&fields=productName,productDesc&pageNo=1&pageSize=1
     * @param pageNo    当前页
     * @param pageSize  每页显示的数据条数
     * @param keyword   关键字
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @RequestMapping("query_hit_page")
    public Page<Map<String,Object>> queryHitByPage(@RequestParam int pageNo, @RequestParam int pageSize
                                                    , @RequestParam String keyword,  @RequestParam String fields){
        String[] fieldNames = {};
        if(fields.contains(",")) fieldNames = fields.split(",");
        else fieldNames[0] = fields;
        return TranscationSearchService.queryHitByPage(pageNo,pageSize,keyword,"transactions",fieldNames);
    }

    /**
     * 删除索引库
     * @param indexName
     * @return
     */
    @RequestMapping("delete_index/{indexName}")
    public String deleteIndex(@PathVariable String indexName){
        TranscationSearchService.deleteIndex(indexName);
        return "success";
    }
}
