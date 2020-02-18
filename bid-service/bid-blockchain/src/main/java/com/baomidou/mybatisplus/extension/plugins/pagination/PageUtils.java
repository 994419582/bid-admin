package com.baomidou.mybatisplus.extension.plugins.pagination;

import org.springblade.core.mp.support.Query;
import org.springblade.core.mp.support.SqlKeyword;
import org.springblade.core.tool.utils.Func;

public class PageUtils {
    public static <T> Page<T> getPage(Query query) {
        Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
        page.setAsc(Func.toStrArray(SqlKeyword.filter(query.getAscs())));
        page.setDesc(Func.toStrArray(SqlKeyword.filter(query.getDescs())));
        return page;
    }
}
