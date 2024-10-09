package aim;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CompareServlet")
public class CompareServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public CompareServlet() {
        super();
        studentDAO = new StudentDAO();  // StudentDAOを初期化
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            compareAndUpdateScores();
            response.getWriter().println("Scores updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error updating scores.");
        }
    }

    private void compareAndUpdateScores() throws SQLException {
        ResultSet amResultsSet = studentDAO.getAmResults();  // am_Resultsテーブルからデータを取得

        while (amResultsSet.next()) {
            String studentID = amResultsSet.getString("Student_ID");
            String testIteration = amResultsSet.getString("Test_Iteration");
            String testSession = amResultsSet.getString("Test_Session");
            System.out.println("GoGo");
            // exam_results テーブルから Test_Iteration と Test_Session が一致するレコードを取得
            ResultSet examResultsSet = studentDAO.getExamResults(testIteration, testSession);
            System.out.println(testSession);
            if (examResultsSet.next()) {
                double totalScore = 0.0;
                System.out.println("GoGoGo");

                // 各質問項目 (Question_1 ～ Question_80) を比較
                for (int i = 1; i <= 80; i++) {
                    String questionColumn = "Question_" + i;
                    String questionColumn2 = "問" + i;
                    int amQuestionValue = amResultsSet.getInt(questionColumn);
                    int examQuestionValue = examResultsSet.getInt(questionColumn2);

                    if (amQuestionValue == examQuestionValue) {
                        totalScore += 1.25;
                    }
                }
                System.out.println(totalScore);
                // 合計スコアをam_Resultsテーブルに更新
                studentDAO.updateMorningTotalScore(totalScore, studentID, testIteration, testSession);
            }
        }
    }
}
