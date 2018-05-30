function atualizar(){
	
	var form = $(document.getElementById("form"));
	
	var id = document.getElementById("id");
	var visibilidade = document.getElementById("visibilidade");
	var conteudo = document.getElementById("conteudo");
	var atualizacao = document.getElementById("atualizacao");
	
	$.post("../ajax",form.serialize(),	function(data, status){
		if(status == "success"){
			conteudo.value = data.conteudo;
			atualizacao.innerHTML = data.atualizacao;
		}
	});
}

window.onload = function(e){
	
	var autosave = document.getElementById("autosave");
	var intervalId = 0;
	autosave.addEventListener("click", function(){
		
		if(autosave.checked){
			intervalId = setInterval(atualizar, 2000);
		} else {
			clearInterval(intervalId);
		}
	});
}