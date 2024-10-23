<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>am_results 検索フォーム</title>
    <script>
        // Test_Iterationの選択に基づいてTest_Sessionを取得する
        function updateTestSessions() {
            var testIteration = document.getElementById("testIteration").value;
            if (testIteration === "") {
                document.getElementById("testSession").innerHTML = "<option value=''>Test Iterationを選択してください</option>";
                return;
            }

            var xhr = new XMLHttpRequest();
            xhr.open("GET", "GetTestSessionsServlet?testIteration=" + encodeURIComponent(testIteration), true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    document.getElementById("testSession").innerHTML = xhr.responseText;
                }
            };
            xhr.send();
        }
    </script>
</head>
<body>
    <h2>am_results 検索フォーム</h2>
    <form action="SearchServlet" method="post">
        <label for="studentID">Student ID:</label>
        <input type="text" id="studentID" name="studentID" required><br><br>

        <label for="testIteration">Test Iteration:</label>
        <select id="testIteration" name="testIteration" onchange="updateTestSessions()" required>
            <option value="">選択してください</option>
            <c:forEach var="iteration" items="${testIterations}">
                <option value="${iteration}">${iteration}</option>
            </c:forEach>
        </select><br><br>

        <label for="testSession">Test Session:</label>
        <select id="testSession" name="testSession" required>
            <option value="">Test Iterationを選択してください</option>
        </select><br><br>

        <input type="submit" value="検索">
    </form>
</body>
</html>
