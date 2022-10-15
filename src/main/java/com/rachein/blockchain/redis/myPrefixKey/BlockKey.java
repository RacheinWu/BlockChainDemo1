package com.rachein.blockchain.redis.myPrefixKey;


import com.rachein.blockchain.redis.BasePrefix;

/**
 * 区块
 */
public class BlockKey extends BasePrefix {
    public final static String LAST_BLOCK = "last-block";
    public final static String HEAD_BLOCK = "head-block";
    public final static String BLOCKCHAIN_PREFIX = "last-block";
    public static final int TOKEN_EXPIRE = 0;//默认两天

    /**
     * 防止被外面实例化
     * @param expireSeconds
     * @param prefix
     */
    private BlockKey(int expireSeconds, String prefix) {super(expireSeconds,prefix);}
    /**
     * 需要缓存的字段：
     */
    public static BlockKey getLastBlock = new BlockKey(TOKEN_EXPIRE,"block");
    public static BlockKey getHeadBlock = new BlockKey(TOKEN_EXPIRE,"head");
    public static BlockKey getBlockChain = new BlockKey(TOKEN_EXPIRE,"block-chain");

//    public static BlockKey getById = new BlockKey(TOKEN_EXPIRE,"");

}
