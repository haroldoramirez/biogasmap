//INICIO - Monta o mapa na tela - funcao que executa depois que a pagina estiver carregada
$(document).ready(function() {
					
	//let zoomslider  = new ol.control.ZoomSlider();
	
	//Informacoes de KM
	//let scaleline  = new ol.control.ScaleLine();

	//Popup
	let popupUnidade = document.getElementById('popup-unidade');

	let camadaBase = new ol.layer.Tile({
		id : "Camada Base - Ruas",
		title : "Camada Base - Ruas",
		camadaBase : true,
		source: new ol.source.BingMaps({
			key: 'AhEGPA8tz-KzW-PGgFwn-R4N1efXldwyKmjqmTvK0aXW1ZYcAxRjwMBggWsKFaKQ',
			imagerySet: 'road'
		})
	});
	
	let layerStm = new ol.layer.Group({
		layers: [camadaBase],
		camadaBase : true,
		title: 'Camadas Bases'
	});
			
	//Zoom
	let viewport = document.getElementById('mapa');
		
    //Zoom
    let initialZoom = getMinZoom();
    
    //Zoom
    function getMinZoom() {
    	//Menor zoom
    	let width = viewport.clientWidth;
        return Math.ceil(Math.LOG2E * Math.log(width / 256));
    }

    //Overlay do Popup
	overlay = new ol.Overlay(({
		element: popupUnidade,
            autoPan: true,
            autoPanAnimation: {
                duration: 300
            }
	}));

	//Carregamento do Mapa
	map = new ol.Map({
		target: 'mapa',
		layers: [layerStm],
		view: 
			new ol.View({
				//para mostrar no Brasil
				center: ol.proj.transform([-50.000, -18.000], 'EPSG:4326', 'EPSG:3857'),
		        minZoom: initialZoom,
		        zoom: 4
         	}),
         overlays: [overlay]         	
	});
		
	//Adicionar barra de zoom
	//map.addControl(zoomslider);
	
	//Arquivo OpenLayers3/js/controle/mouseposition.js
	addMousePositionControl(4, "EPSG:4326", document.getElementById("mousePosition"));
	
	//Adicionar informacoes de KM
	//map.addControl(scaleline);
	
	//Arquivo OpenLayers3/js/ferramenta/legenda.js
	addLegenda($("#legenda"), true);
									
	//Busca todas as unidades - Arquivo /js/servicos.js
	getUnidadesByFilterScale(escalaInicialSelecionada, escalaFinalSelecionada);
	getAnos();
});
//FIM - Monta o mapa na tela - funcao que executa depois que a pagina estiver carregada
