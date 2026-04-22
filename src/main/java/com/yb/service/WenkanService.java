package com.yb.service;

import com.yb.entity.WenkanEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import com.yb.utils.PageUtils;

public interface WenkanService extends IService<WenkanEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
