package zzx.jxc.util;

import java.util.Random;

public class DetailKeyUtil {
    /**
     * 生成随机数(唯一主键)
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        int i = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(i);
    }
}
