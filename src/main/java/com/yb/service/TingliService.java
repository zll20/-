package com.yb.service;

import com.yb.entity.TingliEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import com.yb.utils.PageUtils;

public interface TingliService extends IService<TingliEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
