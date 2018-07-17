package com.neusipo.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class SeasClientMultiTreadExecution {
	
	
	private static CloseableHttpClient httpClient; 
	private static int POOL_SIZE = 100; 
	/**
	 * 初始化httpclient  
	 */
    static{
	
    	PoolingHttpClientConnectionManager cm  =new PoolingHttpClientConnectionManager();
  	    cm.setMaxTotal(100);  //10 50 100 
  	    //cm.setDefaultMaxPerRoute(50);//设置每个路由上的默认连接数
  	    httpClient = HttpClients.custom().setConnectionManager(cm).build(); 
    }
    static class GetThread implements Runnable{
 	    private final HttpClient httpClient;
 	    private final HttpContext context;  
        private final HttpGet httpget;  
        private final int id;  
        
        public GetThread(HttpClient httpClient, HttpGet httpget, int id) {  
            this.httpClient = httpClient;  
            this.context = new BasicHttpContext();  
            this.httpget = httpget;  
            this.id = id;  
        }  
        
        public void run(){
            try {  
  
                // execute the method  
                HttpResponse response = httpClient.execute(httpget, context);  
                System.out.println(id + " - get executed");  
  
            } catch (Exception e) {  
                httpget.abort();  
                System.out.println(id + " - error: " + e);  
            } finally{
            	httpget.releaseConnection();
            } 
     	   
        }
        
        
 	}
    /**
     * 
     * <p>[读取单个文件]</p>
     * 
     * @return: void
     * @author: 范丹平
     * @mail: fandp@neusoft.com
     * @date: Created on 2018-4-14 下午12:22:03
     */
    public static List<String> readFileByLines(String filename){
   	 File file =new File(filename);
   	 BufferedReader reader=null;
   	 String tempString=null;
   	 List<String> urls = new ArrayList<String>();
   	 try{
   		reader =new BufferedReader(new FileReader(file));
   		int line=1;
   		
   		// 一次读入一行，直到读入null为文件结束  
   		while((tempString =reader.readLine())!=null){
   			//HttpGet httpGet = new HttpGet(tempString);
   			//executor.execute(new GetThread(httpClient, httpGet, line));
   			 // line++;  
   			//System.out.println(tempString);
   			urls.add(tempString);
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
	return urls;
    }
    

	/**
	 * <p>[描述功能]</p>
	 * 
	 * @param args
	 * @return: void
	 * @author: 范丹平
	 * @mail: fandp@neusoft.com
	 * @date: Created on 2018-4-21 上午09:36:52
	 */
	public static void main(String[] args) {
		//创建线程池
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		//List<String> list = readFileByLines("/home/sipo/http/jixie_10.txt");
		List<String> list = readFileByLines("E:/http/jixie_10.txt");
		for(int i = 0, length = list.size();i < length;i++){
			executor.execute(new GetThread(httpClient, new HttpGet(list.get(i)), i));
		}
		executor.shutdown();

	}

}
