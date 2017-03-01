package com.wzy.entity;

import java.util.Date;

/**
 * Created by wzy on 17-2-9.
 */
public class SuccessKilled {

    private long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    private Seckill seckill;


    public long getSeckillId() {
        return seckillId;
    }

    public void setSecKillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSecKill() {
        return seckill;
    }

    public void setSecKill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "secKillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", secKill=" + seckill +
                '}';
    }
}
