package com.reklamkoll.server;

import java.io.IOException;
 
public class SendPush {
	
	public void SendPush(){
		 try {
		      Runtime.getRuntime().exec( "wscript D:/send.vbs" );
		   }
		   catch( IOException e ) {
		      System.out.println(e);
		      System.exit(0);
		   }
	}
	
} 
