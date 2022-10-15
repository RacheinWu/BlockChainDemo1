package com.rachein.blockchain.entity.DB;

import lombok.Data;

import java.util.List;

@Data
public class Chain {
    private List<Block> chain;

    /**
     * 添加区块
     * @param newBlock
     * @return 是否添加成功
     */
    public boolean addBlock(Block newBlock) {
        //
        try {
            /**
             * 问题
             * 1。 如果同时添加节点怎么办？
             */

            //获取链上最后一个区块：
            Block lastBlock = chain.get(chain.size() - 1);
            //获取区块链最后一个区块的hash：
            String previousHash = lastBlock.getHash();
            newBlock.setPreviousHash(previousHash);//设置父节点的Hash
//            newBlock.setHash(newBlock.calculateHash());

            //进行添加到链上:
            chain.add(newBlock);
            //添加到数据库中；


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
