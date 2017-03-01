package com.wzy.dao;

import com.wzy.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wzy on 17-2-9.
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 通过ID查询秒杀商品
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 批量查询
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProduce(Map<String ,Object> paramMap);
}
