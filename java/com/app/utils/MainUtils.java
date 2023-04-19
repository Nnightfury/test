package com.app.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;



public class MainUtils {
	public static String encode(String in) {
		try {
			return new String(in.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}

	
	public static JSONObject doGetStr(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url); 
        JSONObject jsonObject = null;
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }
	public static String doGetStr1(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url); 
        JSONObject jsonObject = null;
        String ret = "";
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");
                ret=result;
                //jsonObject = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return ret;
    }
	/**
     * 获取本月第一天
     * @return
     */
    public static Date getmindate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        
        return calendar.getTime();
    }
    
    public static Date dayAdd(Date sDate,int i)
    {
    	Calendar c = Calendar.getInstance();  
        c.setTime(sDate);  
        c.add(Calendar.DAY_OF_MONTH, i); 
        return c.getTime();
    }
    
    public static int dayCompare(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        return day;
    }
    
    
    public static int dayCompare2(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
       
        
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        return day;
    }
    
    public static long getDatePoor(Date endDate, Date nowDate) {
    	 
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return hour ;
    }
    
    
    public static int getDayOfMonth(){
    	   Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
    	   int day=aCalendar.getActualMaximum(Calendar.DATE);
    	   return day;
    	}

    /**
     * 获取本月最后一天
     * @return
     */
    public static Date getmaxdate(){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar2.getTime();
    }
	
	public static String getOrderIdByTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date());
		String result="";
		Random random=new Random();
		for(int i=0;i<3;i++){result+=random.nextInt(10);
		}
		return newDate+result;
		}

	public static String getTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateTime = df.format(date);
		return dateTime;
	}

	public static Date convertTime(String in) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date = new Date(in);
		System.out.println(in);
		Date date = df.parse(in);
		// String dateTime = df.format(date);
		return date;
	}

	public static String getYear(Date in) {
		Calendar clr = Calendar.getInstance();
		clr.setTime(in);

		return clr.get(Calendar.YEAR) + "";
	}

	public static String getMouth(Date in) {
		Calendar clr = Calendar.getInstance();
		clr.setTime(in);

		return (clr.get(Calendar.MONTH) +1)+ "";
	}

	public static String getDay(Date in) {
		Calendar clr = Calendar.getInstance();
		clr.setTime(in);

		return clr.get(Calendar.DAY_OF_MONTH) + "";
	}
}
