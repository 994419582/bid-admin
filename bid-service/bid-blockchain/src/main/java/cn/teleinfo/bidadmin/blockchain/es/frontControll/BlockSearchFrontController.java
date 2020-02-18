package cn.teleinfo.bidadmin.blockchain.es.frontControll;

import cn.teleinfo.bidadmin.blockchain.document.BlockDocument;
import cn.teleinfo.bidadmin.blockchain.es.page.Page;
import cn.teleinfo.bidadmin.blockchain.es.service.BlockSearchService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * elasticsearch 搜索
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/front/block/")
@Api(value = "区块查询前端", tags = "区块查询前端接口")
public class BlockSearchFrontController {

    private BlockSearchService blockSearchService;


    /**
     * 根据ID获取
     * @return
     */
    @GetMapping("get/{id}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "id")
    public BlockDocument getById(@PathVariable String id){
        return blockSearchService.getById(id);
    }

    /**
     * 根据获取全部
     * @return
     */
    @GetMapping("get_all")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取所有数据", notes = "获取所有数据")
    public List<BlockDocument> getAll(){
        return blockSearchService.getAll();
    }

    /**
     * 搜索
     * @param keyword
     * @return
     */
    @GetMapping("query/{keyword}")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "根据关键字排序", notes = "根据关键字排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "keyword", paramType = "query", dataType = "string"),
    })
    public List<BlockDocument> query(@PathVariable String keyword){
        return blockSearchService.query(keyword, BlockDocument.class);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit?keyword=无印良品荣耀&indexName=orders&fields=productName,productDesc
     * @param keyword   关键字
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @GetMapping("query_hit")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "获取高亮查询结果", notes = "获取高亮查询结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fields", value = "搜索字段名称，多个以“，”分割", paramType = "query", dataType = "string"),
    })
    public List<Map<String,Object>> queryHit(@RequestParam String keyword, @RequestParam String fields){
        String[] fieldNames = {};
        if(fields.contains(",")) fieldNames = fields.split(",");
        else fieldNames[0] = fields;
        return blockSearchService.queryHit(keyword,"block",fieldNames);
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
    @GetMapping("query_hit_page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "获取分页查询", notes = "获取分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fields", value = "搜索字段名称，多个以“，”分割", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", paramType = "query", dataType = "int"),
    })
    public Page<Map<String,Object>> queryHitByPage(@RequestParam int pageNo, @RequestParam int pageSize
                                                    , @RequestParam String keyword, @RequestParam String fields){
        String[] fieldNames = {};
        if (!StringUtil.isEmpty(fields)) {
            fieldNames = fields.split(",");
//            if (fields.contains(",")) fieldNames = fields.split(",");
//            else fieldNames[0] = fields;
        }
        return blockSearchService.queryHitByPage(pageNo,pageSize,keyword,"block",fieldNames);
    }
}
