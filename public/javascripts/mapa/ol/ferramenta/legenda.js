/*-INICIO-LEGENDAS-----------------------------------------------------------------------------*/

//Instancia e característica da legenda
function addLegenda(div, auto) {
	
	//Pontos do mapa
	let iconPequeno = 'assets/images/verde.png';
	let iconMedio = 'assets/images/amarelo.png';
	let iconGrande = 'assets/images/azul.png';

	let html = '';

	html += '<div id="conteiner-layerSwitch" title="Produção de Biogás">';
	html += '<p class="mdl-card__subtitle-text" style="font-size: 0.8em;"><img src="'+iconPequeno+'" border="0" alt="icon_pequeno"> <b>Pequeno</b> ( < 1.000.000 Nm³/ano)</p>';
	html += '<hr class="separador-legenda">';
	html += '<p class="mdl-card__subtitle-text" style="font-size: 0.73em;"><img src="'+iconMedio+'" border="0" alt="icon_medio"> <b>Médio</b> (1.000.001 a 5.000.000 Nm³/ano)</p>';
	html += '<hr class="separador-legenda">';
	html += '<p class="mdl-card__subtitle-text" style="font-size: 0.8em;"><img src="'+iconGrande+'" border="0" alt="icon_grande"> <b>Grande</b> ( > 5.000.001 Nm³/ano)</p>';

	html += '</div>';

	div.html(html);

	//instanciar dialog
	$("#conteiner-layerSwitch").dialog({ 
		position: {my: 'right bottom', at: 'right bottom-176', of: window},
		width : 249,
		autoOpen : false, 
		resizable: false,
		draggable: true,
		dialogClass: 'legenda-dialog'});
		
}

//Abre o modal de legendas
function openLegenda() {
	
	$(".ui-dialog-titlebar-close").addClass("btn btn-success");

	$(".ui-dialog-titlebar-close").html('<span class="glyphicon glyphicon-remove"></span> ');
	
	$("#conteiner-layerSwitch").dialog("open");

}

/*-FIM-LEGENDAS-----------------------------------------------------------------------------*/