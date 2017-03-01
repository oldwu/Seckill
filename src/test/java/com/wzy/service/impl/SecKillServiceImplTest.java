package com.wzy.service.impl;

import com.wzy.dto.Exposer;
import com.wzy.dto.SeckillExecution;
import com.wzy.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by wzy on 17-2-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml", "classpath:spring/spring-dao.xml"})
public class SecKillServiceImplTest {

    @Autowired
    SeckillService seckillService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void executeSeckillProduce() throws Exception {
        long seckillId = 1004;
        long phone = 18260631564L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){

            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProduce(seckillId, phone, md5);
            logger.info(seckillExecution.getStateInfo());
        }
    }

}