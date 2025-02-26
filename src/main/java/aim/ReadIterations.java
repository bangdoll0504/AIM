package aim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReadIterations")
public class ReadIterations extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = getServletContext().getRealPath("/WEB-INF/testIterations.txt");
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            fileContent.append("File not found: ").append(filePath);
        }

        // ファイル内容をリクエストスコープに設定
        request.setAttribute("testIterations", fileContent.toString());

        // JSP へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/displayTestIterations.jsp");
        dispatcher.forward(request, response);
    }
}
