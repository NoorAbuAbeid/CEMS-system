package server.dbControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.RequestExtraTime;

public class ExtraTimeDBController {

	public static ArrayList<RequestExtraTime> getRequest() {
		
		ArrayList<RequestExtraTime> list = new ArrayList<RequestExtraTime>();
		String sqlQuery = "select * from extratime";
		try {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
				String exec = rs.getString(1);		
				String reasons = rs.getString(2);		
				String time = rs.getString(3);		
				list.add(new RequestExtraTime(exec, reasons, time));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(list);
		return list;
	}

}