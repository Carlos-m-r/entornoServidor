<%@ include file="../../partials/header.jsp" %>

<h1>Listado de Comunidades Autónomas</h1>

<a href="regions?action=new">Agregar nueva Comunidad Autónoma</a>


<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Código</th>
            <th>Nombre</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="region" items="${listRegions}">
            <tr>
                <td>${region.id}</td>
                <td>${region.code}</td>
                <td>${region.name}</td>
                <td>
                    <a href="regions?action=edit&id=${region.id}">Editar</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="../../partials/footer.jsp" %>