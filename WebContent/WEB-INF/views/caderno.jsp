<%@page import="main.java.br.ufc.qx.wed.dontpad.model.Caderno"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html >
<head>
	<meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/caderno.css"/>" />
  <title>Caderno:${caderno.nome}</title>
</head>

<body>
	Auto Save: <input type="checkbox" id="autosave"/>
	<p>Nome do caderno:<strong>${caderno.nome}</strong></p>
	<p>&Uacute;ltima atualiza&ccedil;&atilde;o:${caderno.ultimaAtualizacao}</p>

	<form method="POST" action="c/${caderno.nome}" id="form">

    <c:if test="${not empty usuario}">
    	<label for="visibilidade">Protegido:</label>
    	<input type="checkbox" name="visibilidade" value="on">
    	<textarea id="conteudo" name="conteudo">
		       ${caderno.conteudo}
	   </textarea>
    </c:if>

		<input type="submit" value="Salvar"/>
		<input type="hidden" id="id" name="id" value="${caderno.id}"/>

	<c:if test="${empty usuario}">

   <c:if test="${caderno.publico == false}">
       <img src="<c:url value="/static/img/privado.png"/>" alt="privado">
      <textarea id="conteudo" name="conteudo" disabled="disabled">
		       ${caderno.conteudo}
	   </textarea>

	</c:if>
      <c:if test="${caderno.publico == true}">
       <img src="<c:url value="/static/img/publico.png"/>" alt="publico">
      <textarea id="conteudo" name="conteudo">
		       ${caderno.conteudo}
	   </textarea>
    </c:if>
	</c:if>

	</form>
	
	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="<c:url value="/static/js/caderno.js"/>"></script>

</body>
</html>