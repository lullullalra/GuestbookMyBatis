<%@page import="java.util.List"%>
<%@page import="gntp.lesson.guestbook.vo.ReplyVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="gntp.lesson.guestbook.util.DateTimeService"%>
<%@page import="gntp.lesson.guestbook.vo.GuestbookVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
	<head>
	<%  GuestbookVO book = (GuestbookVO)request.getAttribute("book"); %>
	<meta charset="UTF-8">
		<script type="text/javascript">
			function goPage(type,frm){
				if(type==0){
					frm.action = "${contextPath}/guestbook/viewUpdateBook.do?id=<%=book.getSeq()%>"; 
				}else{
					frm.action = "${contextPath}/guestbook/writeReply.do";
				}
				frm.submit();
			}
		</script>			
	<title>Read Book</title>
		
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
			color: white;
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
			height: 45px;
			cursor: pointer;
			width: 100px;
		}
		
		.writeBtn:hover{
			background-color: cornflowerblue;
			color: white;
			border-color: blue;
		}
	</style>	
</head>
<body>
	<h1>Read Book</h1>
<body>

<form action="#" method="post" name="frm" >

<input type="hidden" name="seq" value="<%=book.getSeq()%>">

<table>
	<tr><td>title :</td><td><input type="text" class="text" name="title" value="<%=book.getTitle()%>" readonly="readonly"></td><td></td></tr>
	<tr><td>content :</td><td><input type="text" class="text" name="content" size="80" value="<%=book.getContent()%>" readonly="readonly"></td><td></td></tr>
	<tr><td>readCount :</td><td><input type="text"  class="text" name="readCount" value="<%=book.getReadCount() %>" readonly="readonly"></td><td></td></tr>
	<tr><td>date :</td><td><input type="text" name="date" class="text" value="<%=DateTimeService.getDateTime(DateTimeService.DATE_TIME,new Date(book.getRegDate().getTime())) %>" readonly="readonly"></td><td></td></tr>
	<tr><td>userId :</td><td> <input type="text" name="userId" class="text" value="<%=book.getUserId() %>" readonly="readonly"></td><td></td></tr>
	<tr>
		<td colspan="3">
			<input type="submit" value="수정하기" class="writeBtn" onclick="goPage(0,document.frm)">
			<input type="button" value="뒤로가기" class="writeBtn" onclick="javascript:history.back();"> 
			<a href="${contextPath}/guestbook/listBook.do?"><button class="writeBtn">리스트로 가기</button></a>
		</td>
	</tr>
</table><br/>
	
<!-- 댓글 목록 -->
<table>
	<%  
		List<ReplyVO> list = book.getReplyList(); 
		if(list!=null){
			for(ReplyVO vo : list) {
	%>
		<tr><td colspan="3"><%=vo.getReplySeq()+" "+vo.getContent()+" "+vo.getRegDate()%></td></tr>
	<% } } %>	
</table>

<br/>
	<table>
		<tr><th style="text-align:left">댓글</th></tr>
		<tr><td colspan="3"><input type="text" class="text" name="reply" value="" size="100"></td></tr>
		<tr><td colspan="3"><input type="submit" value="댓글쓰기"  class="writeBtn" onclick="goPage(1,document.frm)"></td></tr>
	</table>
</form>
</body>
</html>