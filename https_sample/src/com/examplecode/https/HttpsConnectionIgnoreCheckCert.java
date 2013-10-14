package com.examplecode.https;

import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 忽略证书检查
 * @author mac
 *
 */
public class HttpsConnectionIgnoreCheckCert {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception   {
		
		
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = { new ExpX509TrustManager() };;
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        
        // 创建URL对象
		// 创建URL对象
        URL myURL = new URL("https://github.com");
 
        // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
        
        httpsConn.setSSLSocketFactory(ssf);
        
 
        // 取得该连接的输入流，以读取响应内容
        InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());
 
        // 读取服务器的响应内容并显示
        int respInt = insr.read();
        while (respInt != -1) {
            System.out.print((char) respInt);
            respInt = insr.read();
        }

	}

}


class ExpX509TrustManager implements X509TrustManager{
	   public ExpX509TrustManager(){}
	   public void checkClientTrusted(X509Certificate[] chain,   String authType) {}
	      public void checkServerTrusted(X509Certificate[] chain,String authType) {
	       //System.out.println("cert: " + chain[0].toString() + ", authType: " + authType);
	      }
	      public X509Certificate[] getAcceptedIssuers() {
	       return null;
	      }
	}
