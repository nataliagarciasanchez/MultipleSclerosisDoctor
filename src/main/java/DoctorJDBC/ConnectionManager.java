/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoctorJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author maipa
 */
public class ConnectionManager {
    //this class manages the connection of the doctor to de database
    
    private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/MultipleSclerosis.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			
                        //@todo here we should put any method that we want to exeecute when accesing our database
                        //this is from database but maybe this  year w/ sockets and so on is different

		} catch (Exception e) {
			System.out.println("Database access error");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return c;
	}

	public void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}
    
}
