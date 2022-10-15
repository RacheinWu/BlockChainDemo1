package com.rachein.blockchain.core.service.impl;

import com.rachein.blockchain.core.service.IBlockService;
import com.rachein.blockchain.entity.DB.Block;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/30
 * @Description
 */
@Slf4j
@WebListener
public class BlockQueue implements ServletContextListener {

    //存放用户的data的阻塞队列
    public static final BlockingDeque<Block> QUEUE = new LinkedBlockingDeque<>();
    //监听阻塞队列的线程数量：
    private static final int THREAD_COUNT = 30;

    public static IBlockService blockService;

    /**
     * 监听堵塞队列，异步执行任务
     */
    public static void listen() {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            //从队列中获取数据：
                            Block block = QUEUE.take();
                            //保存到数据库中
                            blockService.addBlock(block);
//                            log.info("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("区块异步保存失败！");
                    }
                }
            });
            thread.start();
        }
    }
}
