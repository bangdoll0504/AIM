<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CSVファイルアップロード</title>
</head>
<body>
    <h2>CSVファイルをアップロードしてください</h2>
    <form action="ST_UploadServlet" method="post" enctype="multipart/form-data">
        <input type="file" name="file" accept=".csv">
        <input type="submit" value="アップロード">
    </form>
</body>
</html>
