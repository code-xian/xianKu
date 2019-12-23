package zzx.jxc.util;

import zzx.jxc.VO.ResultVO;

public class ResultVOUtil {
    public static ResultVO success(Object object,String msg) {
        ResultVO<Object> objectResultVO = new ResultVO<>();
        objectResultVO.setData(object);
        objectResultVO.setCode(0);
        objectResultVO.setMsg(msg);
        return objectResultVO;
    }

    public static ResultVO success(String msg) {
        return success(null, msg);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO objectResultVO = new ResultVO();
        objectResultVO.setMsg(msg);
        objectResultVO.setCode(code);
        return objectResultVO;
    }
}
