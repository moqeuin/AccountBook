package main;

import db.DBconnection;
import view.LoginView;

public class mainClass {

	public static void main(String[] args) {
		
		DBconnection.initConnection();	
		new LoginView();
	}
}
