package com.rachein.blockchain.entity.DB;

import lombok.Data;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/30
 * @Description
 */
@Data
public class BlockCalcDTO {
    private String data;
    private String timestamp;
    private String previousHash;
}
