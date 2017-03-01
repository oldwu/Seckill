package com.wzy.service;

import com.wzy.dto.Exposer;
import com.wzy.dto.SeckillExecution;
import com.wzy.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wzy on 17-2-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml", "classpath:spring/spring-dao.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        for (Seckill seckill : seckillList){
            logger.info("seckill={}", seckill);
        }
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000;
        long phone = 1826061564L;
        String md5 = "dc73ef44009e8e1b6dfb30bf24812137";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
        System.out.println(seckillExecution);
    }

    @Test
    public void executeSeckillProduce() throws Exception {
        long seckillId = 1001;
        long phone = 18260631564L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){

            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProduce(seckillId, phone, md5);
            logger.info(seckillExecution.getStateInfo());
        }
    }

}