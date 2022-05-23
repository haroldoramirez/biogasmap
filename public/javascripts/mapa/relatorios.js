/*-INICIO - currency - Funcao para formatar valores monetarios---------------------------------*/
function formatCurrencyBR(n, c, d, t) {
    let s, j, i;
    c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "," : d, t = t == undefined ? "." : t, s = n < 0 ? "-" : "", i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
    return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
}
function currencyFormatDE (num) {
    return num
       .toFixed(0) // always two decimal digits
       .replace(".", ",") // replace decimal point character with ,
       .replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.")// use . as a separator
}
/*-FIM - currency - Funcao para formatar valores monetarios------------------------------------*/

/*-INICIO - Convert XLSX-----------------------------------------------------------------------*/
function createXLSX(s) {

     var buf = new ArrayBuffer(s.length);
     var view = new Uint8Array(buf);
     for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF;
     return buf;

}
/*-FIM - Convert XLSX--------------------------------------------------------------------------*/

/*-INICIO - Convert CSV------------------------------------------------------------------------*/
function convertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in array[i]) {
            if (line != '') line += ';'

            line += array[i][index];
        }

        str += line + '\r\n';
    }

    return str;
}
function exportCSVFile(headers, items, fileTitle) {
	
    if (headers) {
        items.unshift(headers);
    }

    // Convert Object to JSON
    var jsonObject = JSON.stringify(items);

    var csv = this.convertToCSV(jsonObject);

    var exportedFilenmae = fileTitle + '.csv' || 'export.csv';

    var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    
    if (navigator.msSaveBlob) { // IE 10+
    	
        navigator.msSaveBlob(blob, exportedFilenmae);
        
    } else {
    	
        var link = document.createElement("a");
        
        if (link.download !== undefined) { // feature detection
        	
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            
            link.setAttribute("href", url);
            link.setAttribute("download", exportedFilenmae);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            
        }
    }
}
/*-FIM - Convert CSV---------------------------------------------------------------------------*/

