package com.rachein.blockchain.core.service;

import com.rachein.blockchain.entity.DB.Block;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴远健
 * @since 2022-07-15
 */
public interface IBlockService extends IService<Block> {

    boolean addBlock(Block blockRo);
}
