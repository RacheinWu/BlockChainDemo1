package com.rachein.blockchain.config;

import com.rachein.blockchain.Utils.BlockHashCalcUtil;
import com.rachein.blockchain.core.service.IBlockService;
import com.rachein.blockchain.core.service.impl.BlockQueue;
import com.rachein.blockchain.entity.DB.Block;
import com.rachein.blockchain.redis.RedisService;
import com.rachein.blockchain.redis.myPrefixKey.BlockKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/18
 * @Description
 */
@Configuration
@Slf4j
public class initialConfig {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IBlockService blockService;

    @Bean
    public void redisBlockChainInti() {
        //检查数据库中是否保存有区块：
        Long count = blockService.lambdaQuery().count();
        if (count == 0) {
            //创建新的区块链：
            //创建新的头节点：
            Block head = new Block();
            head.setData("");
            head.setTimestamp(System.currentTimeMillis());
            head.setPreviousHash("previous");
            head.setHash(BlockHashCalcUtil.calculateHash(head));
            //将头节点保存到redis中
            log.info("刷新区块链");
            redisService.set(BlockKey.getHeadBlock, BlockKey.HEAD_BLOCK, head);
            //同时将最后一个节点的身份交付给head:
            redisService.set(BlockKey.getLastBlock, BlockKey.LAST_BLOCK, head);
        }
        else {
//            //如果此时区块链正在计算：
//            //获取最后一个区块：
//            blockService.lambdaQuery().
        }


//        //从redis中获取最后一个区块,如果有的话,就continue
//        Block lastBlock = null;
//        lastBlock = redisService.get(BlockKey.getLastBlock, BlockKey.LAST_BLOCK, Block.class);
//        if (Objects.isNull(lastBlock)) {
//            //不存在redis中,那就创建一个空的区块:
//            lastBlock = new Block();
//            lastBlock.setTimestamp(System.currentTimeMillis());
//            lastBlock.setData("");
//            lastBlock.setHash(BlockHashCalcUtil.calculateHash(lastBlock));
//            //保存在redis中:
//            redisService.set(BlockKey.getLastBlock, BlockKey.LAST_BLOCK, lastBlock);
//            log.info(">>>>>> 刷新区块链");
//        }
        //激活区块队列缓存：
        BlockQueue.blockService = blockService;
        BlockQueue.listen();
        log.info(">>>>> 区块监听队列已激活！");

    }

}
