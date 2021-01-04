package application;

import java.net.HttpURLConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.collections.*;
import javafx.scene.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;



public class Main extends Application {

	private static HttpURLConnection connection;	
	public static ArrayList <String> nameList = new ArrayList <String> () ; 
	public static ArrayList <Integer> issuesList = new ArrayList <Integer> ();
	
	public static void main(String[] args) throws Exception{

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			URL url = new URL("https://api.github.com/users/octocat/repos");
			connection = (HttpURLConnection) url.openConnection();

			// Request Setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();
			//System.out.println(status);

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}

			//System.out.println(responseContent.toString());
			
			  parse(responseContent.toString());
			  
			// Launch pie chart
			launch(args);

		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Parse JSON to readable format. 
		public static String parse (String responseBody) {
		JSONArray info = new JSONArray (responseBody);
		for (int i=0; i<info.length(); i++) {
			JSONObject information = info.getJSONObject(i);
			
			//int id = information.getInt("id");
			String name = information.getString("name");
			int issues = information.getInt("open_issues_count");
			
			//System.out.println(" "+name+" "+issues);
			nameList.add(name);
			issuesList.add(issues);
		}		
		//System.out.println(nameList);
		return null;	
	}

	@Override
	public void start(Stage primaryStage) {
		PieChart pChart = null;
		ObservableList<Data> pieData = null;
		
		//create a pie chart data
		for(int i = 0; i < nameList.size() && i < issuesList.size(); i++) {
		pieData = FXCollections.observableArrayList(
		new PieChart.Data(nameList.get(1), issuesList.get(1)),
		new PieChart.Data(nameList.get(2), issuesList.get(2)),
		new PieChart.Data(nameList.get(3), issuesList.get(3)),
		new PieChart.Data(nameList.get(4), issuesList.get(4)),
		new PieChart.Data(nameList.get(5), issuesList.get(5)));
		}
		
		// Create and assign data to chart
		pChart = new PieChart(pieData);
		pChart.setTitle("Number Of Issues On Each Repo");
	
	   // Set PieChart Properties 
		Group root = new Group(pChart);
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setTitle("pie Chart");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

}























