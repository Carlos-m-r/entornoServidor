<%@ include file="../../partials/header.jsp" %>

<h1>
    <!--
        Título dinámico:
        Si "region" es null -> muestra "Nueva Comunidad Autónoma".
        Si "region" no es null -> muestra "Editar Comunidad Autónoma".
     -->
    <c:out value="${region == null ? 'Nueva Comunidad Autónoma' : 'Editar Comunidad Autónoma'}" />
</h1>

<!-- Mostrar mensaje de error si existe en el modelo -->
<c:if test="${not empty errorMessage}">
    <div class="error-message">${errorMessage}</div>
</c:if>

<form action="regions" method="post">
    <input type="hidden" name="id" value="${region != null ? region.id : ''}" />
    <input type="hidden" name="action" value="${region == null ? 'insert' : 'update'}" />
    <label for="code">Código:</label>
    <input type="text" name="code" id="code"
           value="${region != null ? region.code : ''}"
           required />
    <label for="name">Nombre:</label>
    <input type="text" name="name" id="name"
           value="${region != null ? region.name : ''}"
           required />
    <input type="submit" value="${region == null ? 'Crear' : 'Actualizar'}" />
</form>

<!-- Enlace para volver a la lista de regiones -->
<a href="regions">Volver a la lista</a>

<%@ include file="../../partials/footer.jsp" %>
<!-- Incluye el pie de página común (HTML de cierre, scripts, etc.) -->