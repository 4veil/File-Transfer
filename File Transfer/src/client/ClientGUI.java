package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ClientGUI {
	private JFrame f = new JFrame("Basic GUI");

	private JPanel pnlCenter = new JPanel(); //Centre area
	
	private JButton btnBackup = new JButton("Backup selected file");
	private JButton btnSelect = new JButton("Select File");

	private JMenuBar mb = new JMenuBar(); //Menu bar
	private JMenu mnuFile = new JMenu("File"); // File entry on menu bar
	private JMenuItem mnuItemQuit = new JMenuItem("Quit"); //quite sub item
	private JMenu mnuHelp = new JMenu("Help"); //help menu entry
	private JMenuItem mnuItemAbout = new JMenuItem("About"); //about entry
	
	private JFileChooser selectFile = new JFileChooser();
	
	File selectedFile;
	
  	AppClient a = new AppClient();
  	
	  public ClientGUI() {
	  	f.setJMenuBar(mb);
	  	
	  	mnuFile.add(mnuItemQuit); 
	  	mnuHelp.add(mnuItemAbout); 
	  	mb.add(mnuFile);
	  	mb.add(mnuHelp);
	  	
	  	selectFile.setDialogTitle("Select which file you want to back-up");
	  	selectFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

	  	pnlCenter.add(btnBackup);
	  	pnlCenter.add(btnSelect);

	  	f.getContentPane().add(pnlCenter, BorderLayout.CENTER);
	  	
	  	//Allows the swing App to be closed
	  	f.addWindowListener(new ListenCloseWindowAdapter());
	  	
	  	//Add Menu listener
	  	mnuItemQuit.addActionListener(new ListenMenuQuit());
	  	
	  	//Add send file button listener
	  	btnBackup.addActionListener(new ListenSendButtonPressed());
	  	
	  	//Add select file listener
	  	btnSelect.addActionListener(new ListenSelectFile());
	  }
	  
	//Menu listener
	  public class ListenSelectFile implements ActionListener{
	  	  public void actionPerformed(ActionEvent e){
	  		if (selectFile.showOpenDialog(pnlCenter) == JFileChooser.APPROVE_OPTION) { 
	  	      System.out.println("getCurrentDirectory(): " 
	  	         +  selectFile.getCurrentDirectory());
	  	      System.out.println("getSelectedFile() : " 
	  	         +  selectFile.getSelectedFile());
	  	      
	  	      selectedFile = selectFile.getSelectedFile();
	  	      } else {
	  	      System.out.println("No Selection ");
	  	      }
	     }
	  	  
	 }
	  
	  public Dimension getPreferredSize(){
		    return new Dimension(200, 200);
		    }
	  
	  //Menu listener
	  public class ListenMenuQuit implements ActionListener{
	  	  public void actionPerformed(ActionEvent e){
	  	  	System.exit(0);
	     }
	  	  
	 }
	  
	  //Window listener
	  public class ListenCloseWindowAdapter extends WindowAdapter{
	  	 public void windowClosing(WindowEvent e) {
	  	 	System.exit(0);
	  	 }
	  }
	  
	  //Button listener
	  public class ListenSendButtonPressed implements ActionListener{
		  public void actionPerformed(ActionEvent e){
			  a.sendFile(selectedFile);
		  }
	  }

  public void launchFrame(){
  	//Display Frame
  	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	f.pack(); //Adjust panel to component for display
  	f.setSize(getPreferredSize());
  	f.setVisible(true);
  }
  
  public static void main(String [] args) {
  	ClientGUI gui = new ClientGUI();
  	gui.launchFrame();
  }
}