package aim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> testIterations = new ArrayList<>();

        // testIterations.txtを読み込む
        String filePath = getServletContext().getRealPath("testIterations.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                testIterations.add(line);  // 各行をtestIterationsリストに追加
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!testIterations.isEmpty()) {
            try (Connection conn = DatabaseUtil.getConnection()) {
                // test_information テーブルからTest_Iterationに基づいてTest_Sessionの一覧を取得
                String query = "SELECT DISTINCT Test_Session FROM test_information WHERE Test_Iteration = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                // testIterations.txtの最初の値を使用してクエリを実行（複数ある場合は適宜調整可能）
                stmt.setString(1, testIterations.get(0));

                ResultSet rs = stmt.executeQuery();

                // Test_Sessionをリストに追加
                List<String> testSessions = new ArrayList<>();
                while (rs.next()) {
                    testSessions.add(rs.getString("Test_Session"));
                }

                // JSPにTest_Sessionリストを渡す
                request.setAttribute("testSessions", testSessions);
                request.getRequestDispatcher("resultcheck.jsp").forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("errorMessage", "Test Iterations file is empty or missing.");
            request.getRequestDispatcher("resultcheck.jsp").forward(request, response);
        }
    }
}