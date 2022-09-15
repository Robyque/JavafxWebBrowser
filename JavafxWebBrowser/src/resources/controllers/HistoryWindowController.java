package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class HistoryWindowController extends BorderPane{
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	@FXML
	private Button SortAscendentOrDescendent;
	
	@FXML
	private ListView<String> HistoryDisplayListView;
	
	static ArrayList<String> list = new ArrayList<String>();
public HistoryWindowController() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/HistoryWindow.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}
@FXML
private void initialize() {
	readAllData();
	displayHistory();
}
//---<Read all data from db>
public static void readAllData() {
	Connection con = DBConnection.connect();
	PreparedStatement ps = null;
	ResultSet rs = null;
	
		try {
			String query = "SELECT * FROM History";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String link = rs.getString("Link");
				String date = rs.getString("Date");
				String HistoryView = link + " : " + date;
				list.add(HistoryView);
			}
			
		}catch(SQLException e) {
			System.out.println(e.toString());
		}finally {
			try {
			con.close();
			ps.close();
			rs.close();

			}catch(SQLException e) {
				System.out.println(e.toString());
			}
		}
	
		
  }
public void displayHistory() {
	HistoryDisplayListView.getItems().addAll(list);
}
}
