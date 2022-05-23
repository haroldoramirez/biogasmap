// Verifica Todos os botoes
function verificaBotoes() {
			
	//Limpar o popup se estiver aberto
	overlay.setPosition(undefined);
	$('#popup-closer').blur();
	
	validadorSituacao = false;
	validadorAplicacao = false;
	validadorCategoria = false;
	validadorAno = false;
	validadorEstado = false;
	validadorMunicipio = false;
	
	situacaoOperando = 0;
	situacaoConstruindo = 0; 
	situacaoReformando = 0;
		
	aplicacaoTermica = null;
	aplicacaoEletrica = null;
	aplicacaoMecanica = null;
	aplicacaoBiometano = null;
		
	//Categoria da Unidade
	categoriaAgropecuaria = 0;
	categoriaIndustria = 0;
	categoriaEsgoto = 0;
		
	ano2003 = null; 
	ano2004 = null; 
	ano2005 = null; 
	ano2006 = null; 
	ano2007 = null; 
	
	ano2008 = null; 
	ano2009 = null; 
	ano2010 = null; 
	ano2011 = null; 
	ano2012 = null; 
	
	ano2013 = null; 
	ano2014 = null; 
	ano2015 = null; 
	ano2016 = null; 
	ano2017 = null; 
	
	ano2018 = null; 
	ano2019 = null; 
	ano2020 = null;
	
	periodoRelatorio = "";

	//Pega os dados do campo Escala - simplificados	
	escalaInicial = escalaInicialSelecionada;
	escalaFinal = escalaFinalSelecionada;
		
	//Zera a lista de unidades
	unidades = [];
		
	//BOTOES A SEREM SETADOS
	if ($("#situacaoOperando").is(":checked")) {
		situacaoOperando = 1;
	} 
	
	if ($("#situacaoConstruindo").is(":checked")) {
		situacaoConstruindo = 2;
	} 
		
	if ($("#situacaoReformando").is(":checked")) {
		situacaoReformando = 3;
	} 
				
	//Verificador de Situacoes
	if ((!$("#situacaoOperando").is(":checked") 
			&& !$("#situacaoConstruindo").is(":checked") 
			&& !$("#situacaoReformando").is(":checked"))) {
		
		situacaoOperando = 1;
		situacaoConstruindo = 2;
		situacaoReformando = 3;		

	} else {
		
		validadorSituacao = true;
		
	}
	
	//Check aplicacaoTermica
	if ($("#aplicacaoTermica").is(":checked")) {

		aplicacaoTermica = true;  	
	}
	
	//Check aplicacaoEletrica
	if ($("#aplicacaoEletrica").is(":checked")) {

		aplicacaoEletrica = true;
		
	} 
	
	//Check aplicacaoMecanica
	if ($("#aplicacaoMecanica").is(":checked")) {

		aplicacaoMecanica = true;

	} 
	
	//Check aplicacaoBiometano
	if ($("#aplicacaoBiometano").is(":checked")) {

		aplicacaoBiometano = true;  
		
	} 
	
	//Verificador de Aplicacao
	if ((!$("#aplicacaoTermica").is(":checked") 
			&& !$("#aplicacaoEletrica").is(":checked") 
			&& !$("#aplicacaoMecanica").is(":checked") 
			&& !$("#aplicacaoBiometano").is(":checked"))) {
		
		//Aplicacao
		aplicacaoTermica = true;
		aplicacaoEletrica = true;
		aplicacaoMecanica = true;
		aplicacaoBiometano = true;

	} else {
		
		validadorAplicacao = true;
		
	}
	
	//Categoria agropecuaria
	if ($("#agropecuaria").is(":checked")) {

		// Agropecuaria pega o ID 
		categoriaAgropecuaria = 1;

	} 
	
	//Categoria Industria
	if ($("#industria").is(":checked")) {

		//pega o ID  
		categoriaIndustria = 2;

	} 
	
	//Categoria esgoto
	if ($("#esgoto").is(":checked")) {

		//pega o ID  
		categoriaEsgoto = 3;

	} 
	
	//Verificador de categorias
	if (!$("#agropecuaria").is(":checked") 
			&& !$("#industria").is(":checked")
			&& !$("#esgoto").is(":checked")) {
		
		// categoria
		categoriaAgropecuaria = 1;
		categoriaIndustria = 2;
		categoriaEsgoto = 3;

	} else {
		validadorCategoria = true;		
	}
	
	//ano
	if ($("#ano2003").is(":checked")) {
		
		ano2003 = "2003";
		periodoRelatorio = periodoRelatorio.concat(operacao2003);
		
	} 
	
	//ano
	if ($("#ano2004").is(":checked")) {
		
		ano2004 = "2004";
		periodoRelatorio = periodoRelatorio.concat(operacao2004);
		
	} 
	
	//ano
	if ($("#ano2005").is(":checked")) {
		
		ano2005 = "2005";
		periodoRelatorio = periodoRelatorio.concat(operacao2005);
		
	} 
	
	//ano
	if ($("#ano2006").is(":checked")) {
		
		ano2006 = "2006";
		periodoRelatorio = periodoRelatorio.concat(operacao2006);
		
	} 
	
	//ano
	if ($("#ano2007").is(":checked")) {
		
		ano2007 = "2007";
		periodoRelatorio = periodoRelatorio.concat(operacao2007);
		
	} 
	
	//ano
	if ($("#ano2008").is(":checked")) {
		
		
		ano2008 = "2008";
		periodoRelatorio = periodoRelatorio.concat(operacao2008);
		
	} 
	
	//ano
	if ($("#ano2009").is(":checked")) {
		
		ano2009 = "2009";
		periodoRelatorio = periodoRelatorio.concat(operacao2009);
		
	} 
	
	//ano
	if ($("#ano2010").is(":checked")) {
		
		ano2010 = "2010";
		periodoRelatorio = periodoRelatorio.concat(operacao2010);
		
	} 
	
	//ano
	if ($("#ano2011").is(":checked")) {
		
		ano2011 = "2011";
		periodoRelatorio = periodoRelatorio.concat(operacao2011);
		
	} 
	
	//ano
	if ($("#ano2012").is(":checked")) {
		
		ano2012 = "2012";
		periodoRelatorio = periodoRelatorio.concat(operacao2012);
		
	} 
	
	//ano
	if ($("#ano2013").is(":checked")) {
		
		
		ano2013 = "2013";
		periodoRelatorio = periodoRelatorio.concat(operacao2013);
		
	} 
	
	//ano
	if ($("#ano2014").is(":checked")) {
		
		ano2014 = "2014";
		periodoRelatorio = periodoRelatorio.concat(operacao2014);
		
	} 
	
	//ano
	if ($("#ano2015").is(":checked")) {
		
		ano2015 = "2015";
		periodoRelatorio = periodoRelatorio.concat(operacao2015);
		
	} 
	
	if ($("#ano2016").is(":checked")) {
		
		ano2016 = "2016";
		periodoRelatorio = periodoRelatorio.concat(operacao2016);
		
	} 
	
	if ($("#ano2017").is(":checked")) {
		
		ano2017 = "2017";
		periodoRelatorio = periodoRelatorio.concat(operacao2017);
		
	} 
	
	if ($("#ano2018").is(":checked")) {
		
		ano2018 = "2018";
		periodoRelatorio = periodoRelatorio.concat(operacao2018);
		
	} 
	
	if ($("#ano2019").is(":checked")) {
		
		ano2019 = "2019";
		periodoRelatorio = periodoRelatorio.concat(operacao2019);
		
	} 
	
	if ($("#ano2020").is(":checked")) {
		
		ano2020 = "2020";
		periodoRelatorio = periodoRelatorio.concat(operacao2020);
		
	} 
 	
	//Verificador de Ano
	if (!$("#ano2003").is(":checked")
			&& !$("#ano2004").is(":checked")
			&& !$("#ano2005").is(":checked")
			&& !$("#ano2006").is(":checked")
			&& !$("#ano2007").is(":checked")
			&& !$("#ano2008").is(":checked")
			&& !$("#ano2009").is(":checked")
			&& !$("#ano2010").is(":checked")
			&& !$("#ano2011").is(":checked")
			&& !$("#ano2012").is(":checked")
			&& !$("#ano2013").is(":checked")
			&& !$("#ano2014").is(":checked")
			&& !$("#ano2015").is(":checked")
			&& !$("#ano2016").is(":checked")
			&& !$("#ano2017").is(":checked")
			&& !$("#ano2018").is(":checked")
			&& !$("#ano2019").is(":checked")
			&& !$("#ano2020").is(":checked")) {
		
		ano2003 = "2003";
		ano2004 = "2004";
		ano2005 = "2005";
		ano2006 = "2006";
		ano2007 = "2007";
		
		ano2008 = "2008";
		ano2009 = "2009";
		ano2010 = "2010";
		ano2011 = "2011";
		ano2012 = "2012";
		
		ano2013 = "2013";
		ano2014 = "2014";
		ano2015 = "2015";
		ano2016 = "2016";
		ano2017 = "2017";
		
		ano2018 = "2018";
		ano2019 = "2019";
		ano2020 = "2020";
		
		periodoRelatorio = "2003 a 2019";
						
	} else {
		validadorAno = true;
	}
		
	if (estadoId !== 0) {
		validadorEstado = true;
	}
	
	if (municipioId !== 0) {
		validadorMunicipio = true;
		municipio = $("#municipios option:selected").text();
	}
								
	//caso nenhum botao for apertado/selecionado - chamar todas as unidades
	if (!$("#situacaoOperando").is(":checked") 
			&& !$("#situacaoConstruindo").is(":checked") 
			&& !$("#situacaoReformando").is(":checked")
			&& !$("#aplicacaoTermica").is(":checked")
			&& !$("#aplicacaoEletrica").is(":checked")
			&& !$("#aplicacaoMecanica").is(":checked")
			&& !$("#aplicacaoBiometano").is(":checked")
			&& !$("#agropecuaria").is(":checked")
			&& !$("#industria").is(":checked")
			&& !$("#esgoto").is(":checked")
			&& !$("#ano2003").is(":checked")
			&& !$("#ano2004").is(":checked")
			&& !$("#ano2005").is(":checked")
			&& !$("#ano2006").is(":checked")
			&& !$("#ano2007").is(":checked")
			&& !$("#ano2008").is(":checked")
			&& !$("#ano2009").is(":checked")
			&& !$("#ano2010").is(":checked")
			&& !$("#ano2011").is(":checked")
			&& !$("#ano2012").is(":checked")
			&& !$("#ano2013").is(":checked")
			&& !$("#ano2014").is(":checked")
			&& !$("#ano2015").is(":checked")
			&& !$("#ano2016").is(":checked")
			&& !$("#ano2017").is(":checked")
			&& !$("#ano2018").is(":checked")
			&& !$("#ano2019").is(":checked")
			&& !$("#ano2020").is(":checked") && (estadoId == 0) && (municipioId == 0) && (validadorEscala == false))  
	{
			
		getUnidadesByFilterScale(escalaInicialSelecionada, escalaFinalSelecionada);
		
	} else {
								
		getUnidadesByFilters();
	}
								
}