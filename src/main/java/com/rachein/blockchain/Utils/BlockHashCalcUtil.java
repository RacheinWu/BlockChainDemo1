package com.rachein.blockchain.Utils;

import com.rachein.blockchain.entity.DB.Block;
import com.rachein.blockchain.entity.DB.BlockCalcDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class BlockHashCalcUtil {

    public static String calculateHash(Block block) {
        //先将对象转为json字符串：
        BlockCalcDTO blockCalcDTO = new BlockCalcDTO();
        BeanUtils.copyProperties(block, blockCalcDTO);
        String str = JSONUtil.beanToString(blockCalcDTO);
        log.info("当前对象的JSON为：" + str);
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        log.info("进行Hash计算后，获得的值为：" + encodeStr);
        return encodeStr;
    }


    private static String byte2Hex(byte[] bytes) {
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

}
