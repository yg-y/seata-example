package com.young.seata.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.young.seata.commodity.entity.Commodity;
import com.young.seata.commodity.mapper.CommodityMapper;
import com.young.seata.commodity.service.ICommodityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author young
 * @since 2021-01-16
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

}
