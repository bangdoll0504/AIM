package aim;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetTestSessionsServlet")
public class GetTestSessionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO;

    public GetTestSessionsServlet() {
        super();
        studentDAO = new StudentDAO();  // DAOを初期化
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String testIteration = request.getParameter("testIteration");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Test_Iterationに基づいてTest_Sessionを取得
            List<String> testSessions = studentDAO.getTestSessions(testIteration);

            for (String session : testSessions) {
                out.println("<option value='" + session + "'>" + session + "</option>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<option>Error loading sessions</option>");
        }
    }
}
