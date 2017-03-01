CREATE PROCEDURE `execute_seckill`(IN  `v_seckill_id` BIGINT(20), IN `v_phone` BIGINT(20), IN `v_kill_time` TIMESTAMP,
                                   OUT `r_result`     INT(11))
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO success_killed
    (seckill_id, user_phone, create_time)
    VALUES (v_seckill_id, v_phone, v_kill_time);
    SELECT row_count()
    INTO insert_count;
    IF (insert_count = 0)
    THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < 0)
      THEN
        ROLLBACK;
        SET r_result = -2;
    ELSE
      update seckill.seckill set number = number - 1 where seckill_id = v_seckill_id and start_time <= v_kill_time and end_time >= v_kill_time and number > 0;
      SELECT ROW_COUNT() into insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        set r_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        set r_result = -2;
      ELSE
        COMMIT ;
        set r_result = 1;
      END IF;
    END IF;
  END