<%@page import="gntp.lesson.guestbook.util.DateTimeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<style>
	body{
		background-color: #F0F8FF;
	}

	h1{
		text-align: center;
	}
	tr, th, td{
		text-align: center;
		border: 1px solid lightgray;
	}
	
	a{
		text-decoration-line: none;
	}
	
	table{
	 border: 1px #a39485 solid;
	 font-size: .9em;
	 box-shadow: 0 2px 5px rgba(0,0,0,.25);
	 width: 100%;
	 border-collapse: collapse;
	 border-radius: 5px;
	 overflow: hidden;
	}
	
	.text{
		float:left;
	}
	
	.writeBtn{
		background-color: lightblue;
		color: white;
    	border: solid 1px white;
		border-radius: 5px;
		margin-left: 92%;
		height: 45px;
		cursor: pointer;
		width: 100px;
	}
	
	button:hover{
		background-color: cornflowerblue;
		color: white;
		border-color: blue;
	}
</style>
<meta charset="UTF-8">
<title>Guestbook Write</title>
</head>
<body>
<h1>방명록 작성</h1>
<hr>
<form action="${contextPath}/guestbook/insertBook.do" method="post">
<table>
	<tr><th>Title </th><td><input class="text" type="text" name="title"></td><td></td></tr>
	<tr><th>Content </th><td><input class="text" type="text" name="content" size="80"></td><td></td></tr>
	<tr><th>ReadCount </th><td><input type="text" class="text" name="readCount" value="0" readonly="readonly"></td><td></td></tr>
	<tr><th>Date </th><td><input type="text" name="date" class="text" value="<%=DateTimeService.getDateTime(DateTimeService.DATE_TIME) %>" readonly="readonly"></td><td></td></tr>
	<tr><th>UserId </th><td> <input class="text" type="text" name="userId"></td><td></td></tr>
	<tr><td colspan="3"><input type="submit" value="글 작성" class="writeBtn"></td></tr>
</table>	
</form>

</body>
</html>