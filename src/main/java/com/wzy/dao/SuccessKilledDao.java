package com.wzy.dao;

import com.wzy.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wzy on 17-2-9.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /**
     * 根据ID
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSecKill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


}
