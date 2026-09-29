<%@ include file="../partials/header.jsp" %>
<%
    //Obtenemos el userName desde la request
    String userName = (String) request.getAttribute("userName");
    //Null check
    if(userName != null) {
%>
    <h2>Hello, <%= userName %>!</h2>
<%
    } else{
%>
    <h2>Hello, World!</h2>
<%
    }
%>


