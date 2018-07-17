package com.neusipo.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpUtil {
	
	  private static CloseableHttpClient httpClient; 
	  
      static{
    	  PoolingHttpClientConnectionManager cm  =new PoolingHttpClientConnectionManager();
    	  cm.setMaxTotal(50);  //10 50 100 
    	  //cm.setDefaultMaxPerRoute(50);//设置每个路由上的默认连接数
    	  httpClient = HttpClients.custom().setConnectionManager(cm).build(); 
      }
      
      /**
       * 
       * <p>[描述功能:get(url)]</p>
       * 
       * @param url
       * @throws IOException
       * @return: void
       * @author: 范丹平
       * @mail: fandp@neusoft.com
       * @date: Created on 2018-4-14 下午01:47:42
       */
      public static void get(String url,String filename) throws IOException{
    	  CloseableHttpResponse  response =null;
    	  try{
    		  HttpGet httpGet=new HttpGet(url);
    		  response=httpClient.execute(httpGet);
    		  System.out.println("success url is " + url + " in " + filename+"\r\n");
    	  }catch(Exception e){
    		    //打印出错的串
    		 // System.out.println("error url is " + url );
    		  //将错误的图片缓存url写入文件
     		 BufferedWriter out = null;
     		 try {
     		 out = new BufferedWriter(new OutputStreamWriter(
     		// new FileOutputStream("/home/sipo/httpLog/error.txt", true)));
     	    new FileOutputStream("E:/httperrorLogs/error.txt", true)));
     		 out.write("error url is " + url + " in " + filename+"\r\n");
     		 } catch (Exception e3) {
     		 e3.printStackTrace();
     		 } finally {
     		 try {
     		   out.close();
     		 } catch (IOException e4) {
     		   e4.printStackTrace();
     		 }
     		 }
    	  }finally{
    		  try {
    		        if (null != response) response.close();
    		      } catch (IOException e) {
    		        e.printStackTrace();
    		      }
    	  }
      }
     /**
      * 
      * <p>[读取单个文件，内部调用get()]</p>
      * 
      * @return: void
      * @author: 范丹平
      * @mail: fandp@neusoft.com
      * @date: Created on 2018-4-14 下午12:22:03
      */
     public static void readFileByLines(String filename){
    	 File file =new File(filename);
    	 BufferedReader reader=null;
    	 String tempString=null;
    	 try{
    		reader =new BufferedReader(new FileReader(file));
    		int line=1;
    		// 一次读入一行，直到读入null为文件结束  
    		while((tempString =reader.readLine())!=null){
    			//显示行号
    			// System.out.println("line " + line + ": " + tempString);  
    			 //触发get
    			  get(tempString,filename);
    			  line++;  
    		}
    		reader.close();
    	 }catch(Exception e){
    		 System.out.println("error url is " + tempString + filename);
    		 e.printStackTrace(); 
    	 }finally{
    		if(reader !=null){
    			try{
    				reader.close();
    			}catch(Exception e1){
    				e1.printStackTrace(); 
    			}
    		} 
    	 }
     }
     
     /**
      * 
      * <p>[遍历所有文件]</p>
      * 
      * @param pathName
      * @return: void
      * @author: 范丹平
      * @mail: fandp@neusoft.com
      * @date: Created on 2018-4-14 下午01:16:02
      */
    public static void find(String pathName){
    	//获取PathName的File对象
    	File dirFile =new File(pathName);
    	//判断该文件或目录是否存在，不存在时在控制台输出提醒
    	if(!dirFile.exists()){
    		System.out.println("文件目录不存在");
    		return;
    	}
    	//获取此目录下的所有文件名与目录名  
        File[] fileList = dirFile.listFiles();  
        for(int i=0;i< fileList.length;i++){
        	//System.out.println(fileList[i].getAbsolutePath());
        	//！！！！！！！！！！！！！！！！！！！！！！！！！
        	System.out.println( fileList.length);
        	String inOneFile=fileList[i].getAbsolutePath();
        	readFileByLines(inOneFile);
        }
    }
    
      /**
       * 
       * <p>[描述功能]</p>
       * 
       * @param args
       * @throws IOException
       * @return: void
       * @author: 范丹平
       * @mail: fandp@neusoft.com
       * @date: Created on 2018-4-14 下午01:50:41
       */
      public static void main(String[] args) throws IOException{
    	/*  String[] linkImg={
    			  
    			     "http://10.51.52.90/seas/downloadmedia/default/3062182733/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062980343/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062940468/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062953709/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062968102/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062853074/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062996754/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062158580/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062928810/PNG/0",
    				 "http://10.51.52.90/seas/downloadmedia/default/3062922799/PNG/0"
    	  };
    	 for(int i=0;i<linkImg.length;i++){
    		  get(linkImg[i]);
              //System.out.println(linkImg[i]+"end");
          }*/
    	  
    	 
    	  // find("/home/sipo/http");
    	   find("E:/http");
    	  
    	  //readFileByLines("E:/jixie_0.txt");
    	  System.out.println("done");	
      }
}
