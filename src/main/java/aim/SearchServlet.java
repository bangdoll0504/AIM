package aim;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO;

    public SearchServlet() {
        super();
        studentDAO = new StudentDAO();  // StudentDAOの初期化
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // エンコーディングをUTF-8に設定
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

    	// フォームからの入力を取得
        String studentID = request.getParameter("studentID");
        String testIteration = request.getParameter("testIteration");
        String testSession = request.getParameter("testSession");
        System.out.println(studentID);
        System.out.println(testIteration);
        System.out.println(testSession);

        try {
            // am_resultsテーブルからデータを取得
            String amresult = studentDAO.getAmResultsById(studentID, testIteration, testSession);
            String examResults = studentDAO.getExamResults_l(testIteration, testSession);

            // 検索結果をJSPに渡して表示
            request.setAttribute("result", amresult);
            request.getRequestDispatcher("result.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error fetching results.");
        }
    }
}