/*-INICIO-PDF----------------------------------------------------------------------------------*/
function downloadRelatorioPdf() {
					
    let agora = new moment(Date.now()).format('DD-MM-YYYY - HH:mm:ss');

    if (municipioId == 0) {
        municipio = "Todos";
    }
	   
	//Conteudo deve ser declarado antes da definicao do documento
    //Inicio - Conteudo do Arquivo PDF
    let conteudo = [
        {
            // usually you would use a dataUri instead of the name for client-side printing
            // sampleImage.jpg however works inside playground so you can play with it
            image: biogasmap_logo,
            width: 242,
            height: 32,
            alignment: 'center',
            margin: [0, 0, 0, 0]
        },
        {text: ' ', style: 'paragrafo', alignment: 'left'},
        {text: 'RELATÓRIO', style: 'titulo', alignment: 'center'},
        //INICIO - Tabela 1
        {style: 'topTable',
            table: {
                widths: ['50%','50%'],
                heights: [12],
                headerRows: 1,
                body: [
                    [
                        {text: 'Dados do sistema de tratamento de dejetos', style: 'tableHeader', colSpan: 2}, {}
                    ],
                    [
                        {text: 'Situação', style: 'tableLabel'}, {text: concatSituacao, style: 'tableValue'},

                    ],
                    [
                        {text: 'Porte', style: 'tableLabel'}, {text: concatPorte, style: 'tableValue'},

                    ],
                    [
                        {text: 'Fonte de substratos', style: 'tableLabel'}, {text: concatFonteDeSubstrato, style: 'tableValue'},

                    ],
                    [
                        {text: 'Região', style: 'tableLabel'}, {text: concatRegiao, style: 'tableValue'},

                    ],
                    [
                        {text: 'Estado', style: 'tableLabel'}, {text: concatEstado, style: 'tableValue'},

                    ],
                    [
                        {text: 'Município', style: 'tableLabel'}, {text: municipio, style: 'tableValue'},

                    ]
                ]
            },
            layout: {
                paddingLeft: function(i, node) { return 8; },
                paddingRight: function(i, node) { return 8; },
                paddingTop: function(i, node) { return 6; },
                paddingBottom: function(i, node) { return 6; },
                fillColor: function (i, node) {
                    return (i % 2 === 0) ?  '#F5F5F5' : null;
                }
            }
        },
        //FIM - Tabela 1
        {text: ' ', style: 'paragrafo', alignment: 'left'},
        //INICIO - Tabela 2
        {style: 'topTable',
            table: {
                widths: ['50%','50%'],
                heights: [12],
                headerRows: 1,
                body: [
                    [
                        {text: 'Detalhamento técnico', style: 'tableHeader', colSpan: 2}, {}
                    ],
                    [
                        {text: 'Ano de instalação das plantas', style: 'tableLabel'}, {text: periodoRelatorio, style: 'tableValue'},

                    ],
                    [
                        {text: 'Tecnologia de tratamento de dejetos', style: 'tableLabel'}, {text: 'Biodigestão', style: 'tableValue'},

                    ],
                    [
                        {text: 'Quantidade de unidades', style: 'tableLabel'}, {text: quantidadeUnidadesBiodigestor, style: 'tableValue'},

                    ],
                    [
                        {text: 'Aplicação das unidades', style: 'tableLabel'}, {text: concatAplicacao, style: 'tableValue'},

                    ],
                    [
                        {text: 'Produção total de biogás', style: 'tableLabel'}, {text: currencyFormatDE(producaoTotalBiogas) + ' Nm³/ano', style: 'tableValue'},

                    ],
                ]
            },
            layout: {
                paddingLeft: function(i, node) { return 8; },
                paddingRight: function(i, node) { return 8; },
                paddingTop: function(i, node) { return 6; },
                paddingBottom: function(i, node) { return 6; },
                fillColor: function (i, node) {
                    return (i % 2 === 0) ?  '#F5F5F5' : null;
                }
            }
        },
        //FIM - Tabela 2
        {text: ' ', style: 'paragrafo', alignment: 'left'},
        {text: 'PARCEIROS', style: 'titulo', alignment: 'center'},
        //Linha 1
        {
            columns: [
                {
                    image: logo_parceiros,
                    width: 450,
                    height: 35,
                    margin: [0, 0, 0, 0],
                },
            ]
        },
        {text: ' ', style: 'paragrafo', alignment: 'left'},
      ];
    //Fim - Conteudo do Arquivo PDF
	  
    //Inicio - Doc Definition
    let docDefinition = {
        //watermark: { text: 'documento digital', fontSize: 20 },
        compress: false,
        //Cabecalho da pagina
        header: (currentPage, pageCount) => {
            return [
                {
                    style: 'table',
                    // [left, top, right, bottom] or [horizontal, vertical] or just a number for equal margins
                    margin: [52,35,52,35],
                    table: {
                        widths: [100, '*'],
                        headerRows: 0,
                        body: [
                            [
                                {
                                    // usually you would use a dataUri instead of the name for client-side printing
                                    // sampleImage.jpg however works inside playground so you can play with it
                                    image: cib_logo,
                                    width: 90,
                                    height: 25,
                                    alignment: 'left',
                                    margin: [0, 0, 0, 0]
                                },
                            ]
                        ]
                    },
                    layout: 'noBorders'
                },
            ]
        },
        //Rodape da pagina
        footer: (currentPage, pageCount) => {
            if (currentPage === 1) {
                return [
                    {text: 'Centro Internacional de Energias Renováveis ' + agora, alignment: 'center', style: 'footer'},
                ]
            }
        },
        //Onde declaramos o conteudo da pagina
        content: conteudo,
        pageSize: 'A4',
        pageOrientation: 'portrait',
        pageMargins: [72,80,72,80],
        //Onde declaramos os estilos dos paragrafos
        styles: {
            topHeader: {
                fontSize: 16,
                bold: true,
                margin: [0, 6, 0, 30],
                alignment: 'right'
            },
            titulo: {
                fontSize: 16,
                bold: true,
                margin: [0, 6, 0, 10],
                alignment: 'center'
            },
            titulo2: {
                fontSize: 14,
                bold: true,
                margin: [0, 6, 0, 10],
                alignment: 'center'
            },
            titulo3: {
                fontSize: 14,
                bold: true,
                margin: [0, 6, 0, 10],
                alignment: 'left'
            },
            paragrafo: {
                fontSize: 12,
                alignment: 'justify',
                lineHeight: 1.5,
            },
            tituloTabela: {
                fontSize: 9,
                alignment: 'center'
            },
            table: {
                fontSize: 8,
                alignment: 'left',
                color: 'black',
                margin: [0, 5, 0, 15]
            },
            header: {
                fontSize: 16,
                bold: true,
                margin: [0, 10, 0, 15],
                alignment: 'left'
            },
            footer: {
                fontSize: 8,
                margin: [0, 25, 0, 17],
                alignment: 'right'
            },
            footerHeader: {
                fontSize: 12,
                bold: true,
                margin: [0, 3, 0, 21],
                alignment: 'center'
            },
            negrito: {
                bold:true,
            },
            paragrafoitalico: {
                italics: true,
                fontSize: 10,
                alignment: 'left'
            },
            tableHeader : {
                 alignment: 'center',
                 bold: true,
                 fontSize: 11
            },
            tableLabel : {
                 alignment: 'center',
                 bold: true,
                 fontSize: 10,
            },
            tableValue : {
                 alignment: 'center',
                 fontSize: 10,
            },
            listaUl : {
                lineHeight: 1.5
            }
        },
        defaultStyle: {
            columnGap: 20
        }
    };
    //Fim - Doc Definition
      
    $('#relatorioModal').modal('hide');

    let pdf = pdfMake.createPdf(docDefinition).download('biogasmap-reports-'+ agora +'.pdf');

    //Promises de acesso
    $.get("acessos", {

        //Parametros para o back-end
        nome: "pdf"

    }, function(data) {
      //Success
    }).fail(function(data) {

        console.log(data);

    });

  };
