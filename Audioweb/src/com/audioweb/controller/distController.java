package com.audioweb.controller;

import com.audioweb.util.Tools;
import gnu.io.SerialPort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.audioweb.util.KMP;

public class distController extends Tools{

    public static String template2() throws Exception {
        // 打开串口
        SerialPort serialPort = portParameterOpen("COM4", 9600);
        // 要发送的数据
        String dataSend = "";

        int i=1;
        for(int j=0;j<1;j++) {
            // 发送数据到单片机
            byte []datByte = dataSend.getBytes();
            uartSendDatatoSerialPort(serialPort, datByte);
            System.out.println("-------------------------------------------------------");
            //System.out.println((i++) + ". 发送到串口的数据：" + dataSend);

            // 休眠300ms，等待单片机反应
            Thread.sleep(500);

            // 从单片机接收到的数据
            byte[] dat = uartReceiveDatafromSingleChipMachine(serialPort);
            if(dat != null && dat.length > 0) {
                String dataReceive = new String(dat, "GB2312");
                //String dataReceive = new String(dat, "UTF-8");
                System.out.println("当前距离为" + dataReceive);
                KMP kmp = new KMP();
                serialPort.close();
                //int temperature_local = kmp.kmpMatch(dataReceive,"temperature");
                //int humidity_local = kmp.kmpMatch(dataReceive,"humidity");
                return dataReceive.substring(10,16)+"CM";
            } else {
                serialPort.close();
                System.out.println("接收到的数据为空！");
            }
        }
        return ("未检测到距离");
    }

}
