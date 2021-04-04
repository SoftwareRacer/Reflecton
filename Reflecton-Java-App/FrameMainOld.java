package com.pinnovations.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pinnovations.download.Day;
import com.pinnovations.widgets.EmailWidget;

public class FrameMainOld extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel[] widgets;

	public FrameMainOld() {
		this.frame = new JFrame();
		this.frame.setSize(new Dimension(1080, 1920));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBackground(Color.BLACK);
		this.frame.setAutoRequestFocus(false);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.getContentPane().setBackground(Color.BLACK);
		this.frame.getContentPane().setLayout(null);
		
		widgets = new JPanel[4];
		for (int i = 0; i < widgets.length; i++) {
			widgets[i] = new JPanel();
			widgets[i].setLayout(new FlowLayout());
		}
		
		widgets[0].setBounds(40, 40, 400, 400);
		widgets[1].setBounds(640, 40, 400, 400);
		widgets[2].setBounds(40, 1480, 400, 400);
		widgets[3].setBounds(640, 1480, 400, 400);
		
		/*
		for (int i = 0; i < widgets.length; i++) {
			this.frame.getContentPane().add(widgets[i]);
		}*/
	}
	
	/*public void setWidgetEmail(int pos, EmailWidget emailWidget) {
		// JLabel heading = new JLabel("Email");
		// this.widgets[pos].add(heading);
		this.widgets[pos].add(emailWidget.getHeading());
	}*/
	
	public void setWidgetEmail(EmailWidget emailWidget) {
		System.out.println("Position: " + emailWidget.getPosition());
		this.widgets[emailWidget.getPosition()] = emailWidget.getInstance();
	}
	
	public void setWidgetCalendar(int pos) {
		JLabel heading = new JLabel("Calendar");
		this.widgets[pos].add(heading);
	}
	
	public void setWidgetWeather(int pos, Day[] weather) {
		JLabel heading = new JLabel("Weather");
		JLabel icon = new JLabel();
		icon.setIcon(new ImageIcon("images/WidgetWeather.png"));
		this.widgets[pos].add(heading);
		this.widgets[pos].add(icon);
		
		/*
		JLabel[] labels = new JLabel[weather[0].getFields().length];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(weather[0].getFields()[i]);
			this.widgets[pos].add(labels[i]);
		}*/
	}
	
	public void setWidgetClock(int pos) {
		JLabel heading = new JLabel("Clock");
		this.widgets[pos].add(heading);
	}
	
	public void clearPanels() {
		for (int i = 0; i < widgets.length; i++) {
			widgets[i].removeAll();
			widgets[i].revalidate();
			widgets[i].repaint();
		}
	}
	
	public void addWidgetsToFrame() {
		for (int i = 0; i < widgets.length; i++) {
			this.frame.getContentPane().add(this.widgets[i]);
		}
	}
	
	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
}