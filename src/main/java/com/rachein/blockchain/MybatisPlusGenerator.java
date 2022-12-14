package com.rachein.blockchain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;


/**
 * @author 计算机系 ITAEM 吴远健
 * @Description
 * @date 2022/6/24 20:37
 */
public class MybatisPlusGenerator {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/h1?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "wuyuanjian0";


    public static void main(String[] args) {

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("吴远健").outputDir("C:/Users/Rache/IdeaProjects/BlockChainDemo1/src/main/java")
                            .enableSwagger()
                            .disableOpenDir();
                })
                .strategyConfig(builder -> {
                    builder.addInclude("block"); // 设置需要生成的表名
//                    builder.addInclude("salary_release_mid_archives"); // 设置需要生成的表名
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .idType(IdType.AUTO)
//                            .addSuperEntityColumns("id", "gmtCreate", "gmtModified")
//                            .addTableFills(new Column("gmt_create", FieldFill.INSERT))
//                            .addTableFills(new Property("gmtModified", FieldFill.INSERT_UPDATE))
                    ;
                })
                .packageConfig(builder -> {
                    builder.parent("com.rachein.blockchain")
                            .service("core.service")
                            .controller("core.controller")
                            .mapper("core.mapper")
                            .serviceImpl("core.service.impl")
                            .entity("entity.DB");
                })
                .strategyConfig(builder -> {
                    builder.controllerBuilder()
                            .enableRestStyle();
                })
                .execute();
    }

}
