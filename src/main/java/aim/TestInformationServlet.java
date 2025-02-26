package aim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date; // SQL Date をインポート
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addTestInformation")
public class TestInformationServlet extends HttpServlet {
    private StudentDAO studentDAO;
    
    private static final String FILE_PATH = "./testIterations.txt"; // 実際のファイルパスに変更

    @Override
    public void init() throws ServletException {
        super.init();
        // StudentDAOのインスタンスを初期化
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // testIterations.txtからTest_Iterationのデータを読み込み
        List<String> testIterations = readTestIterationsFromFile();

        // フォームから受け取るデータ
        String testItaration = request.getParameter("Test_Iteration");
        String testSession = request.getParameter("Test_Session");
        String testDateStr = request.getParameter("Test_Date");

        // データベースに接続してデータを挿入
        try (Connection conn = studentDAO.getConnection()) {
            String sql = "INSERT INTO test_information (Test_Iteration, Test_Session, Test_Date) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            Date testDate = null;
            if (testDateStr != null && !testDateStr.isEmpty()) {
                testDate = Date.valueOf(testDateStr);
            }

            for (String testIteration : testIterations) {
                stmt.setString(1, testIteration);
                stmt.setString(2, testSession);
                stmt.setDate(3, testDate); // java.sql.Dateを使用
                stmt.addBatch();  // バッチに追加
            }

            int[] rowsInserted = stmt.executeBatch(); // バッチ実行
            response.getWriter().println(rowsInserted.length + " records inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error.");
        }
    }

    // ファイルからテスト反復データを読み込むメソッド
    private List<String> readTestIterationsFromFile() {
    	String filePatha = getServletContext().getRealPath("/WEB-INF/testIterations.txt");
    	System.out.println(filePatha);
        List<String> testIterations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                testIterations.add(line.trim()); // 各行をリストに追加
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testIterations;
    }
}