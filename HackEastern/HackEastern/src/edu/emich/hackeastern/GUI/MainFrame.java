package edu.emich.hackeastern.GUI;

import java.util.*;
import java.util.Timer;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import edu.emich.hackeastern.Utility.Browser;
import edu.emich.hackeastern.Utility.ParseHTML;
import edu.emich.hackeastern.Utility.SendMail;

/**
 * HackEastern Project: QuickReg
 * 
 * @author Chushu Liu, Jiansong He, Zhiqiang Wang
 * @version 0.9
 * 
 */
public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final static long INTERVAL = 30000L;
	//Instance variables
	private Container frameContainer;
	private Browser browser;
	private JComboBox<String> majorBox;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem, interval, stop;
	private JTable courseTable;
	private JScrollPane tablePanel;
	private JPanel listPanel,inputPanel,controlPanel, enableSignalPanel, topPanel;
	private JTextField codeNumberInput;
	private InputOptionPanel loginPanel;
	private JButton submit;
	private JLabel majorLabel, inputLabel, enabledLight;
	private ImageIcon icon,signal,red,green;;
	private String[] majorList;
	private String email, currentSelectedMajor, value;
	private ArrayList<ArrayList<Integer>> availableSpots;
	private HashMap<String,String> majorSub;
	private HashMap<Integer,Integer> availableSection;
	private ParseHTML ph;
	private Timer timer;
	private boolean timerIsEnable = false;
	private static final Pattern EMAIL_ADDRESS_VALIDATOR = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Customized InputDialog panel
	 * @author chushuliu
	 *
	 */
	private class InputOptionPanel extends JPanel{

		static final long serialVersionUID = 1L;
		JTextField username;
		JPasswordField password;
		JTextField email;
		InputOptionPanel(){
			setLayout(new GridLayout(3,2));
			username = new JTextField(12);
		    password = new JPasswordField(12);
		    email = new JTextField(12);
		    add(new JLabel("EID:"));
		    add(username);
		    //add(Box.createHorizontalStrut(15));
		    add(new JLabel("PIN:"));
		    add(password);
		    add(new JLabel("Email:"));
		    add(email);
		}
	}
	
	// Customized table model
    private class MyTableModel extends AbstractTableModel{
		
		private static final long serialVersionUID = 1L;
		// column labels
		private String[] columnNames = {"Course","Section","Available Seat","Waiting List Remain"};
		/**
		 * {@inheritDoc}
		 */
        public int getColumnCount() {
        	return columnNames.length;
        }
        /**
		 * {@inheritDoc}
		 */
        public int getRowCount() {
        	if(availableSpots != null)
        		return availableSpots.get(0).size();
        	return 0;
        }
        /**
		 * {@inheritDoc}
		 */
        public String getColumnName(int col) {
            return columnNames[col];
        }
        /**
		 * {@inheritDoc}
		 */
        public Object getValueAt(int row, int col) {
        	if(col == 0)
        		return majorSub.get(currentSelectedMajor)+" "+codeNumberInput.getText();
        	else if(col == 1)
        		return row;
            return availableSpots.get(col-2).get(row);
        }
    }
	
	/**
	 * Constructor
	 * @param title
	 * @param browser
	 */
	public MainFrame(String title, Browser browser){
		super(title);
		frameContainer = getContentPane();
		//import icon
		icon = new ImageIcon(getClass().getResource("/emu.png"));
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		//import signal image
		signal = new ImageIcon(getClass().getResource("/green.png"));
		img = signal.getImage();
		newimg = img.getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		green = new ImageIcon(newimg);
		signal = new ImageIcon(getClass().getResource("/red.png"));
		img = signal.getImage();
		newimg = img.getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		red = new ImageIcon(newimg);
		//=>browser
		this.browser = browser;
		majorSub = browser.getMajorDic();
		login();
		setIconImage(icon.getImage());
		//initialization
		try{
			majorList = browser.majorList();
		}catch(Exception e){
			System.exit(0);
		}
		//=> menu bar
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);
		interval = new JMenuItem("Subscribe for this course");
		interval.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				timerIsEnable = true;
				enabledLight.setIcon(green);
				enableSignalPanel.updateUI();
				timer = new Timer();
				timer.schedule(new TimerTask(){
					public void run(){
						submit();
					}
				}, 0L, INTERVAL);
			}
		});
		interval.setEnabled(false);
		stop = new JMenuItem("Stop listening");
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				timer.cancel();
				enabledLight.setIcon(red);
				timerIsEnable = false;
				enableSignalPanel.updateUI();
			}
		});
		stop.setEnabled(false);
		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		menu.add(interval);
		menu.add(stop);
		menu.addSeparator();
		menu.add(menuItem);
		setJMenuBar(menuBar);
		//List Panel
		listPanel = new JPanel(new GridLayout(2,1));
		//Drop down list
		majorBox = new JComboBox<>(majorList);
		majorBox.setSelectedIndex(0);
		majorBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				currentSelectedMajor = (String)cb.getSelectedItem();
			}
		});
		//Label
		majorLabel = new JLabel("Major List: ");
		listPanel.add(majorLabel);
		listPanel.add(majorBox);
		//Course# input text field
		codeNumberInput = new JTextField();
		inputLabel = new JLabel("Course number: ");
		inputPanel = new JPanel(new GridLayout(2,1));
		inputPanel.add(inputLabel);
		inputPanel.add(codeNumberInput);
		//enableSignalPanel
		enableSignalPanel = new JPanel(new GridLayout(2,1));
		enabledLight = new JLabel(":Subscribed status(Red:disabled; Green:enabled)",red, SwingConstants.CENTER);
		enableSignalPanel.add(enabledLight);
		enableSignalPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		//Control Panel
		controlPanel = new JPanel(new GridLayout(1,2));
		controlPanel.add(listPanel);
		controlPanel.add(inputPanel);
		//topPanel
		topPanel = new JPanel(new GridLayout(2,1));
		topPanel.add(enableSignalPanel);
		topPanel.add(controlPanel);
		frameContainer.add(topPanel,"North");
		//Table
		courseTable = new JTable(new MyTableModel());
        tablePanel = new JScrollPane(courseTable);
        courseTable.setFillsViewportHeight(true);
        frameContainer.add(tablePanel,"Center");
		//Button
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				submit();
				interval.setEnabled(true);
				stop.setEnabled(true);
			}
		});
		frameContainer.add(submit, "South");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Try log in 
	 */
	private void login(){
		loginPanel = new InputOptionPanel();
		String prompt = "Please Enter EID & PIN and email address";
	  x:do{
			int inputDialog = JOptionPane.showConfirmDialog(this, loginPanel, 
					prompt, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,icon);
		      if (inputDialog == JOptionPane.OK_OPTION){
		    	  try{
		    		  email = loginPanel.email.getText();
		        	  if(!EMAIL_ADDRESS_VALIDATOR.matcher(email).matches()){
		        		  prompt = "Not a valid email address! Try again!";
				          continue x;
		        	  }
		    		  boolean isAuthorized = browser.logSuccessful(loginPanel.username.getText(), loginPanel.password.getText());
			          if(isAuthorized)
			        	  return;
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
	
	/**
	 * Keep checking the course availability
	 */
	public void submit(){
		value = majorSub.get(currentSelectedMajor);
		try{
			ph = new ParseHTML(value,codeNumberInput.getText());
			availableSpots = ph.getRemainSeats();
			
			if(timerIsEnable){
				availableSection = new HashMap<>();
				for(int i = 0; i < availableSpots.get(0).size(); i++){
					for(int j = 0; j < 2; j++)
						if(availableSpots.get(j).get(i) > 0)
							availableSection.put(i, j);
				}
				String courseInfo = "";
				Iterator itr = availableSection.entrySet().iterator();
				while(itr.hasNext()){
					Map.Entry pair = (Map.Entry)itr.next();
					Integer row = (Integer)pair.getKey();
					Integer col = (Integer)pair.getValue();
					String seatType = col != 0 ? "Waiting list" : "Registration spot";
					courseInfo += "Section: " + row + " have " + seatType +" available<br>";
				}
				courseInfo = "The course: " + value + " " + codeNumberInput.getText() + ":<br>" + courseInfo;
		
				if(!availableSection.isEmpty())
					sendEmailToUser(courseInfo);
			}
			tablePanel.updateUI();
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
	
	/**
	 * Send email to user if course they subscribed is available now
	 */
	public void sendEmailToUser(String course){
		try{
			SendMail.generateAndSendEmail(course, email);
		}catch(Exception e){
			JOptionPane.showConfirmDialog(frameContainer, "Email send failed");
		}
	}
}