/*-FIM-PDF-------------------------------------------------------------------------------------*/

/*-INICIO-CSV----------------------------------------------------------------------------------*/
function downloadRelatorioCsv(csv) {

    let agora = new moment(Date.now()).format('DD-MM-YYYY - HH:mm:ss');
    let intervalo = "";

    //Aplicacoes
    let biometano = "";
    let termica = "";
    let eletrica = "";
    let mecanica = "";
		
    let headers = {
        codigo: "codigo",
        anoDeInicioOperacao: 'anoDeInicioOperacao'.replace(/,/g, ''), // remove commas to avoid errors
        biogasParaBiometano: "biogasParaBiometano",
        biogasParaEnergiaEletrica: "biogasParaEnergiaEletrica",
        biogasParaEnergiaMecanica: "biogasParaEnergiaMecanica",
        biogasParaEnergiaTermica: "biogasParaEnergiaTermica",
        porte: "porte",
        situacaoDaPlanta: "situacao",
        categoria: "categoria",
        municipio: "municipio",
        estado: "estado",
        regiao: "regiao",
        producaoBiogasAno: "producaoBiogasAno"
      };

    itemsNotFormatted = unidades;

    let itemsFormatted = [];

    // format the data
    itemsNotFormatted.forEach((item) => {

        switch(item.biogasParaBiometano) {
            case true:
                biometano = "Sim";
                break;
            case false:
                biometano = "Não";
                break;
        }

        switch(item.biogasParaEnergiaEletrica) {
            case true:
                eletrica = "Sim";
                break;
            case false:
                eletrica = "Não";
                break;
        }

        switch(item.biogasParaEnergiaMecanica) {
            case true:
                mecanica = "Sim";
                break;
            case false:
                mecanica = "Não";
                break;
        }

        switch(item.biogasParaEnergiaTermica) {
            case true:
                termica = "Sim";
                break;
            case false:
                termica = "Não";
                break;
        }

        switch(item.escala.intervalo) {
            case 1:
                intervalo = "Pequeno";
                break;

            case 2:
                intervalo = "Médio";
                break;

            case 3:
                intervalo = "Grande"
                break;

            default:
                {}
        }

        itemsFormatted.push({
            codigo: item.codigo,
            anoDeInicioOperacao: item.anoDeInicioOperacao.replace(/,/g, ''), // remove commas to avoid errors,
            biogasParaBiometano: biometano,
            biogasParaEnergiaEletrica: eletrica,
            biogasParaEnergiaMecanica: mecanica,
            biogasParaEnergiaTermica: termica,
            porte: intervalo,
            situacaoDaPlanta: item.situacao.nome,
            categoria: item.categoria.nome,
            municipio: item.municipio.nome,
            estado: item.municipio.estado.sigla,
            regiao: item.municipio.estado.regiao,
            producaoBiogasAno: item.producaoBiogasAno,
        });
    });
		  
    $('#relatorioModal').modal('hide');

    let fileTitle = 'biogasmap-reports-' + agora; // or 'my-unique-title'

    exportCSVFile(headers, itemsFormatted, fileTitle); // call the exportCSVFile() function to process the JSON and trigger the download

    //Promises de acesso
    $.get("acessos", {

        //Parametros para o back-end
        nome: "csv"

    }, function(data) {
        //Success
    }).fail(function(data) {

        console.log(data);

    });
}
/*-FIM-CSV-------------------------------------------------------------------------------------*/

