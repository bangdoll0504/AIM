<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- index.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Test Information Form</title>
</head>
<body>
    <h2>Test Information Form</h2>
    <form action="addTestInformation" method="post">
        <label for="Iteration">Test Iteratio:${testIterations}</label>
        <label for="session">Test Session:</label>
        <input type="text" id="session" name="Test_Session" required><br><br>
        
        <!-- Test_Date フィールドを date に変更 -->
        <label for="date">Test Date:</label>
        <input type="date" id="date" name="Test_Date" required><br><br>
        
        <input type="submit" value="Submit">
    </form>
</body>
</html>
