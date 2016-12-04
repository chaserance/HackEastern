package edu.emich.hackeastern.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import edu.emich.hackeastern.Utility.Browser;


/**
 * HackEastern Project: QuickReg
 * 
 * @author Chushu Liu, Jiansong He, Zhiqiang Wang
 * @version 0.9
 * 
 */
public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//Instance variables
	private Container frameContainer;
	private Browser browser;
	private JComboBox<String> majorBox;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JTable inputTable;
	private JPanel controlPanel, rightPanel;
	private InputOptionPanel loginPanel;
	private JScrollPane tablePanel;
	private JButton submit;
	private JRadioButton linear, polynomial;
	private ButtonGroup group;
	private JLabel orderLabel, regressionLabel;
	private ImageIcon icon;
	private String[] majorList;
	
	/**
	 * Customized InputDialog panel
	 * @author chushuliu
	 *
	 */
	private class InputOptionPanel extends JPanel{

		static final long serialVersionUID = 1L;
		JTextField username;
		JPasswordField password;
		InputOptionPanel(){
			setLayout(new GridLayout(2,2));
			username = new JTextField(9);
		    password = new JPasswordField(6);
		    add(new JLabel("EID:"));
		    add(username);
		    //add(Box.createHorizontalStrut(15));
		    add(new JLabel("PIN:"));
		    add(password);
		}
	}
	
	
	public MainFrame(String title, Browser browser){
		super(title);
		frameContainer = getContentPane();
		//import icon
		icon = new ImageIcon(getClass().getResource("/emu.png"));
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		login();
		setIconImage(icon.getImage());
		//initialization
		//=>browser
		this.browser = browser;
		try{
			majorList = browser.majorList();
		}catch(Exception e){
			//System.exit(0);
			e.printStackTrace();
		}
		//=> menu bar
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);
		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		menu.add(menuItem);
		setJMenuBar(menuBar);
		//Drop down list
		majorBox = new JComboBox<>(majorList);
		frameContainer.add(majorBox, "West");
		submit = new JButton("一个字就是干");
		frameContainer.add(submit, "South");
		setSize(480,360);
		//pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void login(){
		loginPanel = new InputOptionPanel();
		String prompt = "Please Enter EID and PIN";
	  x:do{
			int inputDialog = JOptionPane.showConfirmDialog(this, loginPanel, 
					prompt, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,icon);
		      if (inputDialog == JOptionPane.OK_OPTION){
		    	  try{
		    		  boolean isAuthorized = browser.logSuccessful(loginPanel.username.getText(), loginPanel.password.getText());
			          if(isAuthorized){
			        	  return;
			          }
			          else{
			        	 prompt = "Invalid EID or PIN, please try again";
			        	 continue x;
			         }
		    	  }catch(Exception e){
		    		 prompt = "Connection problem, try again or quit"; 
		    		 continue x;
		    	  }
		      }else
		    	  System.exit(0);
		}while(true);
	}
}

