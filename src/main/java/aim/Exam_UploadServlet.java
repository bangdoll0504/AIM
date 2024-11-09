package aim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/ExamuploadCsv")
@MultipartConfig
public class Exam_UploadServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8"); // レスポンスエンコーディングをUTF-8に設定
        response.setContentType("text/html; charset=UTF-8"); // コンテンツタイプもUTF-8に設定

        Part filePart = request.getPart("file");
        Connection connection = studentDAO.getConnection();
        
        if (filePart != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "UTF-8"));
                 /*Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)*/) {
                
                String line;
                String sql = "INSERT INTO am_results (Student_ID, Test_Iteration, Test_Session, " +
                             "Question_1, Question_2, Question_3, Question_4, Question_5, Question_6, " +
                             "Question_7, Question_8, Question_9, Question_10, Question_11, Question_12, " +
                             "Question_13, Question_14, Question_15, Question_16, Question_17, Question_18, " +
                             "Question_19, Question_20, Question_21, Question_22, Question_23, Question_24, " +
                             "Question_25, Question_26, Question_27, Question_28, Question_29, Question_30, " +
                             "Question_31, Question_32, Question_33, Question_34, Question_35, Question_36, " +
                             "Question_37, Question_38, Question_39, Question_40, Question_41, Question_42, " +
                             "Question_43, Question_44, Question_45, Question_46, Question_47, Question_48, " +
                             "Question_49, Question_50, Question_51, Question_52, Question_53, Question_54, " +
                             "Question_55, Question_56, Question_57, Question_58, Question_59, Question_60, " +
                             "Question_61, Question_62, Question_63, Question_64, Question_65, Question_66, " +
                             "Question_67, Question_68, Question_69, Question_70, Question_71, Question_72, " +
                             "Question_73, Question_74, Question_75, Question_76, Question_77, Question_78, " +
                             "Question_79, Question_80, Morning_Total_Score) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                                     "?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement statement = connection.prepareStatement(sql);
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",", -1); // -1で空フィールドも配列に含める
                    System.out.println(values.length);
                    
                    // CSVファイルの列数が84でない場合にエラーを表示
                    if (values.length != 84) {
                        response.getWriter().write("CSVのフォーマットエラー: 列数が84ではありません。");
                        System.out.println(values.length);
                        return;
                    }
                    
                    // 各カラムの値を設定
                    statement.setString(1, values[0]);   // Student_ID
                    statement.setString(2, values[1]);   // Test_Iteration
                    statement.setString(3, values[2]);   // Test_Session
                    
                    // 各Questionフィールド（int型）
                    for (int i = 4; i <= 83; i++) {
                        if (values[i - 1].isEmpty()) {
                            statement.setNull(i, java.sql.Types.INTEGER);
                        } else {
                            statement.setInt(i, Integer.parseInt(values[i - 1]));
                        }
                    }
                    
                    // Morning_Total_Score（double型）
                    if (values[83].isEmpty()) {
                        statement.setNull(84, java.sql.Types.DOUBLE);
                    } else {
                        statement.setDouble(84, Double.parseDouble(values[83]));
                    }
                    
                    try{
                    	statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        response.getWriter().write("データベースエラー: " + e.getMessage());
                    }
                }
                response.getWriter().write("データが正常にインポートされました。");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("データベースエラー: " + e.getMessage());
            }
        } else {
            response.getWriter().write("ファイルが選択されていません。");
        }
    }
}
