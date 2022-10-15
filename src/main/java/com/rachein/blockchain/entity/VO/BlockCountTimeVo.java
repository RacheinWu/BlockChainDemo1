package com.rachein.blockchain.entity.VO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/31
 * @Description
 */
@Data
public class BlockCountTimeVo {
    private LocalDateTime tg;
    private Long count;

}
