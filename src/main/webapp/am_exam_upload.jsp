<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CSVファイルアップロード</title>
</head>
<body>
    <h2>CSVファイルをアップロード</h2>
    <form action="ExamuploadCsv" method="post" enctype="multipart/form-data">
        <label for="file">CSVファイルを選択:</label>
        <input type="file" name="file" id="file" accept=".csv" required>
        <br><br>
        <button type="submit">アップロード</button>
    </form>
</body>
</html>