/*-INICIO-XLS----------------------------------------------------------------------------------*/
function downloadRelatorioXls() {

    let agora = new moment(Date.now()).format('DD-MM-YYYY - HH:mm:ss');

    let ws_data = [];
    let intervalo = "";

    //Aplicacoes
    let biometano = "";
    let termica = "";
    let eletrica = "";
    let mecanica = "";

    let wb = XLSX.utils.book_new();

    wb.Props = {
        Title: "Unidades Filtradas",
        Subject: "Biogasmap",
        Author: "Centro Internacional de Energias Renovaveis",
        CreatedDate: new Date()
    };

    wb.SheetNames.push("Unidades");

    //Header da planilha
    ws_data.push(['codigo',
        'anoDeInicioOperacao',
        'biogasParaBiometano',
        'biogasParaEnergiaEletrica',
        'biogasParaEnergiaMecanica',
        'biogasParaEnergiaTermica',
        'porte',
        'situacao',
        'categoria',
        'municipio',
        'estado',
        'regiao',
        'producaoBiogasAno']);

    for (let i = 0; i < unidades.length; i++) {

        switch(unidades[i].biogasParaBiometano) {
            case true:
                biometano = "Sim";
                break;
            case false:
                biometano = "Não";
                break;
        }

        switch(unidades[i].biogasParaEnergiaEletrica) {
            case true:
                eletrica = "Sim";
                break;
            case false:
                eletrica = "Não";
                break;
        }

        switch(unidades[i].biogasParaEnergiaMecanica) {
            case true:
                mecanica = "Sim";
                break;
            case false:
                mecanica = "Não";
                break;
        }

        switch(unidades[i].biogasParaEnergiaTermica) {
            case true:
                termica = "Sim";
                break;
            case false:
                termica = "Não";
                break;
        }

        switch(unidades[i].escala.intervalo) {
            case 1:
                intervalo = "Pequeno";
                break;

            case 2:
                intervalo = "Médio";
                break;

            case 3:
                intervalo = "Grande"
                break;

            default:
                {}
        }

        ws_data.push([unidades[i].codigo,
            unidades[i].anoDeInicioOperacao,
            biometano,
            eletrica,
            mecanica,
            termica,
            intervalo,
            unidades[i].situacao.nome,
            unidades[i].categoria.nome,
            unidades[i].municipio.nome,
            unidades[i].municipio.estado.sigla,
            unidades[i].municipio.estado.regiao,
            unidades[i].producaoBiogasAno]);
    }

    let ws = XLSX.utils.aoa_to_sheet(ws_data);
    wb.Sheets["Unidades"] = ws;
    let wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

    saveAs(new Blob([createXLSX(wbout)],{type:"application/octet-stream"}), 'biogasmap-reports-'+ agora +'.xlsx');

    $('#relatorioModal').modal('hide');

    //Promises de acesso
    $.get("acessos", {

        //Parametros para o back-end
        nome: "xls"

    }, function(data) {
       //Success
    }).fail(function(data) {

        console.log(data);

    });

}
/*-FIM-XLS-------------------------------------------------------------------------------------*/

