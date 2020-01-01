package com.audioweb.util;

import gnu.io.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 说明：常用工具
 */
public class Tools {
	
	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum(){
		 Random r = new Random();
		 return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 把时间根据时、分、秒转换为时间段
	 * @param StrDate
	 */
	public static String getTimes(String StrDate){
		String resultTimes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date now;
	    
	    try {
	    	now = new Date();
	    	java.util.Date date=df.parse(StrDate);
	    	long times = now.getTime()-date.getTime();
	    	long day  =  times/(24*60*60*1000);
	    	long hour = (times/(60*60*1000)-day*24);
	    	long min  = ((times/(60*1000))-day*24*60-hour*60);
	    	long sec  = (times/1000-day*24*60*60-hour*60*60-min*60);
	        
	    	StringBuffer sb = new StringBuffer();
	    	//sb.append("发表于：");
	    	if(hour>0 ){
	    		sb.append(hour+"小时前");
	    	} else if(min>0){
	    		sb.append(min+"分钟前");
	    	} else{
	    		sb.append(sec+"秒前");
	    	}
	    		
	    	resultTimes = sb.toString();
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    
	    return resultTimes;
	}
	
	/**
	 * 写txt里的单行内容
	 * @param filePath  文件路径
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if(filePath.indexOf(":") != 1){
			filePath = File.separator + filePath;
		}
		try {
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");      
	        BufferedWriter writer=new BufferedWriter(write);          
	        writer.write(content);      
	        writer.close(); 

	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	
	 /**
	  * 验证手机号码
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 
	/**
	 * 检测KEY是否正确
	 * @param paraname  传入参数
	 * @param FKEY		接收的 KEY
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean checkKey(String paraname, String FKEY){
		paraname = (null == paraname)? "":paraname;
		return MD5.md5(paraname+DateUtil.getDays()+",fh,").equals(FKEY);
	}
	 
	/**
	 * 读取txt里的单行内容
	 * @param filePath  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {
			
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}
	/**
	 * 根据Key读取Properties中Value
	 * @Title: GetValueByKey 
	 * @param @param fileP
	 * @param @param key
	 * @return String
	 */
	      public static String GetValueByKey(String fileP, String key) {
	    	  String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
				filePath = filePath.replaceAll("file:/", "");
				filePath = filePath.replaceAll("%20", " ");
				filePath = filePath.trim() + fileP.trim();
				if(filePath.indexOf(":") != 1){
					filePath = File.separator + filePath;
				}
	          Properties pps = new Properties();
	          try {
	              InputStream in = new BufferedInputStream (new FileInputStream(filePath));  
	              pps.load(in);
	              String value = pps.getProperty(key);
//	             System.out.println(key + " = " + value);
	             return value;
	             
	         }catch (IOException e) {
	             e.printStackTrace();
	             return null;
	         }
	     }
	      /**
	       *写入properties信息
	       * @Title: writeProperties 	
	       * @param fileP 配置文件路径
	       * @param parameterName 配置文件属性名
	       * @param parameterValue 需要写入的配置文件的信息
	       */
	      public static boolean writeProperties(String fileP,String parameterName, String parameterValue) throws  IOException {
	    	  String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
	    	  filePath = filePath.replaceAll("file:/", "");
	    	  filePath = filePath.replaceAll("%20", " ");
	    	  filePath = filePath.trim() + fileP.trim();
	    	  if(filePath.indexOf(":") != 1){
	    		  filePath = File.separator + filePath;
	    	  }
	    	  Properties prop = new Properties();
	          try {
	              InputStream fis = new FileInputStream(filePath);
	              //从输入流中读取属性列表（键和元素对）
	              prop.load(fis);
	              //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
	              //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
	              OutputStream fos = new FileOutputStream(filePath);
	              prop.put(parameterName, parameterValue);
	              //以适合使用 load 方法加载到 Properties 表中的格式，
	              //将此 Properties 表中的属性列表（键和元素对）写入输出流
	              prop.store(fos, " Update '" + parameterName + "' value");
	              //setLastUpdateBalanceStat(parameterValue);
	              return true;
	          }catch (IOException e) {
	        	  e.printStackTrace();
	          }
	          return false;
	      }
	     /**
	      * 读取Properties的全部信息
	      * @Title: GetAllProperties 
	      * @param @param fileP
	      * @param @throws IOException 
	      * @return void
	      */
	     public static  Map<String,String> GetAllProperties(String fileP) throws IOException {
	    	 String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
				filePath = filePath.replaceAll("file:/", "");
				filePath = filePath.replaceAll("%20", " ");
				filePath = filePath.trim() + fileP.trim();
				if(filePath.indexOf(":") != 1){
					filePath = File.separator + filePath;
				}
	         Properties pps = new Properties();
	        InputStream in = new BufferedInputStream(new FileInputStream(filePath));
	         pps.load(in);
	         Enumeration en = pps.propertyNames(); //得到配置文件的名字
	         Map<String,String> map = new HashMap<String,String>();
	         while(en.hasMoreElements()) {
	             String strKey = (String) en.nextElement();
	             String strValue = pps.getProperty(strKey);
	             map.put(strKey, strValue.trim());
//	             System.out.println(strKey + "=" + strValue);
	         }
	         return map;
	     }
	     /**
	      * list去重复元素
	      * @Title: removeDuplicate 
	      * @param @param list
	      * @return void
	      */
	     public static void removeDuplicate(List<Object> list) {
	    	    LinkedHashSet<Object> set = new LinkedHashSet<Object>(list.size());
	    	    set.addAll(list);
	    	    list.clear();
	    	    list.addAll(set);
	    	}
	     /**
	      * list添加自动排序
	      * @Title: SortedArrayList 
	      * @param @param e
	      * @return boolen
	      */
	     public static class SortedArrayList<E extends Comparable<E>> extends ArrayList<E>
		 	{
		 		@Override
		 		public boolean add(E e)
		 		{
		 			if(size()==0)
		 			{
		 				add(0,e);
		 				return true;
		 			}
		 			else{
		 				E value = e;
		 				int x=0;
		 				for(x=size(); x>0; x--)
		 				{
		 					if (value.compareTo(get(x-1))>0)
		 					{
		 						break;
		 					}
		 				}
		 				add(x,value);
		 				return true;
		 			}
		 		}
		 	}
	     /**
	      * 
	      * @return
	      * TODO 获取全部IP地址
	      * 时间：2019年1月11日
	      */
	     public static List<String> getLocalIPList() {
	            List<String> ipList = new ArrayList<String>();
	            try {
	                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	                NetworkInterface networkInterface;
	                Enumeration<InetAddress> inetAddresses;
	                InetAddress inetAddress;
	                String ip;
	                while (networkInterfaces.hasMoreElements()) {
	                    networkInterface = networkInterfaces.nextElement();
	                    inetAddresses = networkInterface.getInetAddresses();
	                    while (inetAddresses.hasMoreElements()) {
	                        inetAddress = inetAddresses.nextElement();
	                        if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
	                            ip = inetAddress.getHostAddress();
	                            ipList.add(ip);
	                        }
	                    }
	                }
	            } catch (SocketException e) {
	                e.printStackTrace();
	            }
	            return ipList;
	        }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final ArrayList<String> uartPortUseAblefind() {
		//获取当前所有可用串口
		//由CommPortIdentifier类提供方法
		Enumeration<CommPortIdentifier> portList=CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> portNameList=new ArrayList();
		//添加并返回ArrayList
		while(portList.hasMoreElements()) {
			String portName=portList.nextElement().getName();
			portNameList.add(portName);
		}
		return portNameList;
	}

	/*
	 * 串口常见设置
	 * 1)打开串口
	 * 2)设置波特率 根据单板机的需求可以设置为57600 ...
	 * 3)判断端口设备是否为串口设备
	 * 4)端口是否占用
	 * 5)对以上条件进行check以后返回一个串口设置对象new UARTParameterSetup()
	 * 6)return:返回一个SerialPort一个实例对象，若判定该com口是串口则进行参数配置
	 *   若不是则返回SerialPort对象为null
	 */
	public static final SerialPort portParameterOpen(String portName, int baudrate) {
		SerialPort serialPort=null;
		try {  //通过端口名识别串口
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			//打开端口并设置端口名字 serialPort和超时时间 2000ms
			CommPort commPort=portIdentifier.open(portName,1000);
			//进一步判断comm端口是否是串口 instanceof
			if(commPort instanceof SerialPort) {
				System.out.println("该COM端口是串口！串口名称是：" + portName);
				//进一步强制类型转换
				serialPort=(SerialPort)commPort;
				//设置baudrate 此处需要注意:波特率只能允许是int型 对于57600足够
				//8位数据位
				//1位停止位
				//无奇偶校验
				serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				//串口配制完成 log
				System.out.println("串口参数设置已完成，波特率为"+baudrate+",数据位8bits,停止位1位,无奇偶校验");
			} else {    //不是串口
				System.out.println("该com端口不是串口,请检查设备!");
				//将com端口设置为null 默认是null不需要操作
			}

		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}

		return serialPort;
	}

	/*
	 * 串口数据发送以及数据传输作为一个类
	 * 该类做主要实现对数据包的传输至下单板机
	 */

	/*
	 * 上位机往单板机通过串口发送数据
	 * 串口对象 seriesPort
	 * 数据帧:dataPackage
	 * 发送的标志:数据未发送成功抛出一个异常
	 */
	public static void  uartSendDatatoSerialPort(SerialPort serialPort,byte[] dataPackage) {
		OutputStream out=null;
		try {
			out=serialPort.getOutputStream();
			out.write(dataPackage);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭输出流
			if(out!=null) {
				try {
					out.close();
					out=null;
					//System.out.println("数据已发送完毕!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 上位机接收数据
	 * 串口对象seriesPort
	 * 接收数据buffer
	 * 返回一个byte数组
	 */
	public static byte[] uartReceiveDatafromSingleChipMachine(SerialPort serialPort) {
		byte[] receiveDataPackage=null;
		InputStream in=null;
		try {
			in=serialPort.getInputStream();
			// 获取data buffer数据长度
			int bufferLength=in.available();
			while(bufferLength!=0) {
				receiveDataPackage=new byte[bufferLength];
				in.read(receiveDataPackage);
				bufferLength=in.available();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return receiveDataPackage;
	}
}
