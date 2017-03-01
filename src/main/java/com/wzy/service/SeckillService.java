package com.wzy.service;

import com.wzy.dto.Exposer;
import com.wzy.dto.SeckillExecution;
import com.wzy.entity.Seckill;
import com.wzy.exception.RepeatKillException;
import com.wzy.exception.SeckillCloseException;
import com.wzy.exception.SeckillException;

import java.util.List;

/**
 * Created by wzy on 17-2-10.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException;

    SeckillExecution executeSeckillProduce(long seckillId, long userPhone, String md5);


}
