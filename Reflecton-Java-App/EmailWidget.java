package com.pinnovations.widgets;

public class EmailWidget extends Widget {
	private static final long serialVersionUID = 1L;

	public EmailWidget() {
		this.setHeading(null);
		this.setPosition(-1);
	}
	
	public EmailWidget(String heading, int pos) {
		this.setHeading(heading);
		this.setPosition(pos);
	}
}
