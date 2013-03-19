package com.example.lib;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WebStuff {
	
	// boolean for connection
	static Boolean _conn = false;
	static String _connectionType = "Unavailable";
	
	public static String getConnectionType(Context context){
		
		// get connection type
		netInfo(context);
		return _connectionType;
	}
	
	public static Boolean getConnectionStatus(Context context){
		
		// get connection status
		netInfo(context);
		return _conn;
	}
	
	private static void netInfo(Context context){
		
		// get network information
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni != null){
			if(ni.isConnected()){
				_connectionType = ni.getTypeName();
				_conn = true;
			}
			
		}
	}
	
	public static String getURLStringResponse(URL url){
		String response = "";
		
		// open url connection
		try {
			URLConnection conn = url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());
			
			// set bytes read and create buffer
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			
			// loop for bytes read and response from buffer
			while((bytesRead = bin.read(contentBytes)) != -1){
				response = new String(contentBytes, 0, bytesRead);
				responseBuffer.append(response);
			} 
			return responseBuffer.toString();
			
		} catch(Exception e){
			Log.e("URL RESPONSE ERROR", "getURLStringReponse");
				
		}
		
		return response;
	}
}
