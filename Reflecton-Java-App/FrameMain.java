package com.pinnovations.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.pinnovations.widgets.EmailWidget;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;
	public FrameMain() {
		// this = new JFrame();
		this.setSize(new Dimension(1080, 1920));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.BLACK);
		this.setAutoRequestFocus(false);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.BLACK);
		this.getContentPane().setLayout(null);
	}
	
	public void setWidgetEmail(EmailWidget emailWidget) {
		this.getContentPane().add(emailWidget);
	}
}
