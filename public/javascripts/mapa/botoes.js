/*-INICIO-BOTOES----------------------------------------------------------------------------------*/

/*-CADA-BOTAO-APERTADO-CHAMA-O-METODO-que-esta-no-arquivo-verificadores.js------------------------*/

//Auxiliar do texto contido no input se selecao de porte da unidade
$("#escala-valores").val("Pequeno, Médio e Grande");

//Escala input range
$("#escala").slider({
	  animate: false,
	  range: true, 
	  min: 1, 
	  max: 4, 
	  step: 1,
	  values: [1, 4], 
	  slide: function(event, ui) {},
	  change: function( event, ui ) {

		  //Pequeno e Grande
		  if (($("#escala").slider("values", 0) == 1) && ($("#escala").slider("values", 1) == 2)) {
			  escalaInicialSelecionada = 1;
			  escalaFinalSelecionada = 1000000;
			  $("#escala-valores").val("Pequeno");	
			  porteUnidade = "Pequeno";
		  } else if (($("#escala").slider("values", 0) == 1) && ($("#escala").slider("values", 1) == 3)) {
			  escalaInicialSelecionada = 1;
			  escalaFinalSelecionada = 5000000;
			  $("#escala-valores").val("Pequeno e Médio");
			  porteUnidade = "Pequeno e Médio";
		  } else if (($("#escala").slider("values", 0) == 1) && ($("#escala").slider("values", 1) == 4)) {
			  escalaInicialSelecionada = 1;
			  escalaFinalSelecionada = 5000000000;
			  $("#escala-valores").val("Pequeno, Médio e Grande");
			  porteUnidade = "Pequeno, Médio e Grande";
		  } else if (($("#escala").slider("values", 0) == 2) && ($("#escala").slider("values", 1) == 4)) {
			  escalaInicialSelecionada = 1000001;
			  escalaFinalSelecionada = 5000000000;
			  $("#escala-valores").val("Médio e Grande");
			  porteUnidade = "Médio e Grande";
		  } else if (($("#escala").slider("values", 0) == 3) && ($("#escala").slider("values", 1) == 4)) {
			  escalaInicialSelecionada = 5000001;
			  escalaFinalSelecionada = 5000000000;
			  $("#escala-valores").val("Grande");
			  porteUnidade = "Grande";
		  } else if (($("#escala").slider("values", 0) == 2) && ($("#escala").slider("values", 1) == 3)) {
			  escalaInicialSelecionada = 1000001;
			  escalaFinalSelecionada = 5000000;
			  $("#escala-valores").val("Médio");
			  porteUnidade = "Médio";
		  } 
		  
		  validadorEscala = true;

		 //Verifica todos os botoes
		 verificaBotoes();
	  }
});

//Botao Operando
$("#situacaoOperando").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass("btn-default");
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass("btn-success");
		$(this).parent().toggleClass("btn-default");
	}
		
	//Verifica todos os botoes
	verificaBotoes();
});

//Botao Construindo
$("#situacaoConstruindo").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass("btn-default");
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass("btn-success");
		$(this).parent().toggleClass("btn-default");
	}
	
	verificaBotoes();
});

//Botao Reformando
$("#situacaoReformando").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$("#situacaoReformando").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
	
	verificaBotoes();
});

//Botao 2003
$("#ano2003").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2003").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2004
$("#ano2004").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2004").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2005
$("#ano2005").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2005").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2006
$("#ano2006").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	} else {
		$("#ano2006").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2007
$("#ano2007").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2007").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2008
$("#ano2008").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2008").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2009
$("#ano2009").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2009").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2010
$("#ano2010").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2010").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2011
$("#ano2011").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2011").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2012
$("#ano2012").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2012").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2013
$("#ano2013").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2013").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2014
$("#ano2014").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2014").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2015
$("#ano2015").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2015").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
			
	verificaBotoes();
	
});

//Botao 2016
$("#ano2016").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");	
						
	}
	else {
		$("#ano2016").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");	
	}
		
	verificaBotoes();

});

//Botao 2017
$("#ano2017").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
			
	}
	else {
		$("#ano2017").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
		
	verificaBotoes();

});

//Botao 2018
$("#ano2018").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
				
	}
	else {
		$("#ano2018").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");	
	}
		
	verificaBotoes();

});

