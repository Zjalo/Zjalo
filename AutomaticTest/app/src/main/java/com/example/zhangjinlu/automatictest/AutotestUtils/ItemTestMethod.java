package com.example.zhangjinlu.automatictest.AutotestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by zhangjinlu on 2016/2/4.
 */
public class ItemTestMethod {

    public long getSectorTotalSize() {

        double totalSecSize = 0.00;
        BufferedReader br = null;

        try {

            File totalNode = new File("/sys/bus/mmc/devices/mmc0:0001/block/mmcblk0/size");

            br = new BufferedReader(new FileReader(totalNode));

            String total = br.readLine();

            totalSecSize = Double.parseDouble(total);

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } finally {

            try {

                if(br != null){

                    br.close();

                }

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }

        }

        double totalMemory = totalSecSize/2.0/1024.0/1024.0/0.91;

        long totalMemorySize = Math.round(totalMemory); //四舍五入

        if (totalMemorySize >= 15 && totalMemorySize <= 16) {

            totalMemorySize = 16;

        }

        return totalMemorySize;
    }

    private static String getprop(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class );
            value = (String)(   get.invoke(c, key, "unknown" ));
        } catch (Exception e) {
        }
        return value;
    }

    public static Boolean logcat_test(){
        String loggerState = getprop("persist.service.logr.enable","");
        String adbdState = getprop("init.svc.adbd","");
        if(((loggerState != "" && "0".equals(loggerState)))
                && (adbdState != "" && "running".equals(adbdState))) {
            return  true;
        }else{
            return false;
        }
    }
}
