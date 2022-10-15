package com.rachein.blockchain.entity.DB;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Block {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String hash;
    private String previousHash;
    private long timestamp;
    private String data;
    private Long checkTime;
    private Integer lastCheckStatus;
    private String type;
    private String digest;
//
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
//
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
