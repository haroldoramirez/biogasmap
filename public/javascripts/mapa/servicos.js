/*-INICIO-SERVICOS-----------------------------------------------------------------------------*/

//Monta os pontos no mapa com os dados filtrados - o parametro unidades é um array com as unidades filtradas vindas do backend
function getFeatureMap(unidades) {

	layerPequeno = new ol.layer.Group({ visibleLayerSwitch: true, title: "Pequeno Porte", icon: iconPequeno });
	layerMedio = new ol.layer.Group({ visibleLayerSwitch: true, title: "Médio Porte",icon: iconMedio });
	layerGrande = new ol.layer.Group({ visibleLayerSwitch: true, title: "Grande Porte", icon: iconGrande });

    //Percorre a lista de unidades
    unidades.forEach(function(unidade) {

        //Adiciona a latitude e a longitude e o valor que ajuda na busca por codigo da unidade
        let iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([unidade.longitude,unidade.latitude], 'EPSG:4326', 'EPSG:3857')),
            id: unidade.codigo
        });

        //Seta o estilo baseado no intervalo/porte
        iconFeature.setStyle(getIconFeature(unidade.escala.intervalo));

        let vectorSourceUnidade = new ol.source.Vector({
            visibleLayerSwitch: false,
            features: [iconFeature]
        });

        let vectorLayerUnidade = new ol.layer.Vector({
            visibleLayerSwitch: false,
            source: vectorSourceUnidade
        });

        switch(unidade.escala.intervalo) {

        case 1:
            layerPequeno.getLayers().push(vectorLayerUnidade);
            break;
        case 2:
            layerMedio.getLayers().push(vectorLayerUnidade);
            break;
        case 3:
            layerGrande.getLayers().push(vectorLayerUnidade);
            break;
        default:
            {}
        }
    });

	map.addLayer(layerPequeno);
	map.addLayer(layerMedio);
	map.addLayer(layerGrande);

	//Arquivo /js/popup.js
	addPopup();

}

//Seta os estilos dos pontos - verde - amarelo - azul dependendo do porte
function getIconFeature(porte) {

	switch(porte) {

    case 1:
    	return new ol.style.Style({
    		image: new ol.style.Icon(({
    			anchor: [0.5, 46],
    			anchorXUnits: 'fraction',
    			anchorYUnits: 'pixels',
    			opacity: 0.85,
    			offset:[-18,-9],
    			size: [66,66],
    			src: iconPequeno
    		}))
    	});
    case 2:
    	return new ol.style.Style({
    		image: new ol.style.Icon(({
    			anchor: [0.5, 46],
    			anchorXUnits: 'fraction',
    			anchorYUnits: 'pixels',
    			opacity: 0.85,
    			offset:[-18,-9],
    			size: [66,66],
    			src: iconMedio
    		}))
    	});
    case 3:
    	return new ol.style.Style({
    		image: new ol.style.Icon(({
    			anchor: [0.5, 46],
    			anchorXUnits: 'fraction',
    			anchorYUnits: 'pixels',
    			opacity: 0.85,
    			offset:[-18,-9],
    			size: [66,66],
    			src: iconGrande
    		}))
    	});

    default:
        {}
	}

}

//BUSCA TODAS AS UNIDADES COM filtros de escala inicial e escala final de producao anual de biogas
function getUnidadesByFilterScale(escalaInicial, escalaFinal) {

	map.removeLayer(layerPequeno);
	map.removeLayer(layerMedio);		
	map.removeLayer(layerGrande);

	$.get("unidades/getUnidades", {

		//Parametros para o back-end
		escalaInicial:                          escalaInicial,
		escalaFinal:							escalaFinal

	}, function(data) {
		
		//Recebe os dados do back-end
		unidades = data;
        erro = 0;
				
		//monta os pontos no mapa
		getFeatureMap(unidades);

		//carrega o modal de reports com as unidades filtradas
		generateReports(unidades);

		validadorEscala = false;
		concatMunicipio = "Todos";

	}).fail(function(data, textStatus, xhr) {

	    erro = data.status;
	    console.log(data);
	    erro = 404;
	    $.toast({
	        heading: 'Biogásmap',
	        text: 'Ocorreu um erro ao carregar as unidades.',
	        position: 'bottom-center',
	        stack: false
	    })
		
	});
	
}

