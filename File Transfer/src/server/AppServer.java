package server;

import java.net.*;
import java.io.*;

@SuppressWarnings("serial")
public class AppServer implements Serializable, Runnable {
	
	int filesize=6022386; //GET THIS VALUE FROM THE FILE DESCRIPTION!!!

	private long start = System.currentTimeMillis();
    private int bytesRead;
    private int current = 0;
    private ServerSocket sSock = null;
	private String fileName = "";
	//Save this location to a file so that it is set properly the next time the
	//user turns on the server!
	private String destinationFile;
	Thread serv = new Thread(this);
	
	public AppServer()
	{
		readDefaultSaveLocation();
	}
	
	private ServerGUI gui;
	
	
	public String saveLocation()
	{
		return destinationFile;
	}
	
	public void setSaveLocation(File f)
	{
		destinationFile = f.toString();
		saveDefaultSaveLocation();
		
	}
	
	public void saveDefaultSaveLocation()
	{
		//Save the last set file location for the back-up
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("H:/My Documents/University/Save here/settings.txt"));
			out.write(destinationFile);
			out.write("\r");
			out.close();
		}catch (IOException e) {}
	}
	
	public void readDefaultSaveLocation()
	{
		try{
			BufferedReader in = new BufferedReader(new FileReader("H:/My Documents/University/Save here/settings.txt"));
			String lastLocation = in.readLine();
			File location = new File(lastLocation);
			setSaveLocation(location);
			in.close();
		}catch (IOException e) {}
	}
	
	public void serverStatus(Boolean b)
	{
		if (b == true)
		{
			gui.changeServerStatus("Server running");
		}else{
			gui.changeServerStatus("Server stopped");
		}
	}
	
	public void runServer(ServerGUI gui)
	{
		this.gui = gui;
		serv.start();
	}
	
	public void stopServer()
	{
		//STOP SERVER HAHAHAH
		serv.interrupt();
	}
	
	public void run()
	{
		try {
			sSock = new ServerSocket(4445);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while (true)
		{
			System.out.println("Waiting for a connection..");
			gui.addLine("Waiting for a connection..");
			
			Socket sock;
			try {
		
				sock = sSock.accept();
				System.out.println("Accepted connection: " + sock);
				gui.addLine("------------------------------------------------");
				gui.addLine("Accepted connection: " + sock);
				
				
				//Receive the file name
			    InputStream socketStream = sock.getInputStream();
			    ObjectInput objectOutput = new ObjectInputStream(socketStream);
			    try {
					fileName = (String)objectOutput.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				//Receive file
			    byte [] mybytearray  = new byte [filesize];
			    InputStream is = sock.getInputStream();
			    FileOutputStream fos = new FileOutputStream(destinationFile + "\\" + fileName);
			    System.out.println("Recieved file: " + fileName);
			    gui.addLine("Recieved file: " + fileName);
			    gui.addLine("File saved to: " + destinationFile + "\\" + fileName);
			    BufferedOutputStream bos = new BufferedOutputStream(fos);
			    bytesRead = is.read(mybytearray,0,mybytearray.length);
			    current = bytesRead;

			    do {
			       bytesRead =
			          is.read(mybytearray, current, (mybytearray.length-current));
			       if(bytesRead >= 0) current += bytesRead;
			    } while(bytesRead > -1);

			    bos.write(mybytearray, 0 , current);
			    bos.flush();
			    long end = System.currentTimeMillis();
			    System.out.println(end-start);
			    long tt = ((end - start) / 1000);
			    String totalTime = Long.toString(tt);
			    gui.addLine("Transfer took: " + totalTime);
			    bos.close();
			    sock.close();
			    gui.addLine("------------------------------------------------");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
