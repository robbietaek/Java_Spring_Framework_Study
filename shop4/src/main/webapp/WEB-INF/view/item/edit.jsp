<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/jspHeader.jsp" %>    <%--다 쓰기 귀찮으니까 한번에 태그라이브러리 가지고 있는애를 썼다. --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품수정</title>
</head>
<body>
<form:form modelAttribute="item" action="update.shop" enctype="multipart/form-data">
<form:hidden path="id"/>
<form:hidden path="pictureUrl"/>
<h2>상품 정보 수정</h2>
<table><tr><td>상품명</td>
<td><form:input path="name" maxlength="20"/>
<font color = "red"><form:errors path = "name"/></font></td>
</tr>

<tr><td>상품가격</td>
<td><form:input path="price" maxlength="20"/>
<font color = "red"><form:errors path = "price"/></font></td>
</tr>

<tr><td>상품이미지</td>
<td colspan = "2"><input type = "file" name = "picture">${item.pictureUrl}</td>
</tr>

<tr><td>상품설명</td>
<td><form:textarea path = "description" cols = "20" rows = "5"/>
<font color = "red"><form:errors path = "description"/></font></td>
</tr>
<tr><td colspan = "3"><input type = "submit" value = "수정 등록">&nbsp;
<input type = "button" value = "상품목록" onclick = "location.href = 'list.shop'"></td></tr>
</table>
</form:form>
</body>
</html>