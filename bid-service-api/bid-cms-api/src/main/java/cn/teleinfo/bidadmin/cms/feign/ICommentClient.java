package cn.teleinfo.bidadmin.cms.feign;

import cn.teleinfo.bidadmin.cms.entity.Comment;
import cn.teleinfo.bidadmin.cms.vo.CommentVO;
import cn.teleinfo.bidadmin.common.constant.AppConstant;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
        value = AppConstant.APPLICATION_CMS_NAME,
        fallback = ICommentClientFallback.class
)
public interface ICommentClient {

    String COMMENT_PREFIX = "/front/cms/comment";

    /**
     * 获取评论详情
     *
     * @param id    字典编号
     * @return
     */
    @GetMapping(COMMENT_PREFIX + "/detail")
    R<CommentVO> getDetail(@RequestParam("id") String id);

    /**
     * 获取文章列表
     *
     * @param comment 字典编号
     * @return
     */
    @GetMapping(COMMENT_PREFIX + "/list")
    R<IPage<CommentVO>> list(@ApiIgnore @RequestParam Map<String, Object> comment, Query query);

    @PostMapping(COMMENT_PREFIX + "/submit")
    R submit(HttpServletRequest request, Comment comment);

    @PostMapping(COMMENT_PREFIX+"/remove")
    R remove(HttpServletRequest request,Comment comment);
}
