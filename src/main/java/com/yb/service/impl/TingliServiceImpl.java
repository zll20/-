package com.yb.service.impl;

import com.yb.dao.TingliDao;
import com.yb.entity.TingliEntity;
import com.yb.service.TingliService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;

@Service("tingliService")
public class TingliServiceImpl extends ServiceImpl<TingliDao, TingliEntity> implements TingliService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<TingliEntity> wrapper = new QueryWrapper<>();
        // 如果前端传了级别参数，则进行等值查询
        if (params.containsKey("jibie") && params.get("jibie") != null && !params.get("jibie").toString().isEmpty()) {
            wrapper.eq("jibie", params.get("jibie"));
        }
        
        Page<TingliEntity> page = baseMapper.selectPage(
            new Query<TingliEntity>(params).getPage(),
            wrapper
        );
        return new PageUtils(page);
    }
}
