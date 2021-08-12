package com.young.seata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.young.seata.entity.Commodity;
import com.young.seata.feign.OrderServiceFeign;
import com.young.seata.mapper.CommodityMapper;
import com.young.seata.service.ICommodityService;
import io.seata.common.XID;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author young
 * @since 2021-01-16
 */
@Slf4j
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Autowired
    OrderServiceFeign orderServiceFeign;

    @Autowired
    CommodityMapper commodityMapper;

    @Override
//    @GlobalTransactional
    @Transactional
    public String addCommodity(Commodity commodity) throws Exception {
        commodity.setCreateTime(System.currentTimeMillis());
        commodity.setUpdateTime(commodity.getCreateTime());
        String xid = GlobalTransactionContext.getCurrentOrCreate().getXid();
        log.info("seata-commodity GlobalTransactional XID :{}",xid);
        // feign
        orderServiceFeign.save();
        // save
        commodityMapper.insert(commodity);
        log.info("class: CommodityServiceImpl , method : addCommodity , msg: run is ....");
        return "success";
    }
}
