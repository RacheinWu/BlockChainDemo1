package com.rachein.blockchain;

import com.rachein.blockchain.Utils.JSONUtil;
import com.rachein.blockchain.core.mapper.BlockMapper;
import com.rachein.blockchain.core.service.impl.BlockServiceImpl;
import com.rachein.blockchain.entity.DB.Block;
import com.rachein.blockchain.entity.VO.BlockCountVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
@Slf4j
class BlockChainDemo1ApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {
        Block block = new Block();
        block.setHash("d5fcbd4afe1bcb86c5b53302801765231dc1eefed12a8c8a3b42cf4f75f1f203");
        block.setPreviousHash("05fcbd4afe1bcb86c5b53302801765231dc1eefed12a8c8a3b42cf4f75f1f203");
        block.setData("{\"code\":400501,\"message\":\"no found ad\",\"data\":null}");
        block.setTimestamp(System.currentTimeMillis());
        String str = JSONUtil.beanToString(block);
        log.info(str);
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        log.info(encodeStr);
    }


    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    @Autowired
    private BlockServiceImpl blockService;
    @Autowired
    private BlockMapper blockMapper;

    @Test
    public void h3x() {
        List<BlockCountVo> blockCountVos = blockMapper.selectCountGroupByType();
        System.out.println(blockCountVos);
//        System.out.println(count    );
    }

}
