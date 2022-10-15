package com.rachein.blockchain.result;

import lombok.Data;

/**
 * @author 计算机系 ITAEM 吴远健
 * @date 2022/2/27 20:33
 */
@Data
public class CodeMsg {

    private int code;
    private String msg;

    //通用：
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg BIND_ERROR = new CodeMsg(50001, "参数错误!");
    public static CodeMsg ERROR_SERVER = new CodeMsg(50002, "服务器错误!");
    public static CodeMsg NOT_FOUND = new CodeMsg(50003, "查询无结果了...");
    public static CodeMsg UPDATE_ERROR = new CodeMsg(50004, "更新数据失败...");

    //上传：

    //user: 5002xx:
    public static CodeMsg USER_LOGIN_ERROR = new CodeMsg(500201, "账号或者密码错误...");

    //archives: 5003xx:


    public static CodeMsg HASH_FAILED = new CodeMsg(500201, "区块无效！肯能已经被人篡改...");
    public static CodeMsg HASH_CHAIN_BREAK = new CodeMsg(500202, "计算中断！链条节点失效！");




    public CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回带参数的错误码
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
