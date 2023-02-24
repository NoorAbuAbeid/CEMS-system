package server.dbControl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import client.controllers.ClientUI;
import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.TestDocs;

public class ReportDBController {
	private static ArrayList<TestDocs> testDocsArr;

	public static ArrayList<TestDocs> getAllTestsDocsBySubject(ArrayList<String> arr) {
		ArrayList<TestDocs> answer = new ArrayList<>();
		String sqlQuery = "select * from testdocs where id like \"" 
		+ arr.get(0) + "%\" and teacherUsername = \"" + arr.get(1) + "\";";
		
		TestDocs temp;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					temp = new TestDocs();
					temp.setId(rs.getString(1));
					temp.setYear(rs.getString(2));
					temp.setSemester(rs.getString(3));
					temp.setDate(rs.getString(4));
					temp.setTeacherUsername(rs.getString(5));
					temp.setAssignedTime(rs.getString(6));
					temp.setNumStudentsStart(Integer.parseInt(rs.getString(7)));
					temp.setNumStudentsFinishInTime(Integer.parseInt(rs.getString(8)));
					temp.setNumStudentsNotFinishInTime(Integer.parseInt(rs.getString(9)));
					temp.setAverage(Double.parseDouble(rs.getString(10)));
					temp.setMedian(Double.parseDouble(rs.getString(11)));
					
					Blob distributionBlob = rs.getBlob(12);
					BufferedInputStream bis = new BufferedInputStream(distributionBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					temp.setDistribution((ArrayList<Integer>) ois.readObject());
					//ois.close();
					answer.add(temp);
				}
					
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return answer;
	}
	
	/*public static void main(String[] args) {
		DBConnector db = new DBConnector();
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(5);
		arr.add(1);
		arr.add(7);
		arr.add(1);
		arr.add(4);
		arr.add(2);
		arr.add(1);
		arr.add(3);
		arr.add(6);
		arr.add(8);
		
		String sql = "update testdocs set distribution = ? where id = 010101 and semester = 02";
		try {
			PreparedStatement ps = DBConnector.myConn.prepareStatement(sql);
			
			Blob disBlob = DBConnector.myConn.createBlob();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(arr);
			oos.close();
			
			byte[] questionsAsByte = baos.toByteArray();
			disBlob.setBytes(1, questionsAsByte);
			ps.setBlob(1, disBlob);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
