package aim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchFormServlet")
public class SearchFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // エンコーディングをUTF-8に設定
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // testIterations.txtファイルのパス
        String filePath = getServletContext().getRealPath("/testIterations.txt");
        List<String> testIterations = new ArrayList<>();

        // ファイルからTest_Iterationを読み込む
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            line = reader.readLine();
            testIterations.add(line.trim());
            System.out.println(testIterations);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException("Error reading testIterations.txt", e);
        }

        // Test_IterationのリストをJSPに渡す
        request.setAttribute("testIterations", testIterations);
        request.getRequestDispatcher("searchForm.jsp").forward(request, response);
    }
}
