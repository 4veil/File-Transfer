package server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.*;

public class ServerGUI 
{
	AppServer serv = new AppServer();
	
	private JFrame f = new JFrame("Server GUI");
	private JButton btnStart = new JButton("Start Server");
	private JButton btnStop = new JButton("Stop Server ");
	private JButton btnSelectSave = new JButton("Select file Destination ");
	private JLabel lblDir = new JLabel(serv.saveLocation());
	private JLabel lblSavedTo = new JLabel("Save to: ");
	private JFileChooser selectDir = new JFileChooser();
	private JTextArea tfServerLog = new JTextArea();
	private JLabel lblStatus = new JLabel("Server stopped");


	JPanel p = new JPanel(new MigLayout());
	
	
	public ServerGUI()
	{	
		f.add(p);
		f.setResizable(false);
		p.add(lblSavedTo, "split 2");
		p.add(lblDir);
		p.add(btnSelectSave, "right, wrap");
		p.add(btnStart, "split 2");
		p.add(btnStop);
		p.add(lblStatus, "wrap");
		p.add(tfServerLog, "span");
		
		tfServerLog.setMinimumSize(getTextAreaSize());
		tfServerLog.setText("");
		tfServerLog.setEditable(false);

		//Add Listeners
		btnSelectSave.addActionListener(new ListenSelectFile());
		
		btnStart.addActionListener(new ListenStartServer(this));
	}
	
	public static void main(String[] args) 
	{
		
		ServerGUI gui = new ServerGUI();
	  	gui.launchFrame();
	}
	
	public void launchFrame()
	{
	  	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	f.setSize(getPreferredSize());
	  	f.setVisible(true);
	}
	
	public Dimension getPreferredSize()
	{
	    return new Dimension(500, 300);
	}
	
	public Dimension getTextAreaSize()
	{
		return new Dimension(450, 140);
	}
	
	public void addLine(String s)
	{
		tfServerLog.append(s + "\n");
	}
	
	public void changeServerStatus(String s)
	{
		lblStatus.setText(s);
	}
	
	public class ListenSelectFile implements ActionListener
	{
	  	  public void actionPerformed(ActionEvent e){
	  		selectDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	  		if (selectDir.showOpenDialog(p) == JFileChooser.APPROVE_OPTION) { 

	  	      serv.setSaveLocation(selectDir.getSelectedFile());
	  	      lblDir.setText(serv.saveLocation());
	 } else {
	  	      System.out.println("No Selection ");
	  	      }
	     }
	 }
	
	public class ListenStartServer implements ActionListener
	{
		ServerGUI gui;
		
		public ListenStartServer(ServerGUI gui) {
			this.gui = gui;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			serv.runServer(this.gui);
			btnStart.setEnabled(false);
			serv.serverStatus(true);
		}
	}

	

}
