package com.rachein.blockchain.Utils;

import com.rachein.blockchain.entity.DB.Block;
import com.rachein.blockchain.entity.DB.Chain;

import java.util.List;

public class ChainValidUtil {

    public static boolean isValidBlock(Chain c) {
        List<Block> chain = c.getChain();
//        Block head = chain.get(1);//0号不算，因为0号为头，初始化默认为引导者，1号开始才是真正的区块;
        //区块链的长度:
        int length = chain.size();
        //此节点和父节点进行双指针对比，其中有两种对比方式，只要其一不通过，那么整条链子后续就会作废：
        for (int i = 1; i < length; i++) {
            Block currentBlock = chain.get(i);
            Block lastBlock = chain.get(i - 1);
            //先计算本身的Hash是否合法：
            if (!BlockHashCalcUtil.calculateHash(currentBlock).equals(currentBlock.getHash())) {
                return false;
            }
            //与父节点的hash进行对比，检查本身所保存的父Hash是否一致：
            if (!currentBlock.getPreviousHash().equals(lastBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
}
