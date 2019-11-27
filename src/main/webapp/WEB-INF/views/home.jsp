<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

    <title>Valiutos Kursai</title>
    <link href="${contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

    <script>
        var disabledDays = [0, 6];
        var disableDates = ${holidays};
        $(function() {
            $('#datepickerFrom').datepicker({
                maxDate:new Date,
                onRenderCell: function (date, cellType) {
                    if (cellType == 'day') {
                        var ymd = date.getFullYear()+"-" +(date.getMonth()+1) + "-" + date.getDate();
                        var day = date.getDay();
                        var isDisabled = (disableDates.indexOf(ymd) != -1)+(disabledDays.indexOf(day) != -1) ;

                        return { disabled: isDisabled }
                    }

                }

            })
        });


    </script>
    <link href="${contextPath}/dist/css/datepicker.min.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/dist/js/datepicker.js"></script>


</head>

<body style="background-image: url('${contextPath}/resources/pic.jpg')" >

    <div class="container" style="border: solid 1px white;
                                    -webkit-box-shadow: -1px -1px 10px 2px white;
                                    -moz-box-shadow: -1px -1px 10px 2px white;
                                    box-shadow: -1px -1px 10px 2px white;
                                    background-color: rgba(45, 201, 76, 0.3)">
        <form:form method="POST" modelAttribute="modelForm">
            <div style="margin-top: 30px">
                <div class="d-inline-block">
                    <label>Select Currency:</label>
                    <form:select class="form-control-sm" path="currencyCode" >
                        <form:option value="${datePick.currencyCode}"></form:option>
                        <form:options items="${currCode}" />
                    </form:select>
                </div>
                <div class="d-inline-block" >
                    <label>Choose date: From - To</label>
                    <form:input path="datePick" class="form-control-sm" id="datepickerFrom" data-range="true" data-multiple-dates-separator=" : " type="text"  data-language='en' value="${datePick.datePick}" />
                </div>
                <div class="d-inline-block" >
                    <button class="btn-sm btn-success" type="submit">Submit</button>
                </div>
            </div>
        </form:form>
    </div>

    <c:if test="${empty infoList}">
        <div class="container">
             <h2  >${change}</h2>
        </div>
    </c:if>



    <c:if test="${not empty infoList}">
        <div class="container">
            <h2>Change: ${change}</h2>
            <table class="table table-striped table-light bg-transparent">
                <thead>
                    <tr>
                        <th>Currency Name</th>
                        <th>Currency Code</th>
                        <th>Proportion</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${infoList}" var="list">
                    <tr>
                        <td class="text-center">${list.currencyName}</td>
                        <td class="text-center">${list.currencyCode}</td>
                        <td class="text-center">${list.currency}</td>
                        <td class="text-center">${list.dateCur}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</body>
</html>
