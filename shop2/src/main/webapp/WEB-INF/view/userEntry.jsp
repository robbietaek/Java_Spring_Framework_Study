<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /webapp/WEB-INF/view/userEntry.jsp --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 등록</title>
</head>
<body>
<h2>사용자 등록</h2>
<%-- 유효성 검증을 하기 위해 무조건 필요한 행(16) --%>
<form:form modelAttribute="user" method="post" action="userEntry.shop">
	<spring:hasBindErrors name="user">
		<font color="red">
			<c:forEach items="${errors.globalErrors}" var="error">
				<spring:message code="${error.code}" />
			</c:forEach>
		</font>
	</spring:hasBindErrors>
	<table border="1" style="border-collapse: collapse;">
		<tr height="40px">
			<td>아이디</td>
			<td><form:input path="userid" />
			<font color="red">
				<form:errors path="userid" /></font></td>
		</tr>
		<tr height="40px">
			<td>비밀번호</td>
			<td><form:password path="password" />
			<font color="red">
				<form:errors path="password" /></font></td>
		</tr>
		<tr height="40px">
			<td>이름</td>
			<td><form:input path="username" />
			<font color="red">
				<form:errors path="username" /></font></td>
		</tr>
		<tr height="40px">
			<td>전화번호</td>
			<td><form:input path="phoneno" />
			<font color="red">
				<form:errors path="phoneno" /></font></td>
		</tr>		
		<tr height="40px">
			<td>우편번호</td>
			<td><form:input path="postcode" />
			<font color="red">
				<form:errors path="postcode" /></font></td>
		</tr>		
		<tr height="40px">
			<td>주소</td>
			<td><form:input path="address" />
			<font color="red">
				<form:errors path="address" /></font></td>
		</tr>
		<tr height="40px">
			<td>이메일</td>
			<td><form:input path="email" />
			<font color="red">
				<form:errors path="email" /></font></td>
		</tr>
		<tr height="40px">
			<td>생년월일</td>
			<td><form:input path="birthday" />
			<font color="red">
				<form:errors path="birthday" /></font></td>
		</tr>
		<tr height="40px">
			<td colspan="2" align="center">
				<input type="submit" value="등록">
				<input type="reset" value="초기화">
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>