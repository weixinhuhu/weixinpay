package org.utils.http;




import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class HttpConnect { 
	 private static HttpConnect httpConnect = new HttpConnect();
	    /**
	     * 工厂方法
	     * 
	     * @return
	     */
	    public static HttpConnect getInstance() {
	        return httpConnect;
	    }
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
      
        // 预定接口的返回处理，对特殊字符进行过�?
        public HttpResponse doGetStr(String url) {
    		String CONTENT_CHARSET = "GBK";
    		long time1 = System.currentTimeMillis();
    		HttpClient client = new HttpClient(connectionManager);  
            client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);  
            client.getHttpConnectionManager().getParams().setSoTimeout(55000);
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET); 
            HttpMethod method = new GetMethod(url);
            HttpResponse response = new HttpResponse();
            try {
				client.executeMethod(method);
				System.out.println("调接口返回的时间:"+(System.currentTimeMillis()-time1));
				response.setStringResult(method.getResponseBodyAsString());
			} catch (HttpException e) {
				method.releaseConnection();
				return null;
			} catch (IOException e) {
				method.releaseConnection();
				return null;
			}finally{
				method.releaseConnection();
			}
			return response;
    }
}