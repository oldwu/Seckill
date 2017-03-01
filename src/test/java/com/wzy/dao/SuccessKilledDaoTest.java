package com.wzy.dao;

import com.wzy.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by wzy on 17-2-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")

public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int insertCount = successKilledDao.insertSuccessKilled(1001L, 18260631564L);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void queryByIdWithSecKill() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(1001L, 18260631564L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSecKill());

    }

}