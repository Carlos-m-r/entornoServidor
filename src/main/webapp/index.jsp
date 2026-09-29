<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="partials/header.jsp" %>
<body>
   <h2>Hello, World!</h2>
   <%
       int count = 10;
       for (int i = 0; i < count; i++) {
           out.println("Number: " + i + "<br>");
       }
   %>
   <p> Current Time: <%= new java.util.Date() %></p>

   <%!
        private int counter = 0;

        public int getCounter() {
            return ++counter;
        }
   %>

   <p>Counter Value: <%= getCounter() %></p>

   <%
        // embebed java code example (Scriptlet)
        String userName = "Carlos Martel";
        int age = 35;
   %>

   <p>User Name: <%= userName %></p>
   <p>User Age: <%= age %></p>

   <%
        int num1 = 10;
        int num2 = 20;
        int sum = num1 + num2;
   %>
   <%
        String role = "admin";
        if(role.equals("admin")) {
   %>
        <p>Welcome, Administrator!</p>
   <%
        } else {
   %>
        <p>Welcome, User!</p>
   <%
        }
   %>
   <ul>
   <%
        int i = 1;
        while (i <= 5) {
   %>
        <li>Item <%= i %></li>
   <%
        i++;
        }
   %>
   </ul>


<%@ include file="partials/footer.jsp" %>
