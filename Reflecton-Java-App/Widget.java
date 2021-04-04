package com.pinnovations.widgets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Widget extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel heading;
	private int position;
	
	public Widget() {
		this.heading = null;
		this.position = -1;
	}
	
	public Widget(String heading, int pos) {
		this.heading = new JLabel(heading);
		this.position = pos;
		
		switch (pos) {
		case 0:
			this.setBounds(40, 40, 400, 400);
			break;
		
		case 1:
			this.setBounds(640, 40, 400, 400);
			break;
		
		case 2:
			this.setBounds(40, 1480, 400, 400);
			break;
		
		case 3:
			this.setBounds(640, 1480, 400, 400);
			break;

		default:
			break;
		}
	}
	
	public void setHeading(String heading) {
		this.heading = new JLabel(heading);
		this.add(this.heading);
	}
	
	public void setPosition(int pos) {
		this.position = pos;
		
		switch (pos) {
		case 0:
			this.setBounds(40, 40, 400, 400);
			break;
		
		case 1:
			this.setBounds(640, 40, 400, 400);
			break;
		
		case 2:
			this.setBounds(40, 1480, 400, 400);
			break;
		
		case 3:
			this.setBounds(640, 1480, 400, 400);
			break;

		default:
			break;
		}
	}
	
	public JLabel getHeading() {
		return heading;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public Widget getInstance() {
		return this;
	}
	
}