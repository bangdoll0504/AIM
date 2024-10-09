package aim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/aim";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String SELECT_AM_RESULTS_SQL = "SELECT * FROM am_Results";
    private static final String SELECT_EXAM_RESULTS_SQL = "SELECT * FROM exam_results WHERE 実施回 = ?";
    private static final String UPDATE_MORNING_TOTAL_SCORE_SQL = "UPDATE am_Results SET Morning_Total_Score = ? WHERE Student_ID = ? AND Test_Iteration = ? AND Test_Session = ?";

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
}
