package zzx.jxc.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ddbhUtil {

    public static String xsdd(Integer number) {
    Date date=new Date();
    DateFormat format=new SimpleDateFormat("yyyyMMdd");
    String timeStr=format.format(date);
        String xsdd = "";
        int count = number;   //需要通过当天时间去查 库存表中有多少条数据
        String strcount = ""; //这里是把查出来的int值转成string用于判断count的长度
        //开始拼接
        if(count <= 9){
            int aa = count + 1;
            String straa = aa + "";//int转string
            strcount += "00" + straa;
        }
        if( count >=10 && count <= 99 ){
            int aa = count + 1;
            String straa = aa + "";//int转string
            strcount += "0" + straa;
        }
        if(count >=100 && count <= 999){
            int aa = count + 1;
            String straa = aa + "";//int转string
            strcount += straa;
        }
//        if (strcount.length() == 1) {
//            int aa = count + 1; //这个是按顺序递增1
//            if(aa>=10){
//                String straa = aa + "";//int转string
//                strcount += "0" + straa;
//            }else{
//                System.out.println(aa);
//                String straa = aa + "";//int转string
//                strcount += "00" + straa;
//            }
//        }
//        if (strcount.length() == 2) {
//            int bb = count + 1; //这个是按顺序递增1
//            if(bb>=100){
//                String strbb = bb + "";//int转string
//                strcount += strbb;
//            }else{
//                String strbb = bb + "";//int转string
//                strcount += "0" + strbb;
//            }
//        }
//        if (strcount.length() == 3) {
//            int cc = count + 1; //这个是按顺序递增1
//            if(cc>999){
//                return "订单已满";
//            }else{
//                String strcc = cc + "";//int转string
//                strcount += strcc;
//            }
//        }
        xsdd = "XSDD"+timeStr+strcount ;
        return xsdd;
    }

    public static Integer getCount() {
        return  null;
    }
}
