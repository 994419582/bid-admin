package cn.teleinfo.bidadmin.soybean.feign;

import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "bid-soybean",
        fallback = WxSubscribeClientFallback.class
)
public interface IWxSubscribeClient {
    String API_PREFIX = "/wx/subscribe/";

    @GetMapping({"/wx/subscribe/send"})
    R send(@RequestParam(defaultValue = "") String openId);

    @GetMapping({"/wx/subscribe/send/batch"})
    R sendBatch(@RequestParam(defaultValue = "")String openIds);

    @GetMapping({"/wx/subscribe/send/group"})
    R sendGroup(@RequestParam(defaultValue = "")String groupId);

}
