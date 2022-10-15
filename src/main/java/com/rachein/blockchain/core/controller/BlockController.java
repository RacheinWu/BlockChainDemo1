package com.rachein.blockchain.core.controller;

import com.alibaba.fastjson.JSON;
import com.rachein.blockchain.Utils.BlockHashCalcUtil;
import com.rachein.blockchain.core.mapper.BlockMapper;
import com.rachein.blockchain.core.service.IBlockService;
import com.rachein.blockchain.core.service.impl.BlockQueue;
import com.rachein.blockchain.entity.DB.Block;
import com.rachein.blockchain.entity.DTO.BlockAmountDTO;
import com.rachein.blockchain.entity.VO.BlockCountVo;
import com.rachein.blockchain.exception.GlobalException;
import com.rachein.blockchain.redis.RedisService;
import com.rachein.blockchain.redis.myPrefixKey.BlockKey;
import com.rachein.blockchain.result.CodeMsg;
import com.rachein.blockchain.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/block")
@Slf4j
public class BlockController {

    @Autowired
    private IBlockService blockService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BlockMapper blockMapper;

    @PostMapping("/insert")
    public void add(@RequestBody Block blockRo) {
        System.out.println(blockRo);
        blockRo.setDigest(blockRo.getData().substring(0,12) + "...");
        BlockQueue.QUEUE.push(blockRo);
//        blockService.addBlock(blockRo);
//        return Result.success("block-add-success");
    }


    @GetMapping("/all")
    public Result<List<Block>> list() {
        List<Block> list = blockService.lambdaQuery()
                .select(Block::getHash, Block::getPreviousHash, Block::getTimestamp, Block::getId, Block::getType)
                .list();
        return Result.success("success", list);
    }

    @ApiOperation("获取某区块的内容")
    @GetMapping("/{index}")
    public Result<Block> getOne(@PathVariable Long index) {
        Block one = blockService.lambdaQuery().eq(Block::getId, index)
                .one();
        if (Objects.isNull(one)) {
            throw new GlobalException(CodeMsg.NOT_FOUND);
        }
        return Result.success("success", one);
    }

    @ApiOperation("计算区块的hash")
    @GetMapping("calc/{hash}")
    public Result<Object> check(@PathVariable String hash) {
        Block one = blockService.lambdaQuery().eq(Block::getHash, hash)
                .one();
        log.info(JSON.toJSONString(one));
        String hashC = BlockHashCalcUtil.calculateHash(one);
        //获取当前时间戳：
        long time = System.currentTimeMillis();
        blockService.lambdaUpdate().eq(Block::getHash, one.getHash()).set(Block::getCheckTime, time).update();
        System.out.println(hashC);
        if (!hashC.equals(one.getHash())) {
            blockService.lambdaUpdate().eq(Block::getHash, one.getHash()).set(Block::getLastCheckStatus, 0).update();
            throw new GlobalException(CodeMsg.HASH_FAILED);
        }
        return Result.success("区块有效！");
    }

    @ApiOperation("计算整条链子的hash是否正确")
    @GetMapping("/check")
    public Result<Long> calc() {
        Map<String, Block> map = new HashMap<>();
        blockService.lambdaQuery()
                .select(Block::getHash, Block::getPreviousHash, Block::getTimestamp, Block::getId, Block::getData)
                .list()
                .stream()
                .forEach(t -> {
                    map.put(t.getPreviousHash(), t);
                });
        long size = map.size();
        long i = 0;
        //从redis中获取头节点：
        Block head = redisService.get(BlockKey.getHeadBlock, BlockKey.HEAD_BLOCK, Block.class);
        //从头节点开始：
        String previousHash = head.getHash();
        while (true) {
            //从哈希表中获取对应的previousHash
            Block block = map.get(previousHash);
            if (Objects.isNull(block)) {
                break;
            }
            log.info("正在测试： 》》》》》》"+block.toString());
            //计算hash：
            String hash = BlockHashCalcUtil.calculateHash(block);
            //校验hash
            if (!hash.equals(block.getHash())) {
                break;
            }
            //获取当前节点的hash 作为previousHash：
            previousHash = hash;
            i++;
        }
        if (size == i) {
            return Result.success("success");
        }
        return Result.error(CodeMsg.HASH_CHAIN_BREAK, i);
    }

    @ApiOperation("获取每一个时间间隔内的区块数量")
    @GetMapping("/count/c")
    public Result<Map<String, Object>> getAmountByDay() {
        Map<String, Object> map = new HashMap<>();
        List<String> typeList = blockService.lambdaQuery().select(Block::getType).groupBy(Block::getType).list().stream().map(Block::getType).collect(Collectors.toList());

        Map<Date, Map<String, Long>> ans = new HashMap<>();
//        System.out.println(blockMapper.selectCountGroupByTime());
        Map<Date, List<BlockAmountDTO>> map_type_dateCount = blockMapper.selectCountGroupByTime().stream().collect(Collectors.groupingBy(BlockAmountDTO::getTime));
        map_type_dateCount.forEach((k,v) -> {
            Map<String, Long> x = new HashMap<>();
            v.stream().forEach(t -> {
                x.put(t.getType(), t.getCount());
            });
            ans.put(k, x);
        });
        map.put("type_list", typeList);
        map.put("details", ans);
        return Result.success("success", map);
    }

    @ApiOperation("获取每一种类型的数据数量,以及总数")
    @GetMapping("/count/details")
    public Result<Map<String, Object>> getAmountByType() {
        Map<String, Object> map = new HashMap<>();
        List<BlockCountVo> blockCountVos = blockMapper.selectCountGroupByType();
        //获取总数：
        long totalCount = blockService.count();
        map.put("details", blockCountVos);
        map.put("totalCount", totalCount);
        return Result.success("success", map);
    }


}
