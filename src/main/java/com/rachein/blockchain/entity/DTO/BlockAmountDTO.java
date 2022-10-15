package com.rachein.blockchain.entity.DTO;

import lombok.Data;

import java.util.Date;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/1
 * @Description
 */
@Data
public class BlockAmountDTO {
    private String type;
    private Date time;
    private Long count;
}
