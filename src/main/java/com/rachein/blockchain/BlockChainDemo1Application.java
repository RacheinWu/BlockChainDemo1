package com.rachein.blockchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rachein.blockchain.core.mapper")
public class BlockChainDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(BlockChainDemo1Application.class, args);
    }

}
