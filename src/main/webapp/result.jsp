<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="aim.CharacterUtil" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>結果</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        .section {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <h2>Student ID: ${studentId}</h2>
    <h2>Test Iteration: ${testIteration}</h2>
    <h2>Test Session: ${testSession}</h2>
    <h2>Test Results: ${testResults}</h2>
    
    <div class="section">
        <table>
            <thead>
                <tr>
                    <th colspan="4">問番号 1-20</th>
                    <th colspan="4">問番号 21-40</th>
                    <th colspan="4">問番号 41-60</th>
                    <th colspan="4">問番号 61-80</th>
                </tr>
                <tr>
                    <th>問番号</th>
                    <th>解答</th>
                    <th>正答</th>
                    <th>結果</th>
                    <th>問番号</th>
                    <th>解答</th>
                    <th>正答</th>
                    <th>結果</th>
                    <th>問番号</th>
                    <th>解答</th>
                    <th>正答</th>
                    <th>結果</th>
                    <th>問番号</th>
                    <th>解答</th>
                    <th>正答</th>
                    <th>結果</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="q" begin="1" end="20">
                    <tr>
                        <!-- 問番号の表示 -->
                        <td>${q}</td>
                        <!-- キーを連結して設定 -->
                        <c:set var="questionKey1"><c:out value="question${q}" /></c:set>
                        <c:set var="examKey1"><c:out value="mon${q}" /></c:set>
                        <td>${amResults[0][questionKey1] != null ? CharacterUtil.getJapaneseCharacter(amResults[0][questionKey1]) : "N/A"}</td>
                        <td>${examResults[0][examKey1] != null ? CharacterUtil.getJapaneseCharacter(examResults[0][examKey1]) : "N/A"}</td>
                        <td>${amResults[0][questionKey1] != null && examResults[0][examKey1] != null && amResults[0][questionKey1] == examResults[0][examKey1] ? "〇" : "×"}</td>

                        <!-- 問番号+20の表示 -->
                        <td>${q + 20}</td>
                        <c:set var="questionKey2"><c:out value="question${q + 20}" /></c:set>
                        <c:set var="examKey2"><c:out value="mon${q + 20}" /></c:set>
                        <td>${amResults[0][questionKey2] != null ? CharacterUtil.getJapaneseCharacter(amResults[0][questionKey2]) : "N/A"}</td>
                        <td>${examResults[0][examKey2] != null ? CharacterUtil.getJapaneseCharacter(examResults[0][examKey2]) : "N/A"}</td>
                        <td>${amResults[0][questionKey2] != null && examResults[0][examKey2] != null && amResults[0][questionKey2] == examResults[0][examKey2] ? "〇" : "×"}</td>

                        <!-- 問番号+40の表示 -->
                        <td>${q + 40}</td>
                        <c:set var="questionKey3"><c:out value="question${q + 40}" /></c:set>
                        <c:set var="examKey3"><c:out value="mon${q + 40}" /></c:set>
                        <td>${amResults[0][questionKey3] != null ? CharacterUtil.getJapaneseCharacter(amResults[0][questionKey3]) : "N/A"}</td>
                        <td>${examResults[0][examKey3] != null ? CharacterUtil.getJapaneseCharacter(examResults[0][examKey3]) : "N/A"}</td>
                        <td>${amResults[0][questionKey3] != null && examResults[0][examKey3] != null && amResults[0][questionKey3] == examResults[0][examKey3] ? "〇" : "×"}</td>

                        <!-- 問番号+60の表示 -->
                        <td>${q + 60}</td>
                        <c:set var="questionKey4"><c:out value="question${q + 60}" /></c:set>
                        <c:set var="examKey4"><c:out value="mon${q + 60}" /></c:set>
                        <td>${amResults[0][questionKey4] != null ? CharacterUtil.getJapaneseCharacter(amResults[0][questionKey4]) : "N/A"}</td>
                        <td>${examResults[0][examKey4] != null ? CharacterUtil.getJapaneseCharacter(examResults[0][examKey4]) : "N/A"}</td>
                        <td>${amResults[0][questionKey4] != null && examResults[0][examKey4] != null && amResults[0][questionKey4] == examResults[0][examKey4] ? "〇" : "×"}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
