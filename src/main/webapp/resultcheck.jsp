<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Test Search</title>
</head>
<body>
    <h2>テスト検索</h2>
    <form action="SearchServlet" method="POST" accept-charset="UTF-8">
        <label for="studentId">Student ID:</label>
        <input type="text" name="studentId" id="studentId" required><br><br>

        <label for="testSession">Test Session:</label>
        <select name="testSession" id="testSession">
            <option value="">選択してください</option>
            <%
                List<String> testSessions = (List<String>) request.getAttribute("testSessions");
                if (testSessions != null && !testSessions.isEmpty()) {
                    for (String testSession : testSessions) {
            %>
                        <option value="<%= testSession %>"><%= testSession %></option>
            <%
                    }
                } else {
            %>
                <option value="">データが見つかりませんでした</option>
            <%
                }
            %>
        </select><br><br>

        <button type="submit">検索</button>
    </form>
</body>
</html>