/*-INICIO-BIOGASDATA---------------------------------------------------------------------------*/
function biogasData() {

    //Promises de acesso
    $.get("acessos", {

        //Parametros para o back-end
        nome: "biogasdata"

    }, function(data) {
       //Success
    }).fail(function(data) {

        console.log(data);

    });

}
/*-FIM-BIOGASDATA------------------------------------------------------------------------------*/

/*-INICIO-BIOGASDATA---------------------------------------------------------------------------*/
function notaTecnica() {

    //Promises de acesso
    $.get("acessos", {

        //Parametros para o back-end
        nome: "notatecnica"

    }, function(data) {
       //Success
    }).fail(function(data) {

        console.log(data);

    });

}
/*-FIM-BIOGASDATA------------------------------------------------------------------------------*/

/*-INICIO-RELATORIO----------------------------------------------------------------------------*/
function generateReports(data) {

	concatSituacao = "";
	concatPorte = "";
	concatFonteDeSubstrato = "";
	concatRegiao = "";
	concatEstado = "";
	concatAplicacao = "";
			
	quantidadeUnidadesBiodigestor = 0;
	producaoTotalBiogas = 0;
	
	volumeTotalCompostoProduzido = 0;
	
	let unidadesFiltradas = data;
					
	//Percorrer a lista de unidades data filtradas e realizar os calculos		
    /*Repeticao para percorrer a lista de unidades filtradas*/
    for (let i = 0; i < unidadesFiltradas.length; i++) {
    	    	    	    	    	   		    		
		quantidadeUnidadesBiodigestor++;
		producaoTotalBiogas = producaoTotalBiogas + unidadesFiltradas[i].producaoBiogasAno;

		//INICIO - Verifica as situacoes do backend

        if (unidadesFiltradas[i].situacao.nome == 'Em operação' && !concatSituacao.includes("Em operação")) {

            concatSituacao = concatSituacao.concat(operacao);

        } else if (unidadesFiltradas[i].situacao.nome == 'Em reforma' && !concatSituacao.includes("Em reforma")) {

            concatSituacao = concatSituacao.concat(reformando);

        } else if (unidadesFiltradas[i].situacao.nome == 'Em implantação' && !concatSituacao.includes("Em implantação")) {

            concatSituacao = concatSituacao.concat(construcao);

        }

		//FIM - Verifica as situacoes do backend

		//INICIO - Verifica porte do backend

		if (unidadesFiltradas[i].escala.intervalo == 1 && !concatPorte.includes("Pequeno")) {

		    concatPorte = concatPorte.concat(pequeno);

		} else if (unidadesFiltradas[i].escala.intervalo == 2 && !concatPorte.includes("Médio")) {

		    concatPorte = concatPorte.concat(medio);

		} else if (unidadesFiltradas[i].escala.intervalo == 3 && !concatPorte.includes("Grande")) {

            concatPorte = concatPorte.concat(grande);

        }

		//FIM - Verifica porte do backend

        //INICIO - Verifica fonte de substrato do backend

        if (unidadesFiltradas[i].categoria.nome == 'Indústria' && !concatFonteDeSubstrato.includes("Indústria")) {

            concatFonteDeSubstrato = concatFonteDeSubstrato.concat(categoriaRelatorioIndustria);

        } else if (unidadesFiltradas[i].categoria.nome == 'Agropecuária' && !concatFonteDeSubstrato.includes("Agropecuária")) {

            concatFonteDeSubstrato = concatFonteDeSubstrato.concat(categoriaRelatorioAgropecuaria);

        } else if (unidadesFiltradas[i].categoria.nome == 'RSU ou Estação de Tratamento de Esgoto' && !concatFonteDeSubstrato.includes("RSU ou Estação de Tratamento de Esgoto")) {

            concatFonteDeSubstrato = concatFonteDeSubstrato.concat(categoriaRelatorioEsgoto);

        }

        //FIM - Verifica fonte de substrato do backend


        //INICIO - Verifica aplicacao do backend

        if (unidadesFiltradas[i].biogasParaBiometano == true && !concatAplicacao.includes("GNV/Biometano")) {

            concatAplicacao = concatAplicacao.concat(aplicacaoBiometanoRelatorio);

        } else if (unidadesFiltradas[i].biogasParaEnergiaEletrica == true && !concatAplicacao.includes("Elétrica")) {

            concatAplicacao = concatAplicacao.concat(aplicacaoEletricaRelatorio);

        } else if (unidadesFiltradas[i].biogasParaEnergiaMecanica == true && !concatAplicacao.includes("Mecânica")) {

            concatAplicacao = concatAplicacao.concat(aplicacaoMecanicaRelatorio);

        } else if (unidadesFiltradas[i].biogasParaEnergiaTermica == true && !concatAplicacao.includes("Térmica")) {

            concatAplicacao = concatAplicacao.concat(aplicacaoTermicaRelatorio);

        }

        //INICIO - Verifica aplicacao do backend
    		    	    	    	    		
    	//INICIO - Verifica as regiões do backend
    	if (unidadesFiltradas[i].municipio.estado.regiao == 'Sul' && !concatRegiao.includes("Sul")) {
    				
    		concatRegiao = concatRegiao.concat(sul);
    		
    	} else if (unidadesFiltradas[i].municipio.estado.regiao == 'Norte' && !concatRegiao.includes("Norte")) {
    		
    		concatRegiao = concatRegiao.concat(norte);
    		
    	} else if (unidadesFiltradas[i].municipio.estado.regiao == 'Nordeste' && !concatRegiao.includes("Nordeste")) {
    		
    		concatRegiao = concatRegiao.concat(nordeste);
    		
    	} else if (unidadesFiltradas[i].municipio.estado.regiao == 'Centro-Oeste' && !concatRegiao.includes("Centro-Oeste")) {
    		
    		concatRegiao = concatRegiao.concat(centrooeste);
    		
    	} else if (unidadesFiltradas[i].municipio.estado.regiao == 'Sudeste' && !concatRegiao.includes("Sudeste")) {
    		
    		concatRegiao = concatRegiao.concat(sudeste);
    		
    	}
    	//FIM - Verifica as regiões do backend
    	    	    	    	    	    	
    	//INICIO - Verifica Estados
    	if (unidadesFiltradas[i].municipio.estado.nome == 'Acre' && !concatEstado.includes("Acre")) {

    		concatEstado= concatEstado.concat(acre);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Alagoas' && !concatEstado.includes("Alagoas")) {

    		concatEstado= concatEstado.concat(alagoas);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Amapá' && !concatEstado.includes("Amapá")) {

    		concatEstado= concatEstado.concat(amapa);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Amazonas' && !concatEstado.includes("Amazonas")) {

    		concatEstado= concatEstado.concat(amazonas);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Bahia' && !concatEstado.includes("Bahia")) {

    		concatEstado= concatEstado.concat(bahia);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Ceará' && !concatEstado.includes("Ceará")) {

    		concatEstado= concatEstado.concat(ceara);
 
    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Distrito Federal' && !concatEstado.includes("Distrito Federal")) {

    		concatEstado= concatEstado.concat(distritofederal);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Espírito Santo' && !concatEstado.includes("Espírito Santo")) {

    		concatEstado= concatEstado.concat(espiritosanto);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Goiás' && !concatEstado.includes("Goiás")) {

    		concatEstado= concatEstado.concat(goias);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Maranhão' && !concatEstado.includes("Maranhão")) {

    		concatEstado= concatEstado.concat(maranhao);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Mato Grosso' && !concatEstado.includes("Mato Grosso")) {

    		concatEstado= concatEstado.concat(matogrosso);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Mato Grosso do Sul' && !concatEstado.includes("Mato Grosso do Sul")) {

    		concatEstado= concatEstado.concat(matogrossodosul);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Minas Gerais' && !concatEstado.includes("Minas Gerais")) {

    		concatEstado= concatEstado.concat(minasgerais);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Pará' && !concatEstado.includes("Pará")) {

    		concatEstado= concatEstado.concat(para);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Paraná' && !concatEstado.includes("Paraná")) {

    		concatEstado= concatEstado.concat(parana);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Paraíba' && !concatEstado.includes("Paraíba")) {

    		concatEstado= concatEstado.concat(paraiba);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Pernambuco' && !concatEstado.includes("Pernambuco")) {

    		concatEstado= concatEstado.concat(pernambuco);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Piauí' && !concatEstado.includes("Piauí")) {

    		concatEstado= concatEstado.concat(piaui);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Rio de Janeiro' && !concatEstado.includes("Rio de Janeiro")) {

    		concatEstado= concatEstado.concat(riodejaneiro);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Rio Grande do Norte' && !concatEstado.includes("Rio Grande do Norte")) {

    		concatEstado= concatEstado.concat(riograndedonorte);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Rio Grande do Sul' && !concatEstado.includes("Rio Grande do Sul")) {

    		concatEstado= concatEstado.concat(riograndedosul);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Rondônia' && !concatEstado.includes("Rondônia")) {

    		concatEstado= concatEstado.concat(rondonia);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Roraima' && !concatEstado.includes("Roraima")) {

    		concatEstado= concatEstado.concat(roraima);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Santa Catarina' && !concatEstado.includes("Santa Catarina")) {

    		concatEstado= concatEstado.concat(santacatarina);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'São Paulo' && !concatEstado.includes("São Paulo")) {

    		concatEstado= concatEstado.concat(saopaulo);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Sergipe' && !concatEstado.includes("Sergipe")){

    		concatEstado= concatEstado.concat(sergipe);

    	} else if (unidadesFiltradas[i].municipio.estado.nome == 'Tocantins' && !concatEstado.includes("Tocantins")) {

    		concatEstado= concatEstado.concat(tocantins);

    	}
    	//FIM - Verifica Estados
    	    	    	    	    	
    }
    //Final do loop da lista de unidades
                             			
	//Parametros recebidos pelo filtro selecionado - Modal
	document.getElementById('situacaoUnidades').textContent = concatSituacao;
	document.getElementById('porteUnidades').textContent = concatPorte;
	document.getElementById('aplicacaoUnidades').textContent = concatAplicacao;
	document.getElementById('categoriaUnidades').textContent = concatFonteDeSubstrato;
	document.getElementById('regiaoUnidades').textContent = concatRegiao;
	document.getElementById('estadoUnidades').textContent = concatEstado;
	document.getElementById('municipioUnidades').textContent = municipio;
	document.getElementById('periodoRelatorio').textContent = periodoRelatorio;
			
	//tecnologia biodigestao
	document.getElementById('quantidadeUnidadeBiodigestores').textContent = quantidadeUnidadesBiodigestor;
	document.getElementById('producaoTotalBiogasUnidades').textContent = currencyFormatDE(producaoTotalBiogas);

}
/*-FIM-RELATORIO-------------------------------------------------------------------------------*/

//Validador de Modal
function abrirModal() {

	if (erro === 404) {
		$("#relatorioModalErro").modal('show');
	} else {
		$("#relatorioModal").modal('show');
	}

}