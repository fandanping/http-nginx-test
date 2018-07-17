package com.neusipo.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ClientMultiThreadExecution {
    
	public static void main(String[] args) throws InterruptedException{
		
		 ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();  
		 cm.setMaxTotal(100);
		 
		 HttpClient httpclient = new DefaultHttpClient(cm);  
	     
		
		 try {  
	            // create an array of URIs to perform GETs on  
				 String[] urisToGet={
						 "http://10.51.52.20/seas/downloadmedia/default/810540958/PNG/0",
						 "http://10.51.52.20/seas/downloadmedia/default/818138355/PNG/0",
						 "http://10.51.52.20/seas/downloadmedia/default/808696450/PNG/0",
						 "http://10.51.52.20/seas/downloadmedia/default/808723455/PNG/0",
						 "http://10.51.52.20/seas/downloadmedia/default/810035728/PNG/0",
						 "http://10.51.52.20/seas/downloadmedia/default/842718748/PNG/0",
						 "http://10.51.52.20/seas/downloadmedia/default/934322690/PNG/0",
					     "http://10.51.52.20/seas/downloadmedia/default/863369196/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/819945119/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/808145909/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/938920651/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/938980074/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/831925579/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/814199307/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/856549783/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/1031053072/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/1034121644/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/1033400022/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/1032289430/PNG/0",
							 "http://10.51.52.20/seas/downloadmedia/default/856615041/PNG/0"
						 
				 };
	  
	            // create a thread for each URI
				 for(int t=0;t<urisToGet.length;t++){
					   
				 }
				
	            GetThread[] threads = new GetThread[4];  
	            
	            for (int i = 0; i < threads.length; i++) {  
	                HttpGet httpget = new HttpGet(urisToGet[i]);  
	                threads[i] = new GetThread(httpclient, httpget, i +1);  
	            }  
	  
	            // start the threads  
	            for (int j = 0; j <threads.length; j++) {  
	                threads[j].start();  
	            }  
	  
	            // join the threads  
	            for (int j = 0; j < threads.length; j++) {  
	                threads[j].join();  
	            }  
	  
	        } finally {  
	       
	            httpclient.getConnectionManager().shutdown();  
	        }  
		 
		 
	}
	
	static class GetThread extends Thread{
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
    	   System.out.println(id + " - about to get something from " + httpget.getURI()); 

           try {  
 
               // execute the method  
               HttpResponse response = httpClient.execute(httpget, context);  
               System.out.println("..........................");
               System.out.println(id + " - get executed");  
               // get the response body as an array of bytes  
             /*  HttpEntity entity = response.getEntity();  
               if (entity != null) {  
                   byte[] bytes = EntityUtils.toByteArray(entity);  
                   System.out.println(id + " - " + bytes.length + " bytes read");  
               }  */
 
           } catch (Exception e) {  
               httpget.abort();  
               System.out.println(id + " - error: " + e);  
           }  
    	   
       }
       
       
	}
	
	
	
}


































































