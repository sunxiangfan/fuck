package com.fuck.core.result;

import lombok.Data;

@Data
public class ResultVO {

    private int code;
    private String msg;
    private Object data;

    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO fail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(9999);
        resultVO.setMsg("fail");
        return resultVO;
    }

    public static ResultVO fail(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(9999);
        resultVO.setMsg(msg);
        return resultVO;
    }
}