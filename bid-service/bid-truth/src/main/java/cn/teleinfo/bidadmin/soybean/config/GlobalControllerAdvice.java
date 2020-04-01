package cn.teleinfo.bidadmin.soybean.config;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(ApiException.class)
    public R apiException(ApiException e) {
        return R.fail(e.getMessage());
    }
}
