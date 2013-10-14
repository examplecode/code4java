package com.examplecode.https;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

public class HttpsServer {

	 private static final int PORT = 9000;

	  public static void main(String[] args) throws Exception {
	    char[] passphrase = "123456".toCharArray();
	    KeyStore keystore = KeyStore.getInstance("JKS");
	    keystore.load(new FileInputStream("/tmp/keystore.jks"), passphrase);
	    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	    kmf.init(keystore, passphrase);
	    SSLContext context = SSLContext.getInstance("TLS");
	    KeyManager[] keyManagers = kmf.getKeyManagers();

	    context.init(keyManagers, null, null);

	    SSLServerSocketFactory ssf = context.getServerSocketFactory();
	    ServerSocket ss = ssf.createServerSocket(PORT);

	    Socket s = ss.accept();

	    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

	    String line = null;
	    while (((line = in.readLine()) != null)) {
	      System.out.println(line);
	    }
	    in.close();
	    s.close();
	  }

}
