package cn.teleinfo.bidadmin.soybean.feign;

import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

@Component
public class WxSubscribeClientFallback implements IWxSubscribeClient {

    @Override
    public R send(String openId) {
        return R.fail("execute send Task任务执行失败");
    }

    @Override
    public R sendBatch(String openIds) {
        return R.fail("execute sendBatch Task任务执行失败");
    }

    @Override
    public R sendGroup(String groupId) {
        return R.fail("execute sendGroup Task任务执行失败");
    }
}
