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
                        <td>${q}</td>
                        <td>${amResult["question" + q] != null ? CharacterUtil.getJapaneseCharacter(amResult["question" + q]) : "N/A"}</td>
                        <td>${examResult != null && examResult["mon" + q] != null ? CharacterUtil.getJapaneseCharacter(examResult["mon" + q]) : "N/A"}</td>
                        <td>${amResult["question" + q] != null && examResult != null && examResult["mon" + q] != null && amResult["question" + q] == examResult["mon" + q] ? "〇" : "×"}</td>
                        <td>${q + 20}</td>
                        <td>${amResult["question" + (q + 20)] != null ? CharacterUtil.getJapaneseCharacter(amResult["question" + (q + 20)]) : "N/A"}</td>
                        <td>${examResult != null && examResult["mon" + (q + 20)] != null ? CharacterUtil.getJapaneseCharacter(examResult["mon" + (q + 20)]) : "N/A"}</td>
                        <td>${amResult["question" + (q + 20)] != null && examResult != null && examResult["mon" + (q + 20)] != null && amResult["question" + (q + 20)] == examResult["mon" + (q + 20)] ? "〇" : "×"}</td>
                        <td>${q + 40}</td>
                        <td>${amResult["question" + (q + 40)] != null ? CharacterUtil.getJapaneseCharacter(amResult["question" + (q + 40)]) : "N/A"}</td>
                        <td>${examResult != null && examResult["mon" + (q + 40)] != null ? CharacterUtil.getJapaneseCharacter(examResult["mon" + (q + 40)]) : "N/A"}</td>
                        <td>${amResult["question" + (q + 40)] != null && examResult != null && examResult["mon" + (q + 40)] != null && amResult["question" + (q + 40)] == examResult["mon" + (q + 40)] ? "〇" : "×"}</td>
                        <td>${q + 60}</td>
                        <td>${amResult["question" + (q + 60)] != null ? CharacterUtil.getJapaneseCharacter(amResult["question" + (q + 60)]) : "N/A"}</td>
                        <td>${examResult != null && examResult["mon" + (q + 60)] != null ? CharacterUtil.getJapaneseCharacter(examResult["mon" + (q + 60)]) : "N/A"}</td>
                        <td>${amResult["question" + (q + 60)] != null && examResult != null && examResult["mon" + (q + 60)] != null && amResult["question" + (q + 60)] == examResult["mon" + (q + 60)] ? "〇" : "×"}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
