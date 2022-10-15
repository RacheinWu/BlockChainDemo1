package com.rachein.blockchain.core.mapper;

import com.rachein.blockchain.entity.DB.Block;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rachein.blockchain.entity.DTO.BlockAmountDTO;
import com.rachein.blockchain.entity.VO.BlockCountTimeVo;
import com.rachein.blockchain.entity.VO.BlockCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴远健
 * @since 2022-07-15
 */
@Mapper
public interface BlockMapper extends BaseMapper<Block> {

    @Select("select type,count(*) as count from block group by type;")
    List<BlockCountVo> selectCountGroupByType();

    @Select("select " +
            "   type," +
            "   DATE_FORMAT(gmt_create, '%Y-%m-%d %H:00:00') time,\n" +
            "    count(0) as count\n" +
            "from block\n" +
            "where gmt_create between '2020-12-01 00:00:00'\n" +
            "AND '2022-12-01 12:00:00'\n" +
            "GROUP BY\n" +
            "time,type;")
    List<BlockAmountDTO> selectCountGroupByTime();

}