//Botao 2019
$("#ano2019").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");	
						
	}
	else {
		$("#ano2019").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
		
	verificaBotoes();

});

//Botao 2020
$("#ano2020").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");	
						
	}
	else {
		$("#ano2020").attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}
	
	verificaBotoes();
		
});

// INICIO - APLICACOES DA UNIDADE----------------------
//Botao Aplicacao Termica
$("#aplicacaoTermica").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});

//Botao Aplicacao Eletrica
$("#aplicacaoEletrica").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");	
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});

//Botao Aplicacao Mecanica
$("#aplicacaoMecanica").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});

//Botao Aplicacao Biometano
$("#aplicacaoBiometano").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});
// FIM - APLICACOES DA UNIDADE--------------------------

//Botao Categoria Agropecuaria
$("#agropecuaria").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});

//Botao Categoria Industria
$("#industria").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});

//Botao Categoria Esgoto
$("#esgoto").change( function() {
	
	if ($(this).is(":checked")) {
		$(this).parent().removeClass('btn-default');
		$(this).parent().toggleClass("btn-success");
	}
	else {
		$(this).parent().attr("checked", false);
		$(this).parent().removeClass('btn-success');
		$(this).parent().toggleClass("btn-default");
	}

	verificaBotoes();
});

//Select dos estados
$("#estados").change( function() {

    //precisa zerar o municio se nao o filtro para de funcionar
    municipioId = 0;

	estadoId = $("#estados option:selected").val();
	let optionMunicipios = "";

	$("#municipios").html("");

	if (estadoId > 0) {

        $.getJSON("municipios/por/estado/" + estadoId, function(data) {

            $("#municipios").attr("disabled", false);

            optionMunicipios += '<option value="0">Selecione o Município</option> \n';

            $.each(data, function(key, val) {

                optionMunicipios += '<option id="municipio-' + val.id + '" value= "' + val.id + '">' + val.nome + '</option> \n';

            });

            $("#municipios").append(optionMunicipios);

        });

	} else {

        //precisa zerar o municio se nao o filtro para de funcionar
		municipioId = 0;

		optionMunicipios += '<option value="0">Selecione o Município</option> \n';

		$("#municipios").attr("disabled", true);
		$("#municipios").css('cursor', 'default');

		$("#municipios").append(optionMunicipios);

	}

	verificaBotoes();

});

//Select dos municipios
$("#municipios").change(function() {

	municipioId = $("#municipios option:selected").val();
	municipio = $("#municipios option:selected").text();

	if (municipioId === 0) {
		municipio = "Todos"
	}

	verificaBotoes();
	
});

