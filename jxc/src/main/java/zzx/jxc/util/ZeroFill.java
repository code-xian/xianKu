package zzx.jxc.util;

public class ZeroFill {
    public static String buOrderCode(long num,int leve){
        String result = ""+num;
        int size = result.length();
        if(size == leve){
            return result;
        }else{
            int i = leve - size;
            for(;i > 0; i--){
                result ="0" + result;
            }
        }
        return result;
    }
}