//BUSCA TODAS AS UNIDADES COM FILTROS
function getUnidadesByFilters() {

	map.removeLayer(layerPequeno);
	map.removeLayer(layerMedio);		
	map.removeLayer(layerGrande);

    //Objecto json que contem os parametros selecionados para realizar o filtro das unidades
	let filterParams = {
        "validadorSituacao":validadorSituacao,
        "validadorAplicacao":validadorAplicacao,
        "validadorCategoria":validadorCategoria,
        "validadorAno":validadorAno,
        "validadorEstado":validadorEstado,
        "validadorMunicipio":validadorMunicipio,
        "validadorEscala":validadorEscala,
        "situacaoOperando":situacaoOperando,
        "situacaoConstruindo":situacaoConstruindo,
        "situacaoReformando":situacaoReformando,
        "aplicacaoTermica":aplicacaoTermica,
        "aplicacaoEletrica":aplicacaoEletrica,
        "aplicacaoMecanica":aplicacaoMecanica,
        "aplicacaoBiometano":aplicacaoBiometano,
        "categoriaAgropecuaria":categoriaAgropecuaria,
        "categoriaIndustria":categoriaIndustria,
        "categoriaEsgoto":categoriaEsgoto,
        "estado":estadoId,
        "municipio":municipioId,
        "ano2003":ano2003,
        "ano2004":ano2004,
        "ano2005":ano2005,
        "ano2006":ano2006,
        "ano2007":ano2007,
        "ano2008":ano2008,
        "ano2009":ano2009,
        "ano2010":ano2010,
        "ano2011":ano2011,
        "ano2012":ano2012,
        "ano2013":ano2013,
        "ano2014":ano2014,
        "ano2015":ano2015,
        "ano2016":ano2016,
        "ano2017":ano2017,
        "ano2018":ano2018,
        "ano2019":ano2019,
        "ano2020":ano2020,
        "escalaInicial":escalaInicial,
        "escalaFinal":escalaFinal
	};

	//Bloqueia os botoes antes de realizar a promise
	bloqueioBotoes();

	$.get("unidades/getUnidadesFiltradas", {

		//Objeto parametros para o back-end
		parametros:	JSON.stringify(filterParams),

	}, function(data) {

		//Recebe os dados do back-end nao pode ser variavel local
		unidades = data;
        erro = 0;

		//monta os pontos no mapa
		getFeatureMap(unidades);
						
		//carrega o modal de reports com as unidades filtradas
		generateReports(unidades);

        if (unidades.length === 0) {
            erro = 404;
            $.toast({
                heading: 'Biogásmap',
                text: 'Nenhuma unidade encontrada com os filtros selecionados.',
                position: 'bottom-center',
                stack: false
            });
        }

        //Desbloqueia os botoes depois realizar a promise
		desbloqueioBotoes();
		
	}).fail(function(data, textStatus, xhr) {

	    erro = data.status;
	    console.log(data);
	    erro = 404;
	    $.toast({
	        heading: 'Biogásmap',
	        text: 'Ocorreu um erro ao realizar o filtro.',
	        position: 'bottom-center',
	        stack: false
	    });

        //Desbloqueia os botoes depois realizar a promise
        desbloqueioBotoes();
		
	});
	
}

function getAnos() {

    $.get("unidades/anos", {

    }, function(data) {

        //Recebe os dados do back-end
        listaAnos = data;
        listaAnos.forEach(element => console.log(element.anoDeInicioOperacao));

    }).fail(function(data, textStatus, xhr) {

        erro = data.status;
        console.log(data);
        erro = 404;
        $.toast({
            heading: 'Biogásmap',
            text: 'Ocorreu um erro ao carregar os anos',
            position: 'bottom-center',
            stack: false
        })

    });
}

/*-FIM-SERVICOS--------------------------------------------------------------------------------*/