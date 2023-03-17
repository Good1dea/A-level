<<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <!DOCTYPE html>
<html>
<head>
<title>List of cars</title>
</head>
<body>
    <h1>You created cars:</h1>
<c:if test="${not empty cars}">
    <table border="2">
        <tr>
            <th>ID</th>
            <th>Type</th>
            <th>Manufacturer</th>
            <th>Color</th>
            <th>Engine</th>
            <th>Count</th>
            <th>Price</th>
            <th>Passenger Count</th>
            <th>Load Capacity</th>
        </tr>
        <c:forEach items="${cars}" var="car" >
            <tr>
                <td>${car.getId()}</td>
                <td>${car.getType()}</td>
                <td>${car.getManufacturer()}</td>
                <td>${car.getColor()}</td>
                <td>${car.getEngine()}</td>
                <td>${car.getCount()}</td>
                <td>${car.getPrice()}</td>
                <td><c:choose>
                   <c:when test="${car.type == 'CAR'}">${car.getPassengerCount()}</c:when>
                   <c:otherwise>N/A</c:otherwise>
                   </c:choose></td>
                <td><c:choose>
                   <c:when test="${car.type == 'TRUCK'}">${car.getLoadCapacity()}</c:when>
                   <c:otherwise>N/A</c:otherwise>
                </c:choose></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty cars}">
    <p>You not create cars yet</p>
</c:if>
</body>
</html>