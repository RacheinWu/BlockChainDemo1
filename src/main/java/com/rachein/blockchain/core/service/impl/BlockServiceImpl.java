package com.rachein.blockchain.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.rachein.blockchain.Utils.BlockHashCalcUtil;
import com.rachein.blockchain.entity.DB.Block;
import com.rachein.blockchain.core.mapper.BlockMapper;
import com.rachein.blockchain.core.service.IBlockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.blockchain.redis.RedisService;
import com.rachein.blockchain.redis.myPrefixKey.BlockKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴远健
 * @since 2022-07-15
 */
@Service
@Slf4j
public class BlockServiceImpl extends ServiceImpl<BlockMapper, Block> implements IBlockService {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean addBlock(Block newBlock) {
        //获取区块链最后一个区块：
        Block last = redisService.get(BlockKey.getLastBlock, BlockKey.LAST_BLOCK, Block.class);
        //获取最后一个区块的hash，并将此赋予给newBlock
        newBlock.setPreviousHash( last.getHash());
        //获取时间戳，并将此赋予给newBlock
        newBlock.setTimestamp(System.currentTimeMillis());
        //为newBlock进行计算Hash，并且赋值给newBlock
        String hash = BlockHashCalcUtil.calculateHash(newBlock);
        newBlock.setHash(hash);
        log.info("保存到数据库中->" + JSON.toJSONString(newBlock));
        //插入到数据库中:
        save(newBlock);
        //将父区块从key-value缓存中出队:(当前区块覆盖到此标记上)
        redisService.set(BlockKey.getLastBlock, BlockKey.LAST_BLOCK, newBlock);
        return true;
    }
}
