package com.wzy.service.impl;

import com.wzy.dao.SeckillDao;
import com.wzy.dao.SuccessKilledDao;
import com.wzy.dao.cache.RedisDao;
import com.wzy.dto.Exposer;
import com.wzy.dto.SeckillExecution;
import com.wzy.entity.Seckill;
import com.wzy.entity.SuccessKilled;
import com.wzy.enums.SeckillStateEnum;
import com.wzy.exception.RepeatKillException;
import com.wzy.exception.SeckillCloseException;
import com.wzy.exception.SeckillException;
import com.wzy.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzy on 17-2-10.
 */
@Service
public class SecKillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    //md5盐值，用于混淆
    private final String slat = "sfdwerj842u32900j0jcv0-s9dfk";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 1000);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1. 开发团队达成一致约定，明确标注事务方法的编程风格
     * 2. 保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求
     * 3. 不是所有的方法都需要事务
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        Date nowTime = new Date();
        try {
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新到记录
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);

                }
            }

        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill error:" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProduce(long seckillId, long userPhone, String md5) {

        if (md5 == null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId, SeckillStateEnum.DATA_REWRITE);
        }

        Date killTime = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);

        try {
            seckillDao.killByProduce(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1){
                SuccessKilled sk = successKilledDao.
                        queryByIdWithSecKill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS);
            }else{
                return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
