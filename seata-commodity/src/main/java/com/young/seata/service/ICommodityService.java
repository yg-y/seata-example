package com.young.seata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.young.seata.entity.Commodity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author young
 * @since 2021-01-16
 */
public interface ICommodityService extends IService<Commodity> {

    String addCommodity(Commodity commodity);
}
