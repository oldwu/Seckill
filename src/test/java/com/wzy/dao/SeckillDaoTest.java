package com.wzy.dao;

import com.wzy.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wzy on 17-2-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        int updateCount = seckillDao.reduceNumber(1004L, new Date());
        System.out.println(updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1004;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.toString());
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill :seckills){
            System.out.println(seckill.toString());
        }
    }

}