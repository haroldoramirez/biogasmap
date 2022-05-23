/*-INICIO-POPUP-----------------------------------------------------------------------------*/

//Possibilida a abertura de popups no mapa
function addPopup() {

	//Botao ao fechar o popup
	$('#popup-closer').click(function() {

		overlay.setPosition(undefined);
		$('#popup-closer').blur();

		return false;
	});

	//Evento ao clicar no ponto do mapa
	map.on('click', function(evt) {

		let feature = map.forEachFeatureAtPixel(evt.pixel, function(feature) {

			return feature;

		});

		//Verificador quando clickado no ponto do mapa
		if (feature) {

			//Coordenada para carregar o popup com a flecha em cima do click
			let coordinate = evt.coordinate;

			//Tempo da animacao quando clicado no ponto
			let pan = ol.animation.pan({duration: 300, source: map.getView().getCenter()});

		    //Mostra o popup
		    overlay.setPosition(coordinate);

			//Animacao antes de ir para o centro
			map.beforeRender(pan);

			//Ao clickar no ponto ele seta para o centro
			//Nao setar no centro e sim mais para baixo sempre!
			map.getView().setCenter(coordinate);

			//Chama a funcao que abre o popup
			popupUnidade(feature.get('id'));

		} else {
			//fecha caso exista 1 click em qualquer regiao do mapa
			overlay.setPosition(undefined);
			$('#popup-closer').blur();
		}

	});

}

//Popup quando clickado no ponto do mapa
function popupUnidade(identificador) {

	let html = "";
    let porte = 0;
    let style = "";
    let valorEstimado = "";

	$.get("unidades/getUnidadesByCodigo", {

		//Parametros para o back-end
		id: identificador

	}, function(data) {

		//Recebe os dados do back-end
		unidade = data;

        switch(unidade.escala.intervalo) {
        case 1:
            porte = "Pequeno";
            break;
        case 2:
            porte = "Médio";
            break;
        case 3:
            porte = "Grande"
            break;
        default:
            {}
        }

        if (unidade.valorEstimado === true) {
            valorEstimado = '<span class="label label-warning pull-right popup-label">Valor estimado</span>';
        }

		html += '<table style="margin-bottom: -11px;" class="table table-hover table-nao-pula-linha">';

        html += '	<thead>';
        html += '		<tr>';
        html += '			<th colspan="6"><div class="text-center"><p><strong><i class="fa fa-industry" aria-hidden="true"></i> Dados do sistema de tratamento de substrato</strong></p></div></th>';
        html += '		</tr>';
        html += '	</thead>';

        html += '	<tbody>';

        html += '		<tr>';
        html += '			<td style="font-size: 13px;"> <div class="row col-md-7 text-left"><b>Código: </b>' + unidade.codigo + '</div> ';
        html += ' 			<div class="pull-right col-md-5 text-left"><b>Situação: </b>' + unidade.situacao.nome + '</div> </td> ';
        html += 		'</tr>'

        html += '		<tr>';
        html += '			<td style="font-size: 13px;"> <div class="row col-md-7 text-left"><b>Município: </b>' + unidade.municipio.nome + '</div> ';
        html += ' 			<div class="pull-right col-md-5 text-left"><b>Estado: </b>' + unidade.municipio.estado.nome + ' </div> </td> ';
        html += 		'</tr>'

        html += '		<tr>';
        html += '			<td style="font-size: 13px;"> <div class="row col-md-8 text-left"><b>Fonte de substrato: </b>' + unidade.categoria.nome + '</div> ';
        html += ' 			</td> ';
        html += 		'</tr>'

        html += '		<tr>';
        html += '			<td style="font-size: 13px;"> <div class="row col-md-8 text-left"><b>Produção anual de biogás: </b>' + currencyFormatDE(unidade.producaoBiogasAno) + ' Nm³/ano' + '</div> ';
        html += ' 			</td> ';
        html += 		'</tr>'

        html += '		<tr>';
        html += '			<td style="font-size: 13px;"> <div class="row col-md-8 text-left"><b>Porte: </b>' + porte + '</div> ';
        html += ' 			</td> ';
        html += 		'</tr>'

        html += '		<tr>';
        html += '			<td style="font-size: 13px;"> <div class="row col-md-7 text-left"><b>Início da operação com uso energético do biogás: </b>' + unidade.anoDeInicioOperacao + '</div> ';
        html += 		'</tr>'

        html += '	<thead>';
        html += '		<tr>';
        html += '			<th colspan="6"><div class="text-center"><p><i class="fa fa-cogs" aria-hidden="true"></i> <strong>Aplicação Energética</strong></p></div></th>';
        html += '		</tr>';
        html += '	</thead>';

        html += '		<tr> <td>';
        
        if (unidade.biogasParaEnergiaTermica !== true) {

            style = "my-btn-default disabled";

        } else {

            style ="btn-success";
        }

        html += '			 <button style="cursor:default;" type="button" class="btn btn-xs ' + style + '"><i class="fa fa-fire" aria-hidden="true"></i> Energia Térmica </button>';

        if (unidade.biogasParaEnergiaEletrica !== true) {

            style = "my-btn-default disabled";

        } else {

            style ="btn-success";
        }
        html += '			<button style="cursor:default;" type="button" class="btn btn-xs ' + style + '"><i class="fa fa-bolt" aria-hidden="true"></i> Energia Elétrica </button>';

        if (unidade.biogasParaEnergiaMecanica !== true) {

            style = "my-btn-default disabled";

        } else {

            style ="btn-success";
        }

        html += '			<button style="cursor:default;" type="button" class="btn btn-xs ' + style + '"><i class="fa fa-cogs" aria-hidden="true"></i> Energia Mecânica</button>';

        if (unidade.biogasParaBiometano !== true) {

            style = "my-btn-default disabled";

        } else {

            style ="btn-success";

        }

        html += '		<button style="cursor:default;" type="button" class="btn btn-xs ' + style + '"><i class="fa fa-envira" aria-hidden="true"></i> Biometano/GNV </button>';
        html += 		'</tr>'

        let dataAlteracaoUnidade = new Date(unidade.dataAlteracao.time);
        let dataFormatada = dataAlteracaoUnidade.getDate() + "/" + (dataAlteracaoUnidade.getMonth() + 1) + "/" + dataAlteracaoUnidade.getFullYear();

        html += '                <tr>';
        html += '                        <td style="font-size: 13px;"> <div class="text-left"><b> Data de atualização: </b> ' + moment(unidade.dataAlteracao).format('DD/MM/YYYY') + ' ' + '</div> </td> ';
        html += '                </tr>'

        html += '</tbody>'
        html += '</table>'

        $("#popup-content").html(html);

	}).fail(function(data, textStatus, xhr) {

        html += '<p>Ocorreu um erro ao carregar a unidade.</p>';

        $("#popup-content").html(html);

	});

}

/*-FIM-POPUP-------------------------------------------------------------------------------*/