package server.dbControl;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import entity.Question;
import entity.QuestionBank;
import entity.Subject;

import client.controllers.ClientUI;
import entity.Course;
import entity.Question;
import entity.QuestionBank;
import entity.Test;
import entity.TestBank;

/**
 * Deals with inserting, deleting and updating questions
 */
public class QuestionDBController {

	/**
	 * @param username
	 * @return returns all subjects by giiven teacherUsername
	 */
	public static ArrayList<Subject> getAllSubjects(String username) {
		if (username != null) {
			ArrayList<Subject> subjects = new ArrayList<Subject>();
			String sqlQuery = "select * from teacher where username = \"" + username + "\";";

			try {
				if (DBConnector.myConn != null) {
					Statement st = DBConnector.myConn.createStatement();
					ResultSet rs = st.executeQuery(sqlQuery);

					while (rs.next()) {
						// Gather Data
						Blob subjectsBlob = rs.getBlob(2);
						BufferedInputStream bis = new BufferedInputStream(subjectsBlob.getBinaryStream());
						ObjectInputStream ois = new ObjectInputStream(bis);
						subjects = (ArrayList<Subject>) ois.readObject();
					}
					rs.close();
				} else
					System.out.println("myConn is NULL !");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return subjects;
		}else { //  all subjects
			ArrayList<Subject> subjects = new ArrayList<Subject>();
			String sqlQuery = "select * from subject;";
			
			try {
				if (DBConnector.myConn != null) {
					Statement st = DBConnector.myConn.createStatement();
					ResultSet rs = st.executeQuery(sqlQuery);

					while (rs.next()) {
						String id = rs.getString(1);
						String name = rs.getString(2);
						Subject s = new Subject(id, name);
						subjects.add(s);
					}
					rs.close();
				} else
					System.out.println("myConn is NULL !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return subjects;
			
		}
			
	}

	/**
	 * @param q the question to add to database
	 * @return true or false if operation succeded
	 */
	public static boolean addQuestion(Question q) {

		String sqlQuery = "insert into question (id,description,correctAnswer,teacherName,A1,A2,A3,A4,teacherUsername) values (?,?,?,?,?,?,?,?,?)";

		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, q.getId());
				pst.setString(2, q.getDescription());
				pst.setString(3, String.valueOf(q.getCorrectAnswer()));
				pst.setString(4, q.getTeacherName());
				pst.setString(5, q.getAnswers().get(0));
				pst.setString(6, q.getAnswers().get(1));
				pst.setString(7, q.getAnswers().get(2));
				pst.setString(8, q.getAnswers().get(3));
				pst.setString(9, q.getTeacherUsername());
				pst.executeUpdate();

				// DBConnector.myConn.close();
				pst.close();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	/**
	 * @param q the question to update
	 * @return returns true or false if operations succeded
	 */
	public static boolean updateQuestion(Question q) {
		String sqlQuery = "update question set description = ?," + " correctAnswer = ?, A1 = ?,A2 = ?,"
				+ " A3 = ?, A4 = ? " + "where id = ? and teacherUsername = ?;";

		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, q.getDescription());
				pst.setString(2, String.valueOf(q.getCorrectAnswer()));
				pst.setString(3, q.getAnswers().get(0));
				pst.setString(4, q.getAnswers().get(1));
				pst.setString(5, q.getAnswers().get(2));
				pst.setString(6, q.getAnswers().get(3));
				pst.setString(7, q.getId());
				pst.setString(8, q.getTeacherUsername());
				pst.executeUpdate();

				// DBConnector.myConn.close();
				pst.close();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	// get num of questions in question bank
	/**
	 * @param arr arr[0] - subjectID, arr[1] - courseID, arr[2] - teacherUsername
	 * @return returns the next QuestionID
	 */
	public static int getNextQID(ArrayList<String> arr) {
		String sqlQuery = "select * from question where id like \"" + arr.get(0) + "%\" and" + " teacherUsername = \""
				+ arr.get(1) + "\";";
		int place = 1;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					int rowIDNum = Integer.parseInt(rs.getString(1).substring(2));
					if (place < rowIDNum)
						return place;
					else
						while (place <= rowIDNum)
							place++;
				}
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return place;
	}

	// get subjectID from bank name
	/**
	 * @param bankName the subject name
	 * @return returns the subjectID
	 */
	public static String getSubjectID(String bankName) {
		String sqlQuery = "select * from subject;";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					if (rs.getString(2).equals(bankName))
						return rs.getString(1);
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param code the test executionCode
	 * @return returns a test given from executioncode
	 */
	public static Test getTestQuestions(String code) {

		String sqlQuery = "select * from test where executionCode ='" + code + "'";

		Test temp = null;
		ArrayList<Question> questions;
		ArrayList<Integer> points;

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					questions = new ArrayList<>();
					points = new ArrayList<>();

					Blob questionsBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(questionsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					questions = (ArrayList<Question>) ois.readObject();

					Blob qPointsBlob = rs.getBlob(4);
					BufferedInputStream bis1 = new BufferedInputStream(qPointsBlob.getBinaryStream());
					ObjectInputStream ois1 = new ObjectInputStream(bis1);
					points = (ArrayList<Integer>) ois1.readObject();

					// construct current read test
					temp = new Test();
					temp.setId(rs.getString(1));
					temp.setDuration(Integer.parseInt(rs.getString(2)));
					temp.setQuestions(questions);
					temp.setPointsPerQuestion(points);
					temp.setTeacherName(rs.getString(5));
					temp.setTeacherUsername(rs.getString(6));
					temp.setExecutionCode(code);
					temp.setTeacherNotes(rs.getString(8));
					temp.setStudentNotes(rs.getString(9));
					return temp;

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param arr arr[0] - questionID, arr[1] - teacherUsername
	 * @return returns specific question by given questionID and the teacher that
	 *         wrote it
	 */
	public static Question getQuestionByID(ArrayList<String> arr) {
		String sqlQuery = "select * from question where id = \"" + arr.get(0) + "\" and teacherUsername = \""
				+ arr.get(1) + "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();

				String id = rs.getString(1);
				String description = rs.getString(2);
				int correctAnswer = Integer.parseInt(rs.getString(3));
				String teacherName = rs.getString(4);
				ArrayList<String> answers = new ArrayList<>();
				answers.add(rs.getString(5));
				answers.add(rs.getString(6));
				answers.add(rs.getString(7));
				answers.add(rs.getString(8));
				String teacherUsername = rs.getString(9);

				return new Question(id, description, answers, correctAnswer, teacherName, teacherUsername);
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param q the question to be deleted according to the teacherUsername
	 * @return true or false if operation succeded
	 */
	public static boolean deleteQuestion(Question q) {
		String sqlQuery = "delete from question where id = ? and teacherUsername = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, q.getId());
				ps.setString(2, q.getTeacherUsername());
				ps.executeUpdate();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Test getTestPreview(String id) {

		String sqlQuery = "select * from test where id =" + id + "";

		Test temp = null;
		ArrayList<Question> questions;
		ArrayList<Integer> points;

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					questions = new ArrayList<>();
					points = new ArrayList<>();

					Blob questionsBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(questionsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					questions = (ArrayList<Question>) ois.readObject();

					Blob qPointsBlob = rs.getBlob(4);
					BufferedInputStream bis1 = new BufferedInputStream(qPointsBlob.getBinaryStream());
					ObjectInputStream ois1 = new ObjectInputStream(bis1);
					points = (ArrayList<Integer>) ois1.readObject();

					// construct current read test
					temp = new Test();
					temp.setId(rs.getString(1));
					temp.setDuration(Integer.parseInt(rs.getString(2)));
					temp.setQuestions(questions);
					temp.setPointsPerQuestion(points);
					temp.setTeacherName(rs.getString(5));
					temp.setTeacherUsername(rs.getString(6));
					temp.setExecutionCode(rs.getString(7));
					temp.setTeacherNotes(rs.getString(8));
					temp.setStudentNotes(rs.getString(9));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(temp);
		return temp;

	}
}
