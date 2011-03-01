package client;

import java.net.*;
import java.io.*;

public class AppClient implements Serializable{
  
private static final long serialVersionUID = -5129157079030380588L;

public void sendFile(File myFile)
  {
	  int port = 4445;
	    String url = "127.0.0.1";
	    String fileName = myFile.getName();
	    
	    // For now we are just connecting to the local host
	    Socket sock;
		try {
			sock = new Socket(url,port);
		    System.out.println("Connecting...");
		    
		    //Send the file name
		    OutputStream socketStream = sock.getOutputStream();
		    ObjectOutput objectOutput = new ObjectOutputStream(socketStream);
		    objectOutput.writeObject(fileName);
		    
		    //Send File
		    byte [] mybytearray  = new byte [(int)myFile.length()];
		    FileInputStream fis = new FileInputStream(myFile);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    bis.read(mybytearray,0,mybytearray.length);
		    OutputStream os = sock.getOutputStream();
		    System.out.println("Sending...");
		    os.write(mybytearray,0,mybytearray.length);

		    os.flush();
		    sock.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }
}