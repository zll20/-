package com.yb.service.impl;

import com.yb.dao.WenkanDao;
import com.yb.entity.WenkanEntity;
import com.yb.service.WenkanService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;

@Service("wenkanService")
public class WenkanServiceImpl extends ServiceImpl<WenkanDao, WenkanEntity> implements WenkanService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WenkanEntity> wrapper = new QueryWrapper<>();
        if (params.containsKey("jibie") && params.get("jibie") != null && !params.get("jibie").toString().isEmpty()) {
            wrapper.eq("jibie", params.get("jibie"));
        }

        Page<WenkanEntity> page = baseMapper.selectPage(
            new Query<WenkanEntity>(params).getPage(),
            wrapper
        );
        return new PageUtils(page);
    }
}
