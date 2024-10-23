package aim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/aim?useUnicode=true&characterEncoding=UTF-8";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String SELECT_AM_RESULTS_SQL = "SELECT * FROM am_Results";
    private static final String SELECT_AM_RESULTS_ST_SQL = "SELECT * FROM am_results WHERE Student_ID = ? AND Test_Iteration = ? AND Test_Session = ?";
    private static final String SELECT_EXAM_RESULTS_SQL = "SELECT * FROM exam_results WHERE 実施回 = ?";
    private static final String UPDATE_MORNING_TOTAL_SCORE_SQL = "UPDATE am_Results SET Morning_Total_Score = ? WHERE Student_ID = ? AND Test_Iteration = ? AND Test_Session = ?";
    private static final String INSERT_STUDENT_SQL = "INSERT INTO student_information (Student_ID, Class_Attendance_No, Name) VALUES (?, ?, ?)";

    
    public StudentDAO() {}

    // DB接続を取得するメソッド
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error getting DB connection: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // am_Resultsテーブルから全レコードを取得
    public ResultSet getAmResults() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AM_RESULTS_SQL);
        return preparedStatement.executeQuery();
    }

    // exam_resultsテーブルから対応するレコードを取得
    public ResultSet getExamResults(String testIteration, String testSession) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXAM_RESULTS_SQL);
        //preparedStatement.setString(1, testIteration);
        preparedStatement.setString(1, testSession);
        return preparedStatement.executeQuery();
    }

    // am_ResultsテーブルのMorning_Total_Scoreを更新
    public void updateMorningTotalScore(double totalScore, String studentID, String testIteration, String testSession) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MORNING_TOTAL_SCORE_SQL)) {
            
            preparedStatement.setDouble(1, totalScore);
            preparedStatement.setString(2, studentID);
            preparedStatement.setString(3, testIteration);
            preparedStatement.setString(4, testSession);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating Morning_Total_Score: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 学生データを挿入するメソッド
    public void insertStudent(String studentID, String attendanceNo, String name) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            
            preparedStatement.setString(1, studentID);
            preparedStatement.setString(2, attendanceNo);
            preparedStatement.setString(3, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to insert student.", e);
        }
    }
    
    // am_resultsテーブルから特定のデータを取得
    public String getAmResultsById(String studentID, String testIteration, String testSession) throws SQLException {
        StringBuilder resultBuilder = new StringBuilder();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AM_RESULTS_ST_SQL)) {

            preparedStatement.setString(1, studentID);
            preparedStatement.setString(2, testIteration);
            preparedStatement.setString(3, testSession);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultBuilder.append("Student ID: ").append(resultSet.getString("Student_ID")).append("<br>");
                resultBuilder.append("Test Iteration: ").append(resultSet.getString("Test_Iteration")).append("<br>");
                resultBuilder.append("Test Session: ").append(resultSet.getString("Test_Session")).append("<br>");
                for (int i = 1; i <= 80; i++) {
                    resultBuilder.append("Question_").append(i).append(": ").append(resultSet.getInt("Question_" + i)).append("<br>");
                }
                resultBuilder.append("Morning Total Score: ").append(resultSet.getDouble("Morning_Total_Score")).append("<br>");
            } else {
                resultBuilder.append("No data found for the specified Student ID, Test Iteration, and Test Session.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching data from am_results.", e);
        }

        return resultBuilder.toString();
    }
    
    // test_informationテーブルからすべてのTest_Iterationを取得
    public List<String> getTestIterations() throws SQLException {
        List<String> testIterations = new ArrayList<>();
        String query = "SELECT DISTINCT Test_Iteration FROM test_information";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                testIterations.add(resultSet.getString("Test_Iteration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching Test_Iteration.", e);
        }

        return testIterations;
    }

    // 特定のTest_Iterationに対応するTest_Sessionを取得
    public List<String> getTestSessions(String testIteration) throws SQLException {
        List<String> testSessions = new ArrayList<>();
        String query = "SELECT Test_Session FROM test_information WHERE Test_Iteration = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, testIteration);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    testSessions.add(resultSet.getString("Test_Session"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching Test_Session.", e);
        }

        return testSessions;
    }
    
    // exam_resultsテーブルから対応するレコードを取得
    public List<String> getExamResults_l(String testIteration, String testSession) throws SQLException {
        List<String> testResult = new ArrayList<>();
    	Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXAM_RESULTS_SQL);
        //preparedStatement.setString(1, testIteration);
        preparedStatement.setString(1, testSession);
        preparedStatement.executeQuery();
        return testResult;
    }

}
