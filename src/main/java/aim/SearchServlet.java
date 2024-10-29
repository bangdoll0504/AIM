package aim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");

        String studentId = request.getParameter("studentId");
        String testSession = request.getParameter("testSession");
        
        System.out.println("Received Student ID: " + studentId);
        System.out.println("Received Test Session: " + testSession);

        String testIteration = null;
        String filePath = getServletContext().getRealPath("testIterations.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            testIteration = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (testIteration == null) {
            request.setAttribute("errorMessage", "Test Iterationの情報がありません。");
            request.getRequestDispatcher("result.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String amResultsQuery = "SELECT * FROM am_results WHERE Student_ID = ? AND Test_Iteration = ? AND Test_Session = ?";
            PreparedStatement amStmt = conn.prepareStatement(amResultsQuery);
            amStmt.setString(1, studentId);
            amStmt.setString(2, testIteration);
            amStmt.setString(3, testSession);

            ResultSet amResults = amStmt.executeQuery();
            List<Map<String, Object>> amResultList = new ArrayList<>();

            while (amResults.next()) {
                Map<String, Object> amResult = new HashMap<>();
                amResult.put("studentId", amResults.getString("Student_ID"));
                amResult.put("testIteration", amResults.getString("Test_Iteration"));
                amResult.put("testSession", amResults.getString("Test_Session"));
                for (int i = 1; i <= 80; i++) {
                    amResult.put("question" + i, amResults.getInt("Question_" + i));
                }
                amResultList.add(amResult);
            }

            String examResultsQuery = "SELECT * FROM exam_results WHERE 実施回 = ?";
            PreparedStatement examStmt = conn.prepareStatement(examResultsQuery);
            examStmt.setString(1, testSession);
            ResultSet examResults = examStmt.executeQuery();
            List<Map<String, Object>> examResultList = new ArrayList<>();

            //デバッグ用
            System.out.println("Step0");
            for (Map<String, Object> examResult : examResultList) {
            	System.out.println("Test Session: " + examResult.get("実施回"));
                for (int i = 1; i <= 80; i++) {
                    System.out.println("問" + i + ": " + examResult.get("問" + i));
                }
            }
            
            while (examResults.next()) {
                Map<String, Object> examResult = new HashMap<>();
                examResult.put("testSession", examResults.getString("実施回"));
                for (int i = 1; i <= 80; i++) {
                    examResult.put("mon" + i, examResults.getInt("問" + i));
                }
                examResultList.add(examResult);
            }

            if (amResultList.isEmpty() && examResultList.isEmpty()) {
                request.setAttribute("errorMessage", "該当するデータが見つかりませんでした。");
            } else {
                request.setAttribute("amResults", amResultList);
                request.setAttribute("examResults", examResultList);
            }

            request.getRequestDispatcher("result.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データベースエラーが発生しました。");
            request.getRequestDispatcher("result.jsp").forward(request, response);
        }
    }
}
