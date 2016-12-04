package edu.emich.hackeastern.start;

import java.awt.EventQueue;

import edu.emich.hackeastern.GUI.MainFrame;
import edu.emich.hackeastern.Utility.DataDriver;

public class Start {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				MainFrame frame = new MainFrame("QuickReg",new DataDriver());
				frame.setVisible(true);
			}
		});
	}

}