//Botao de limpar do menu sidebar
$("#button-clear").click( function() {

	//INICIO - Situacao
	if ($("#situacaoOperando").is(":checked")) {		
		  $("#situacaoOperando").parent().removeClass("btn-success active");
		  $("#situacaoOperando").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#situacaoConstruindo").is(":checked")) {		
		  $("#situacaoConstruindo").parent().removeClass("btn-success active");
		  $("#situacaoConstruindo").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#situacaoReformando").is(":checked")) {		
		  $("#situacaoReformando").parent().removeClass("btn-success active");
		  $("#situacaoReformando").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	//FIM - Situacao
		
	//INICIO - Aplicacao
	if ($("#aplicacaoTermica").is(":checked")) {		
		  $("#aplicacaoTermica").parent().removeClass("btn-success active");
		  $("#aplicacaoTermica").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#aplicacaoEletrica").is(":checked")) {		
		  $("#aplicacaoEletrica").parent().removeClass("btn-success active");
		  $("#aplicacaoEletrica").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#aplicacaoMecanica").is(":checked")) {		
		  $("#aplicacaoMecanica").parent().removeClass("btn-success active");
		  $("#aplicacaoMecanica").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#aplicacaoBiometano").is(":checked")) {		
		  $("#aplicacaoBiometano").parent().removeClass("btn-success active");
		  $("#aplicacaoBiometano").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	//FIM - Aplicacao
		
	//INICIO - Anos
	if ($("#ano2003").is(":checked")) {		
		  $("#ano2003").parent().removeClass("btn-success active");
		  $("#ano2003").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2004").is(":checked")) {		
		  $("#ano2004").parent().removeClass("btn-success active");
		  $("#ano2004").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2005").is(":checked")) {		
		  $("#ano2005").parent().removeClass("btn-success active");
		  $("#ano2005").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2006").is(":checked")) {		
		  $("#ano2006").parent().removeClass("btn-success active");
		  $("#ano2006").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2007").is(":checked")) {		
		  $("#ano2007").parent().removeClass("btn-success active");
		  $("#ano2007").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2008").is(":checked")) {		
		  $("#ano2008").parent().removeClass("btn-success active");
		  $("#ano2008").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2009").is(":checked")) {		
		  $("#ano2009").parent().removeClass("btn-success active");
		  $("#ano2009").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2010").is(":checked")) {		
		  $("#ano2010").parent().removeClass("btn-success active");
		  $("#ano2010").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2011").is(":checked")) {		
		  $("#ano2011").parent().removeClass("btn-success active");
		  $("#ano2011").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2012").is(":checked")) {		
		  $("#ano2012").parent().removeClass("btn-success active");
		  $("#ano2012").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2013").is(":checked")) {		
		  $("#ano2013").parent().removeClass("btn-success active");
		  $("#ano2013").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2014").is(":checked")) {		
		  $("#ano2014").parent().removeClass("btn-success active");
		  $("#ano2014").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2015").is(":checked")) {		
		  $("#ano2015").parent().removeClass("btn-success active");
		  $("#ano2015").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2016").is(":checked")) {		
		  $("#ano2016").parent().removeClass("btn-success active");
		  $("#ano2016").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2017").is(":checked")) {		
		  $("#ano2017").parent().removeClass("btn-success active");
		  $("#ano2017").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2018").is(":checked")) {		
		  $("#ano2018").parent().removeClass("btn-success active");
		  $("#ano2018").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2019").is(":checked")) {		
		  $("#ano2019").parent().removeClass("btn-success active");
		  $("#ano2019").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#ano2020").is(":checked")) {		
		  $("#ano2020").parent().removeClass("btn-success active");
		  $("#ano2020").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	//INICIO - Anos
	
	//INICIO - Categoria
	if ($("#agropecuaria").is(":checked")) {		
		  $("#agropecuaria").parent().removeClass("btn-success active");
		  $("#agropecuaria").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#industria").is(":checked")) {		
		  $("#industria").parent().removeClass("btn-success active");
		  $("#industria").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	
	if ($("#esgoto").is(":checked")) {		
		  $("#esgoto").parent().removeClass("btn-success active");
		  $("#esgoto").parent().toggleClass("btn-default");	
		  $( this ).prop('checked', false);
	} 
	//FIM - Categoria

	$("#estados").val("0");
	$("#municipios").val("0");
	$("#municipios").html("");
	let optionMunicipios = "";
	optionMunicipios += '<option value="0">Selecione o Município</option> \n';
	$("#municipios").append(optionMunicipios);
	$("#municipios").attr("disabled", true);
	$("#municipios").css('cursor', 'default');
		
	$("#escala").slider({
		  animate: true,
		  range: true, 
		  min: 1, 
		  max: 4, 
		  step: 1,
		  values: [1, 4], 
		  slide: function(event, ui) {
			  $("#escala-valores").val(ui.values[0] + " - " + ui.values[1]);
		  }
	});
		
	$("#escala-valores").val("Pequeno, Médio e Grande");
			
	//Busca todas as unidades
	location.reload();
		
});
/*-FIM-BOTOES----------------------------------------------------------------------------------*/

//Funcao que bloqueia os botoes
function bloqueioBotoes() {
		
	if ($('#situacaoOperando').prop('checked') == false) {
		$('#situacaoOperando').attr('disabled', 'disabled');
		$('#situacaoOperando').parent().attr("disabled", true);
		$('#situacaoOperando').prop('disabled', true);
	}
		
	if ($('#situacaoConstruindo').prop('checked') == false) {
		$('#situacaoConstruindo').attr('disabled', 'disabled');
		$('#situacaoConstruindo').parent().attr("disabled", true);
		$('#situacaoConstruindo').prop('disabled', true);
	}
	
	if ($('#situacaoReformando').prop('checked') == false) {
		$('#situacaoReformando').attr('disabled', 'disabled');
		$('#situacaoReformando').parent().attr("disabled", true);
		$('#situacaoReformando').prop('disabled', true);
	}
	
	if ($('#biodigestor').prop('checked') == false) {
		$('#biodigestor').attr('disabled', 'disabled');
		$('#biodigestor').parent().attr("disabled", true);
		$('#biodigestor').prop('disabled', true);
	
	}
	
	if ($('#compostagem').prop('checked') == false) {
		$('#compostagem').attr('disabled', 'disabled');
		$('#compostagem').parent().attr("disabled", true);
		$('#compostagem').prop('disabled', true);
	
	}	
	
	if ($('#ano2003').prop('checked') == false) {		
		$('#ano2003').attr('disabled', 'disabled');
		$('#ano2003').parent().attr("disabled", true);
		$('#ano2003').prop('disabled', true);
	} 
	
	if ($('#ano2004').prop('checked') == false) {		
		$('#ano2004').attr('disabled', 'disabled');
		$('#ano2004').parent().attr("disabled", true);
		$('#ano2004').prop('disabled', true);
	} 
	
	if ($('#ano2005').prop('checked') == false) {		
		$('#ano2005').attr('disabled', 'disabled');
		$('#ano2005').parent().attr("disabled", true);
		$('#ano2005').prop('disabled', true);
	} 
	
	if ($('#ano2006').prop('checked') == false) {		
		$('#ano2006').attr('disabled', 'disabled');
		$('#ano2006').parent().attr("disabled", true);
		$('#ano2006').prop('disabled', true);
	} 
	
	if ($('#ano2007').prop('checked') == false) {		
		$('#ano2007').attr('disabled', 'disabled');
		$('#ano2007').parent().attr("disabled", true);
		$('#ano2007').prop('disabled', true);
	} 
	
	if ($('#ano2008').prop('checked') == false) {		
		$('#ano2008').attr('disabled', 'disabled');
		$('#ano2008').parent().attr("disabled", true);
		$('#ano2008').prop('disabled', true);
	} 
	
	if ($('#ano2009').prop('checked') == false) {		
		$('#ano2009').attr('disabled', 'disabled');
		$('#ano2009').parent().attr("disabled", true);
		$('#ano2009').prop('disabled', true);
	} 
	
	if ($('#ano2010').prop('checked') == false) {		
		$('#ano2010').attr('disabled', 'disabled');
		$('#ano2010').parent().attr("disabled", true);
		$('#ano2010').prop('disabled', true);
	} 
	
	if ($('#ano2011').prop('checked') == false) {		
		$('#ano2011').attr('disabled', 'disabled');
		$('#ano2011').parent().attr("disabled", true);
		$('#ano2011').prop('disabled', true);
	} 
	
	if ($('#ano2012').prop('checked') == false) {		
		$('#ano2012').attr('disabled', 'disabled');
		$('#ano2012').parent().attr("disabled", true);
		$('#ano2012').prop('disabled', true);
	} 
	
	if ($('#ano2013').prop('checked') == false) {		
		$('#ano2013').attr('disabled', 'disabled');
		$('#ano2013').parent().attr("disabled", true);
		$('#ano2013').prop('disabled', true);
	} 
	
	if ($('#ano2014').prop('checked') == false) {		
		$('#ano2014').attr('disabled', 'disabled');
		$('#ano2014').parent().attr("disabled", true);
		$('#ano2014').prop('disabled', true);
	} 
	
	if ($('#ano2015').prop('checked') == false) {		
		$('#ano2015').attr('disabled', 'disabled');
		$('#ano2015').parent().attr("disabled", true);
		$('#ano2015').prop('disabled', true);
	} 
	
	if ($('#ano2016').prop('checked') == false) {		
		$('#ano2016').attr('disabled', 'disabled');
		$('#ano2016').parent().attr("disabled", true);
		$('#ano2016').prop('disabled', true);
	} 
	
	if ($('#ano2017').prop('checked') == false) {		
		$('#ano2017').attr('disabled', 'disabled');
		$('#ano2017').parent().attr("disabled", true);
		$('#ano2017').prop('disabled', true);
	} 
	
	if ($('#ano2018').prop('checked') == false) {		
		$('#ano2018').attr('disabled', 'disabled');
		$('#ano2018').parent().attr("disabled", true);
		$('#ano2018').prop('disabled', true);
	} 
	
	if ($('#ano2019').prop('checked') == false) {		
		$('#ano2019').attr('disabled', 'disabled');
		$('#ano2019').parent().attr("disabled", true);
		$('#ano2019').prop('disabled', true);
	} 
	
	if ($('#ano2020').prop('checked') == false) {		
		$('#ano2020').attr('disabled', 'disabled');
		$('#ano2020').parent().attr("disabled", true);
		$('#ano2020').prop('disabled', true);
	} 
	
	if ($('#aplicacaoTermica').prop('checked') == false) {		
		$('#aplicacaoTermica').attr('disabled', 'disabled');
		$('#aplicacaoTermica').parent().attr("disabled", true);
		$('#aplicacaoTermica').prop('disabled', true);
	} 
	
	if ($('#aplicacaoEletrica').prop('checked') == false) {		
		$('#aplicacaoEletrica').attr('disabled', 'disabled');
		$('#aplicacaoEletrica').parent().attr("disabled", true);
		$('#aplicacaoEletrica').prop('disabled', true);
	} 
	
	if ($('#aplicacaoMecanica').prop('checked') == false) {		
		$('#aplicacaoMecanica').attr('disabled', 'disabled');
		$('#aplicacaoMecanica').parent().attr("disabled", true);
		$('#aplicacaoMecanica').prop('disabled', true);
	} 
	
	if ($('#aplicacaoBiometano').prop('checked') == false) {		
		$('#aplicacaoBiometano').attr('disabled', 'disabled');
		$('#aplicacaoBiometano').parent().attr("disabled", true);
		$('#aplicacaoBiometano').prop('disabled', true);
	} 
	
	if ($('#agropecuaria').prop('checked') == false) {		
		$('#agropecuaria').attr('disabled', 'disabled');
		$('#agropecuaria').parent().attr("disabled", true);
		$('#agropecuaria').prop('disabled', true);
	} 
	
	if ($('#industria').prop('checked') == false) {		
		$('#industria').attr('disabled', 'disabled');
		$('#industria').parent().attr("disabled", true);
		$('#industria').prop('disabled', true);
	} 
	
	if ($('#esgoto').prop('checked') == false) {		
		$('#esgoto').attr('disabled', 'disabled');
		$('#esgoto').parent().attr("disabled", true);
		$('#esgoto').prop('disabled', true);
	} 
		
	$('#campoSituacao').css('pointer-events','none');
	$('#campoAnos').css('pointer-events','none');
	$('#campoAplicacao').css('pointer-events','none');
	$('#campoCategoria').css('pointer-events','none');
	$('#escala').css('pointer-events','none');

}

//Funcao que desbloqueia os botoes
function desbloqueioBotoes() {
			
	$('#situacaoOperando').removeAttr('disabled', 'disabled');
	$('#situacaoOperando').parent().attr("disabled", false);
	$('#situacaoOperando').prop("disabled", false);

	$('#situacaoConstruindo').removeAttr('disabled', 'disabled');
	$('#situacaoConstruindo').parent().attr("disabled", false);
	$('#situacaoConstruindo').prop("disabled", false);
	
	$('#situacaoReformando').removeAttr('disabled', 'disabled');
	$('#situacaoReformando').parent().attr("disabled", false);
	$('#situacaoReformando').prop("disabled", false);
	
	$('#biodigestor').removeAttr('disabled', 'disabled');
	$('#biodigestor').parent().attr("disabled", false);
	$('#biodigestor').prop("disabled", false);
		
	$('#ano2003').removeAttr('disabled', 'disabled');
	$('#ano2003').parent().attr("disabled", false);
	$('#ano2003').prop("disabled", false);
	
	$('#ano2004').removeAttr('disabled', 'disabled');
	$('#ano2004').parent().attr("disabled", false);
	$('#ano2004').prop("disabled", false);
	
	$('#ano2005').removeAttr('disabled', 'disabled');
	$('#ano2005').parent().attr("disabled", false);
	$('#ano2005').prop("disabled", false);
	
	$('#ano2006').removeAttr('disabled', 'disabled');
	$('#ano2006').parent().attr("disabled", false);
	$('#ano2006').prop("disabled", false);
	
	$('#ano2007').removeAttr('disabled', 'disabled');
	$('#ano2007').parent().attr("disabled", false);
	$('#ano2007').prop("disabled", false);
	
	$('#ano2008').removeAttr('disabled', 'disabled');
	$('#ano2008').parent().attr("disabled", false);
	$('#ano2008').prop("disabled", false);
	
	$('#ano2009').removeAttr('disabled', 'disabled');
	$('#ano2009').parent().attr("disabled", false);
	$('#ano2009').prop("disabled", false);
	
	$('#ano2010').removeAttr('disabled', 'disabled');
	$('#ano2010').parent().attr("disabled", false);
	$('#ano2010').prop("disabled", false);
	
	$('#ano2011').removeAttr('disabled', 'disabled');
	$('#ano2011').parent().attr("disabled", false);
	$('#ano2011').prop("disabled", false);
	
	$('#ano2012').removeAttr('disabled', 'disabled');
	$('#ano2012').parent().attr("disabled", false);
	$('#ano2012').prop("disabled", false);
	
	$('#ano2013').removeAttr('disabled', 'disabled');
	$('#ano2013').parent().attr("disabled", false);
	$('#ano2013').prop("disabled", false);
	
	$('#ano2014').removeAttr('disabled', 'disabled');
	$('#ano2014').parent().attr("disabled", false);
	$('#ano2014').prop("disabled", false);
	
	$('#ano2015').removeAttr('disabled', 'disabled');
	$('#ano2015').parent().attr("disabled", false);
	$('#ano2015').prop("disabled", false);
	
	$('#ano2016').removeAttr('disabled', 'disabled');
	$('#ano2016').parent().attr("disabled", false);
	$('#ano2016').prop("disabled", false);
	
	$('#ano2017').removeAttr('disabled', 'disabled');
	$('#ano2017').parent().attr("disabled", false);
	$('#ano2017').prop("disabled", false);
	
	$('#ano2018').removeAttr('disabled', 'disabled');
	$('#ano2018').parent().attr("disabled", false);
	$('#ano2018').prop("disabled", false);
	
	$('#ano2019').removeAttr('disabled', 'disabled');
	$('#ano2019').parent().attr("disabled", false);
	$('#ano2019').prop("disabled", false);
	
	$('#ano2020').removeAttr('disabled', 'disabled');
	$('#ano2020').parent().attr("disabled", false);
	$('#ano2020').prop("disabled", false);
	
	$('#aplicacaoBiometano').removeAttr('disabled', 'disabled');
	$('#aplicacaoBiometano').parent().attr("disabled", false);
	$('#aplicacaoBiometano').prop("disabled", false);
	
	$('#aplicacaoTermica').removeAttr('disabled', 'disabled');
	$('#aplicacaoTermica').parent().attr("disabled", false);
	$('#aplicacaoTermica').prop("disabled", false);
	
	$('#aplicacaoEletrica').removeAttr('disabled', 'disabled');
	$('#aplicacaoEletrica').parent().attr("disabled", false);
	$('#aplicacaoEletrica').prop("disabled", false);
	
	$('#aplicacaoMecanica').removeAttr('disabled', 'disabled');
	$('#aplicacaoMecanica').parent().attr("disabled", false);
	$('#aplicacaoMecanica').prop("disabled", false);
	
	$('#agropecuaria').removeAttr('disabled', 'disabled');
	$('#agropecuaria').parent().attr("disabled", false);
	$('#agropecuaria').prop("disabled", false);
	
	$('#industria').removeAttr('disabled', 'disabled');
	$('#industria').parent().attr("disabled", false);
	$('#industria').prop("disabled", false);
	
	$('#esgoto').removeAttr('disabled', 'disabled');
	$('#esgoto').parent().attr("disabled", false);
	$('#esgoto').prop("disabled", false);
			
	$('#campoSituacao').css('pointer-events','auto');
	$('#campoAnos').css('pointer-events','auto');
	$('#campoAplicacao').css('pointer-events','auto');
	$('#campoCategoria').css('pointer-events','auto');
	$('#escala').css('pointer-events','auto');
	
}