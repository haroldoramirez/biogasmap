package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.google.gson.Gson;
import daos.*;
import models.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import secured.SecuredAdmin;
import validators.UnidadeFormData;
import views.html.admin.unidades.list;

import javax.inject.Inject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static play.data.Form.form;

public class UnidadeController extends Controller {

    static private final DynamicForm listForm = Form.form();

    private final Form<Usuario> unidadeForm = Form.form(Usuario.class);

    static private final LogController logController = new LogController();

    //Colunas para o arquivo csv
    private static final char COMMA_DELIMITER = ';';
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "codigo_unico;" +
            "ano;" +
            "situacao;" +
            "porte;" +
            "categoria;" +
            "municipio;" +
            "estado;" +
            "producao_biogas_ano;" +
            "energia_termica;" +
            "energia_eletrica;" +
            "energia_mecanica;" +
            "biometano;" +
            "latitude;" +
            "longitude";

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private EscalaDAO escalaDAO;

    @Inject
    private EstadoDAO estadoDAO;

    @Inject
    private MunicipioDAO municipioDAO;

    @Inject
    private SituacaoDAO situacaoDAO;

    @Inject
    private CategoriaDAO categoriaDAO;

    @Inject
    private UnidadeDAO unidadeDAO;

    //Expressao regular para auxiliar no cadastro de municipios
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private Optional<Usuario> usuarioAtual() {
        String email = session().get("email");
        return usuarioDAO.comEmail(email);
    }

    public Result getUnidadesByAnoDeInicioOperacaoDistintas() {
        Optional<List<Unidade>> unidades = unidadeDAO.carregarListaAnosInicioOperacao();
        return unidades.map(unidadeList -> ok(Json.toJson(unidadeList))).orElseGet(() -> notFound("Unidade não encontrada"));
    }

    /*
     * Retrieve a unidade from codigo unico
     * */
    public Result getUnidadeByCodigo(Integer codigo) {

/*        System.out.println("Codigo da Unidade: " + codigo);*/
        Optional<Unidade> unidade = unidadeDAO.comCodigoUnico(codigo);

        if (unidade.isPresent()) {
            return ok(Json.toJson(unidade.get()));
        }

        return notFound("Unidade não encontrada");
    }

    /**
     * Retrieve a list of all objects
     *
     * @return a list of all objects in json object for front-end
     */
    public Result getUnidades(Long escalaInicial, Long escalaFinal) {

        /*System.out.println("getUnidades");
        System.out.println("escalaInicial: " + escalaInicial);
        System.out.println("escalaFinal: " + escalaFinal);*/

        try {
            Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                    "WHERE producaoBiogasAno " +
                    "BETWEEN :escalaInicial AND :escalaFinal " +
                    "AND status = :status " +
                    "ORDER BY codigo");

            //Status das unidades sempre aprovado
            query.setParameter("status", "APROVADO");

            //Parametros das escalas
            query.setParameter("escalaInicial", escalaInicial);
            query.setParameter("escalaFinal", escalaFinal);

            List<Unidade> filtroDeUnidades = query.findList();

            return ok(Json.toJson(filtroDeUnidades));
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest(Json.toJson(e.getMessage()));
        }

    }

    /**
     * Retrieve a list of filtered objects
     *
     * Esses conjuntos de IFs servem para garantir que o usuario selecione qualquer ordem de busca e que nas buscas com mais paramentos que seja possivel filtrar por estado e municipio sem que ocorra erros.
     *
     * @return a list of filtered objects in json object for front-end
     */
    public Result getUnidadesFiltradas(String filterParams) {

        try {

            //Instancia o Gson
            Gson g = new Gson();

            //transforma o jsonString que veio do parametro em um POJO - Plain Old Java Object
            Parametros parametros = g.fromJson(filterParams, Parametros.class);

/*            System.out.println("ValidadorSituacao: " + parametros.getValidadorSituacao());
            System.out.println("ValidadorEscala: " + parametros.getValidadorEscala());
            System.out.println("ValidadorEscala: " + parametros.getValidadorAplicacao());
            System.out.println("ValidadorCategoria: " + parametros.getValidadorCategoria());
            System.out.println("ValidadorAno: " + parametros.getValidadorAno());
            System.out.println("ValidadorEstado: " + parametros.getValidadorEstado());
            System.out.println("ValidadorMunicipio: " + parametros.getValidadorMunicipio());*/

            //INICIO - Conjunto de IF para filtrar unidades apenas por tipos de botoes
            if (parametros.getValidadorSituacao()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorAplicacao()
                    && !parametros.getValidadorCategoria()
                    && !parametros.getValidadorAno()
                    && !parametros.getValidadorEstado()
                    && !parametros.getValidadorMunicipio()) {

/*                System.out.println("Selecionado apenas por situacao");
                System.out.println("SituacaoOperando: " + parametros.getSituacaoOperando());
                System.out.println("SituacaoConstruindo: " + parametros.getSituacaoConstruindo());
                System.out.println("SituacaoReformando: " + parametros.getSituacaoReformando());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE (situacao = :situacaoOperando " +
                        "OR situacao = :situacaoConstruindo " +
                        "OR situacao = :situacaoReformando) " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros situacao
                query.setParameter("situacaoOperando", parametros.getSituacaoOperando());
                query.setParameter("situacaoConstruindo", parametros.getSituacaoConstruindo());
                query.setParameter("situacaoReformando", parametros.getSituacaoReformando());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));

            } else if (parametros.getValidadorEscala()
                    && !parametros.getValidadorSituacao()
                    && !parametros.getValidadorAplicacao()
                    && !parametros.getValidadorCategoria()
                    && !parametros.getValidadorAno()
                    && !parametros.getValidadorEstado()
                    && !parametros.getValidadorMunicipio()) {

/*                System.out.println("Selecionado apenas por escala");
                System.out.println("escalaInicial: " + parametros.getEscalaInicial());
                System.out.println("escalaFinal: " + parametros.getEscalaFinal());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE producaoBiogasAno " +
                        "BETWEEN :escalaInicial AND :escalaFinal " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros das escalas
                query.setParameter("escalaInicial", parametros.getEscalaInicial());
                query.setParameter("escalaFinal", parametros.getEscalaFinal());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));

            } else if (parametros.getValidadorAno()
                    && !parametros.getValidadorSituacao()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorAplicacao()
                    && !parametros.getValidadorCategoria()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorEstado()
                    && !parametros.getValidadorMunicipio()) {

/*                System.out.println("Selecionado apenas por ano");
                System.out.println("Ano2003: " + parametros.getAno2003());
                System.out.println("Ano2004: " + parametros.getAno2004());
                System.out.println("Ano2005: " + parametros.getAno2005());
                System.out.println("Ano2006: " + parametros.getAno2006());
                System.out.println("Ano2007: " + parametros.getAno2007());
                System.out.println("Ano2008: " + parametros.getAno2008());
                System.out.println("Ano2009: " + parametros.getAno2009());
                System.out.println("Ano2010: " + parametros.getAno2010());
                System.out.println("Ano2011: " + parametros.getAno2011());
                System.out.println("Ano2012: " + parametros.getAno2012());
                System.out.println("Ano2013: " + parametros.getAno2013());
                System.out.println("Ano2014: " + parametros.getAno2014());
                System.out.println("Ano2015: " + parametros.getAno2015());
                System.out.println("Ano2016: " + parametros.getAno2016());
                System.out.println("Ano2017: " + parametros.getAno2017());
                System.out.println("Ano2018: " + parametros.getAno2018());
                System.out.println("Ano2019: " + parametros.getAno2019());
                System.out.println("Ano2020: " + parametros.getAno2020());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE (anoDeInicioOperacao = :2003 " +
                        "OR anoDeInicioOperacao = :2004 " +
                        "OR anoDeInicioOperacao = :2005 " +
                        "OR anoDeInicioOperacao = :2006 " +
                        "OR anoDeInicioOperacao = :2007 " +
                        "OR anoDeInicioOperacao = :2008 " +
                        "OR anoDeInicioOperacao = :2009 " +
                        "OR anoDeInicioOperacao = :2010 " +
                        "OR anoDeInicioOperacao = :2011 " +
                        "OR anoDeInicioOperacao = :2012 " +
                        "OR anoDeInicioOperacao = :2013 " +
                        "OR anoDeInicioOperacao = :2014 " +
                        "OR anoDeInicioOperacao = :2015 " +
                        "OR anoDeInicioOperacao = :2016 " +
                        "OR anoDeInicioOperacao = :2017 " +
                        "OR anoDeInicioOperacao = :2018 " +
                        "OR anoDeInicioOperacao = :2019 " +
                        "OR anoDeInicioOperacao = :2020) " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros anos
                query.setParameter("2003", parametros.getAno2003());
                query.setParameter("2004", parametros.getAno2004());
                query.setParameter("2005", parametros.getAno2005());
                query.setParameter("2006", parametros.getAno2006());
                query.setParameter("2007", parametros.getAno2007());
                query.setParameter("2008", parametros.getAno2008());
                query.setParameter("2009", parametros.getAno2009());
                query.setParameter("2010", parametros.getAno2010());
                query.setParameter("2011", parametros.getAno2011());
                query.setParameter("2012", parametros.getAno2012());
                query.setParameter("2013", parametros.getAno2013());
                query.setParameter("2014", parametros.getAno2014());
                query.setParameter("2015", parametros.getAno2015());
                query.setParameter("2016", parametros.getAno2016());
                query.setParameter("2017", parametros.getAno2017());
                query.setParameter("2018", parametros.getAno2018());
                query.setParameter("2019", parametros.getAno2019());
                query.setParameter("2020", parametros.getAno2020());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));

            } else if (parametros.getValidadorCategoria()
                    && !parametros.getValidadorSituacao()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorAplicacao()
                    && !parametros.getValidadorAno()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorEstado()
                    && !parametros.getValidadorMunicipio()) {

/*                System.out.println("Selecionado apenas por Categoria");
                System.out.println("Agropecuaria: " + parametros.getCategoriaAgropecuaria());
                System.out.println("Esgoto: " + parametros.getCategoriaEsgoto());
                System.out.println("Industria: " + parametros.getCategoriaIndustria());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE (categoria = :agropecuaria " +
                        "OR categoria = :esgoto " +
                        "OR categoria = :industria) " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros categoria
                query.setParameter("agropecuaria", parametros.getCategoriaAgropecuaria());
                query.setParameter("esgoto", parametros.getCategoriaEsgoto());
                query.setParameter("industria", parametros.getCategoriaIndustria());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));

            } else if (parametros.getValidadorAplicacao()
                    && !parametros.getValidadorSituacao()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorCategoria()
                    && !parametros.getValidadorAno()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorEstado()
                    && !parametros.getValidadorMunicipio()) {

/*                System.out.println("Selecionado apenas por aplicacao");
                System.out.println("AplicacaoTermica: " + parametros.getAplicacaoTermica());
                System.out.println("AplicacaoEletrica: " + parametros.getAplicacaoEletrica());
                System.out.println("AplicacaoMecanica: " + parametros.getAplicacaoMecanica());
                System.out.println("AplicacaoBiometano: " + parametros.getAplicacaoBiometano());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE (biogasParaEnergiaTermica = :AplicacaoTermica " +
                        "OR biogasParaEnergiaEletrica = :AplicacaoEletrica " +
                        "OR biogasParaEnergiaMecanica = :AplicacaoMecanica " +
                        "OR biogasParaBiometano = :AplicacaoBiometano) " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros aplicacao
                query.setParameter("AplicacaoTermica", parametros.getAplicacaoTermica());
                query.setParameter("AplicacaoEletrica", parametros.getAplicacaoEletrica());
                query.setParameter("AplicacaoMecanica", parametros.getAplicacaoMecanica());
                query.setParameter("AplicacaoBiometano", parametros.getAplicacaoBiometano());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));

            } else if (parametros.getValidadorEstado()
                    && !parametros.getValidadorSituacao()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorCategoria()
                    && !parametros.getValidadorAno()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorAplicacao()
                    && !parametros.getValidadorMunicipio()) {

/*                System.out.println("Selecionado apenas por estado");
                System.out.println("Estado: " + parametros.getEstado());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE (municipio.estado.id = :Estado) " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros estado
                query.setParameter("Estado", parametros.getEstado());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));

            } else if (parametros.getValidadorMunicipio()
                    && parametros.getValidadorEstado()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorCategoria()
                    && !parametros.getValidadorAno()
                    && !parametros.getValidadorEscala()
                    && !parametros.getValidadorAplicacao()
                    && !parametros.getValidadorSituacao()) {

/*                System.out.println("Selecionado apenas Estado e Município");
                System.out.println("Estado: " + parametros.getEstado());
                System.out.println("Municipio: " + parametros.getMunicipio());*/

                Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                        "WHERE (municipio.estado.id = :Estado " +
                        "AND municipio.id = :Municipio) " +
                        "AND status = :status " +
                        "ORDER BY codigo");

                //Status das unidades sempre aprovado
                query.setParameter("status", "APROVADO");

                //Parametros estado
                query.setParameter("Estado", parametros.getEstado());
                query.setParameter("Municipio", parametros.getMunicipio());

                List<Unidade> filtroDeUnidades = query.findList();

                return ok(Json.toJson(filtroDeUnidades));
                //FIM - Conjunto de IF para filtrar unidades apenas por tipos de botoes
            } else {

                //else para tratamento de filtro de unidades com localizacao, sem localizacao de forma unificada e com todos os parametros selecionados

                if (parametros.getEstado() == 0 && parametros.getMunicipio() == 0) {

                    //System.out.println("Filtro sem Estado, sem Município");

                    Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                            "WHERE (situacao = :situacaoOperando " +
                            "OR situacao = :situacaoConstruindo " +
                            "OR situacao = :situacaoReformando) " +
                            "AND (biogasParaEnergiaTermica = :AplicacaoTermica " +
                            "OR biogasParaEnergiaEletrica = :AplicacaoEletrica " +
                            "OR biogasParaEnergiaMecanica = :AplicacaoMecanica " +
                            "OR biogasParaBiometano = :AplicacaoBiometano) " +
                            "AND (categoria = :agropecuaria " +
                            "OR categoria = :esgoto " +
                            "OR categoria = :industria) " +
                            "AND (anoDeInicioOperacao = :2003 " +
                            "OR anoDeInicioOperacao = :2004 " +
                            "OR anoDeInicioOperacao = :2005 " +
                            "OR anoDeInicioOperacao = :2006 " +
                            "OR anoDeInicioOperacao = :2007 " +
                            "OR anoDeInicioOperacao = :2008 " +
                            "OR anoDeInicioOperacao = :2009 " +
                            "OR anoDeInicioOperacao = :2010 " +
                            "OR anoDeInicioOperacao = :2011 " +
                            "OR anoDeInicioOperacao = :2012 " +
                            "OR anoDeInicioOperacao = :2013 " +
                            "OR anoDeInicioOperacao = :2014 " +
                            "OR anoDeInicioOperacao = :2015 " +
                            "OR anoDeInicioOperacao = :2016 " +
                            "OR anoDeInicioOperacao = :2017 " +
                            "OR anoDeInicioOperacao = :2018 " +
                            "OR anoDeInicioOperacao = :2019 " +
                            "OR anoDeInicioOperacao = :2020) " +
                            "AND (producaoBiogasAno BETWEEN :escalaInicial AND :escalaFinal) " +
                            "AND status = :status " +
                            "ORDER by codigo");

                    //Parametros situacao
                    query.setParameter("situacaoOperando", parametros.getSituacaoOperando());
                    query.setParameter("situacaoConstruindo", parametros.getSituacaoConstruindo());
                    query.setParameter("situacaoReformando", parametros.getSituacaoReformando());

                    //Parametros aplicacao
                    query.setParameter("AplicacaoTermica", parametros.getAplicacaoTermica());
                    query.setParameter("AplicacaoEletrica", parametros.getAplicacaoEletrica());
                    query.setParameter("AplicacaoMecanica", parametros.getAplicacaoMecanica());
                    query.setParameter("AplicacaoBiometano", parametros.getAplicacaoBiometano());

                    //Parametros categoria
                    query.setParameter("agropecuaria", parametros.getCategoriaAgropecuaria());
                    query.setParameter("esgoto", parametros.getCategoriaEsgoto());
                    query.setParameter("industria", parametros.getCategoriaIndustria());

                    //Parametros anos
                    query.setParameter("2003", parametros.getAno2003());
                    query.setParameter("2004", parametros.getAno2004());
                    query.setParameter("2005", parametros.getAno2005());
                    query.setParameter("2006", parametros.getAno2006());
                    query.setParameter("2007", parametros.getAno2007());
                    query.setParameter("2008", parametros.getAno2008());
                    query.setParameter("2009", parametros.getAno2009());
                    query.setParameter("2010", parametros.getAno2010());
                    query.setParameter("2011", parametros.getAno2011());
                    query.setParameter("2012", parametros.getAno2012());
                    query.setParameter("2013", parametros.getAno2013());
                    query.setParameter("2014", parametros.getAno2014());
                    query.setParameter("2015", parametros.getAno2015());
                    query.setParameter("2016", parametros.getAno2016());
                    query.setParameter("2017", parametros.getAno2017());
                    query.setParameter("2018", parametros.getAno2018());
                    query.setParameter("2019", parametros.getAno2019());
                    query.setParameter("2020", parametros.getAno2020());

                    //Parametros das escalas
                    query.setParameter("escalaInicial", parametros.getEscalaInicial());
                    query.setParameter("escalaFinal", parametros.getEscalaFinal());

                    //Status das unidades sempre aprovado
                    query.setParameter("status", "APROVADO");

                    List<Unidade> filtroDeUnidades = query.findList();

                    return ok(Json.toJson(filtroDeUnidades));

                } else if (parametros.getMunicipio() == 0 && parametros.getEstado() > 0) {

                    //System.out.println("Filtro com estado");

                    Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                            "WHERE (situacao = :situacaoOperando " +
                            "OR situacao = :situacaoConstruindo " +
                            "OR situacao = :situacaoReformando) " +
                            "AND (biogasParaEnergiaTermica = :AplicacaoTermica " +
                            "OR biogasParaEnergiaEletrica = :AplicacaoEletrica " +
                            "OR biogasParaEnergiaMecanica = :AplicacaoMecanica " +
                            "OR biogasParaBiometano = :AplicacaoBiometano) " +
                            "AND (categoria = :agropecuaria " +
                            "OR categoria = :esgoto " +
                            "OR categoria = :industria) " +
                            "AND (anoDeInicioOperacao = :2003 " +
                            "OR anoDeInicioOperacao = :2004 " +
                            "OR anoDeInicioOperacao = :2005 " +
                            "OR anoDeInicioOperacao = :2006 " +
                            "OR anoDeInicioOperacao = :2007 " +
                            "OR anoDeInicioOperacao = :2008 " +
                            "OR anoDeInicioOperacao = :2009 " +
                            "OR anoDeInicioOperacao = :2010 " +
                            "OR anoDeInicioOperacao = :2011 " +
                            "OR anoDeInicioOperacao = :2012 " +
                            "OR anoDeInicioOperacao = :2013 " +
                            "OR anoDeInicioOperacao = :2014 " +
                            "OR anoDeInicioOperacao = :2015 " +
                            "OR anoDeInicioOperacao = :2016 " +
                            "OR anoDeInicioOperacao = :2017 " +
                            "OR anoDeInicioOperacao = :2018 " +
                            "OR anoDeInicioOperacao = :2019 " +
                            "OR anoDeInicioOperacao = :2020) " +
                            "AND (producaoBiogasAno BETWEEN :escalaInicial AND :escalaFinal) " +
                            "AND (municipio.estado.id = :Estado) " +
                            "AND status = :status " +
                            "ORDER BY codigo");

                    //Parametros situacao
                    query.setParameter("situacaoOperando", parametros.getSituacaoOperando());
                    query.setParameter("situacaoConstruindo", parametros.getSituacaoConstruindo());
                    query.setParameter("situacaoReformando", parametros.getSituacaoReformando());

                    //Parametros aplicacao
                    query.setParameter("AplicacaoTermica", parametros.getAplicacaoTermica());
                    query.setParameter("AplicacaoEletrica", parametros.getAplicacaoEletrica());
                    query.setParameter("AplicacaoMecanica", parametros.getAplicacaoMecanica());
                    query.setParameter("AplicacaoBiometano", parametros.getAplicacaoBiometano());

                    //Parametros categoria
                    query.setParameter("agropecuaria", parametros.getCategoriaAgropecuaria());
                    query.setParameter("esgoto", parametros.getCategoriaEsgoto());
                    query.setParameter("industria", parametros.getCategoriaIndustria());

                    //Parametros anos
                    query.setParameter("2003", parametros.getAno2003());
                    query.setParameter("2004", parametros.getAno2004());
                    query.setParameter("2005", parametros.getAno2005());
                    query.setParameter("2006", parametros.getAno2006());
                    query.setParameter("2007", parametros.getAno2007());
                    query.setParameter("2008", parametros.getAno2008());
                    query.setParameter("2009", parametros.getAno2009());
                    query.setParameter("2010", parametros.getAno2010());
                    query.setParameter("2011", parametros.getAno2011());
                    query.setParameter("2012", parametros.getAno2012());
                    query.setParameter("2013", parametros.getAno2013());
                    query.setParameter("2014", parametros.getAno2014());
                    query.setParameter("2015", parametros.getAno2015());
                    query.setParameter("2016", parametros.getAno2016());
                    query.setParameter("2017", parametros.getAno2017());
                    query.setParameter("2018", parametros.getAno2018());
                    query.setParameter("2019", parametros.getAno2019());
                    query.setParameter("2020", parametros.getAno2020());

                    //Parametros das escalas
                    query.setParameter("escalaInicial", parametros.getEscalaInicial());
                    query.setParameter("escalaFinal", parametros.getEscalaFinal());

                    //Parametros estado
                    query.setParameter("Estado", parametros.getEstado());

                    //Status das unidades sempre aprovado
                    query.setParameter("status", "APROVADO");

                    List<Unidade> filtroDeUnidades = query.findList();

                    return ok(Json.toJson(filtroDeUnidades));

                } else {

                    //System.out.println("Filtro com Estado e Município");

                    Query<Unidade> query = Ebean.createQuery(Unidade.class, "FIND unidade " +
                            "WHERE (situacao = :situacaoOperando " +
                            "OR situacao = :situacaoConstruindo " +
                            "OR situacao = :situacaoReformando) " +
                            "AND (biogasParaEnergiaTermica = :AplicacaoTermica " +
                            "OR biogasParaEnergiaEletrica = :AplicacaoEletrica " +
                            "OR biogasParaEnergiaMecanica = :AplicacaoMecanica " +
                            "OR biogasParaBiometano = :AplicacaoBiometano) " +
                            "AND (categoria = :agropecuaria " +
                            "OR categoria = :esgoto " +
                            "OR categoria = :industria) " +
                            "AND (anoDeInicioOperacao = :2003 " +
                            "OR anoDeInicioOperacao = :2004 " +
                            "OR anoDeInicioOperacao = :2005 " +
                            "OR anoDeInicioOperacao = :2006 " +
                            "OR anoDeInicioOperacao = :2007 " +
                            "OR anoDeInicioOperacao = :2008 " +
                            "OR anoDeInicioOperacao = :2009 " +
                            "OR anoDeInicioOperacao = :2010 " +
                            "OR anoDeInicioOperacao = :2011 " +
                            "OR anoDeInicioOperacao = :2012 " +
                            "OR anoDeInicioOperacao = :2013 " +
                            "OR anoDeInicioOperacao = :2014 " +
                            "OR anoDeInicioOperacao = :2015 " +
                            "OR anoDeInicioOperacao = :2016 " +
                            "OR anoDeInicioOperacao = :2017 " +
                            "OR anoDeInicioOperacao = :2018 " +
                            "OR anoDeInicioOperacao = :2019 " +
                            "OR anoDeInicioOperacao = :2020) " +
                            "AND (producaoBiogasAno BETWEEN :escalaInicial AND :escalaFinal) " +
                            "AND (municipio.estado.id = :Estado " +
                            "AND municipio.id = :Municipio) " +
                            "AND status = :status " +
                            "ORDER BY codigo");

                    //Parametros situacao
                    query.setParameter("situacaoOperando", parametros.getSituacaoOperando());
                    query.setParameter("situacaoConstruindo", parametros.getSituacaoConstruindo());
                    query.setParameter("situacaoReformando", parametros.getSituacaoReformando());

                    //Parametros aplicacao
                    query.setParameter("AplicacaoTermica", parametros.getAplicacaoTermica());
                    query.setParameter("AplicacaoEletrica", parametros.getAplicacaoEletrica());
                    query.setParameter("AplicacaoMecanica", parametros.getAplicacaoMecanica());
                    query.setParameter("AplicacaoBiometano", parametros.getAplicacaoBiometano());

                    //Parametros categoria
                    query.setParameter("agropecuaria", parametros.getCategoriaAgropecuaria());
                    query.setParameter("esgoto", parametros.getCategoriaEsgoto());
                    query.setParameter("industria", parametros.getCategoriaIndustria());

                    //Parametros anos
                    query.setParameter("2003", parametros.getAno2003());
                    query.setParameter("2004", parametros.getAno2004());
                    query.setParameter("2005", parametros.getAno2005());
                    query.setParameter("2006", parametros.getAno2006());
                    query.setParameter("2007", parametros.getAno2007());
                    query.setParameter("2008", parametros.getAno2008());
                    query.setParameter("2009", parametros.getAno2009());
                    query.setParameter("2010", parametros.getAno2010());
                    query.setParameter("2011", parametros.getAno2011());
                    query.setParameter("2012", parametros.getAno2012());
                    query.setParameter("2013", parametros.getAno2013());
                    query.setParameter("2014", parametros.getAno2014());
                    query.setParameter("2015", parametros.getAno2015());
                    query.setParameter("2016", parametros.getAno2016());
                    query.setParameter("2017", parametros.getAno2017());
                    query.setParameter("2018", parametros.getAno2018());
                    query.setParameter("2019", parametros.getAno2019());
                    query.setParameter("2020", parametros.getAno2020());

                    //Parametros das escalas
                    query.setParameter("escalaInicial", parametros.getEscalaInicial());
                    query.setParameter("escalaFinal", parametros.getEscalaFinal());

                    //Parametros estado
                    query.setParameter("Estado", parametros.getEstado());
                    query.setParameter("Municipio", parametros.getMunicipio());

                    //Status das unidades sempre aprovado
                    query.setParameter("status", "APROVADO");

                    List<Unidade> filtroDeUnidades = query.findList();

                    return ok(Json.toJson(filtroDeUnidades));

                }

            }

        } catch (Exception e) {

            return badRequest(e.getMessage());

        }

    }

    /**
     * Retrieve a list of all objects
     *
     * @return a list of all objects in a render template
     */
    @Security.Authenticated(SecuredAdmin.class)
    public Result telaLista(int page, String sortBy, String order, String filter) {

        //Necessario para verificar se o usuario e gerente
        if (usuarioAtual().isPresent()) {
            Usuario usuario = usuarioAtual().get();
            if (usuario.isGerente()) {
                return forbidden(views.html.mensagens.erro.naoAutorizado.render());
            }
        }

        try {
            return ok(
                    list.render(
                            Unidade.page(page, 14, sortBy, order, filter, listForm),
                            sortBy, order, filter, listForm
                    )
            );
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest(views.html.error.render(e.getMessage()));
        }

    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result telaNovo(Long id) {

        //Necessario para verificar se o usuario e gerente
        if (usuarioAtual().isPresent()) {
            Usuario usuario = usuarioAtual().get();
            if (usuario.isGerente()) {
                return forbidden(views.html.mensagens.erro.naoAutorizado.render());
            }
        }

        UnidadeFormData unidadeData = (id == 0) ? new UnidadeFormData() : models.Unidade.makeUnidadeFormData(id);
        Form<UnidadeFormData> unidadeForm = form(UnidadeFormData.class);

        return ok(views.html.admin.unidades.create.render(unidadeForm,
                Unidade.makeCategoriaMap(unidadeData),
                Unidade.makeSituacaoMap(unidadeData),
                Unidade.makeEstadoMap(unidadeData)));
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result telaDetalhe(Long id) {

        Form<UnidadeFormData> unidadeForm = form(UnidadeFormData.class);

        try {
            Unidade unidade = Ebean.find(Unidade.class, id);

            if (unidade == null) {
                return notFound(views.html.mensagens.erro.naoEncontrado.render("Unidade não encontrada"));
            }

            return ok(views.html.admin.unidades.detail.render(unidadeForm, unidade));
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest(views.html.error.render(e.getMessage()));
        }

    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result inserir(Long id) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        //Cria uma instancia do formData Validador
        UnidadeFormData unidadeData = (id == 0) ? new UnidadeFormData() : models.Unidade.makeUnidadeFormData(id);

        //Resgata os dados do formulario atraves de uma requisicao
        Form<UnidadeFormData> formData = Form.form(UnidadeFormData.class).bindFromRequest();

        //se existir erros nos campos do formulario retorne o ArtigoFormData com os erros
        if (formData.hasErrors()) {
            return badRequest(views.html.admin.unidades.create.render(formData, Unidade.makeCategoriaMap(unidadeData), Unidade.makeSituacaoMap(unidadeData), Unidade.makeEstadoMap(unidadeData)));
        } else {

            try {

                //faz uma busca na base de dados
                Unidade unidadeBusca = Ebean.find(Unidade.class).where().eq("codigo", Integer.valueOf(formData.data().get("codigo"))).findUnique();

                if (unidadeBusca != null) {
                    formData.reject(new ValidationError("codigo", "A Unidade com o código '" + unidadeBusca.getCodigo() + "' já esta Cadastrado!"));
                    return badRequest(views.html.admin.unidades.create.render(formData, Unidade.makeCategoriaMap(unidadeData), Unidade.makeSituacaoMap(unidadeData), Unidade.makeEstadoMap(unidadeData)));
                }

                if (formData.data().get("municipio") == null || formData.data().get("municipio").equals("0")) {
                    formData.reject("Selecione o estado e o municipio.");
                    return badRequest(views.html.admin.unidades.create.render(formData, Unidade.makeCategoriaMap(unidadeData), Unidade.makeSituacaoMap(unidadeData), Unidade.makeEstadoMap(unidadeData)));

                }

                //Converte os dados do formularios para uma instancia do objeto
                Unidade unidade = Unidade.makeInstance(formData.get());

                Float producaoBiogasAnual = unidade.getProducaoBiogasAno();

                if ((producaoBiogasAnual > 0) && (producaoBiogasAnual <= 500000)) {

                    unidade.setEscala(escalaDAO.comPorte(1L).get());

                } else if ((producaoBiogasAnual >= 500001) && (producaoBiogasAnual <= 1000000)) {

                    unidade.setEscala(escalaDAO.comPorte(2L).get());

                } else if ((producaoBiogasAnual >= 1000001) && (producaoBiogasAnual <= 3500000)) {

                    unidade.setEscala(escalaDAO.comPorte(3L).get());

                } else if ((producaoBiogasAnual >= 3500001) && (producaoBiogasAnual <= 5000000)) {

                    unidade.setEscala(escalaDAO.comPorte(4L).get());

                } else if ((producaoBiogasAnual >= 5000001) && (producaoBiogasAnual <= 30000000)) {

                    unidade.setEscala(escalaDAO.comPorte(5L).get());

                } else if ((producaoBiogasAnual >= 30000001) && (producaoBiogasAnual <= 125000000)) {

                    unidade.setEscala(escalaDAO.comPorte(6L).get());

                } else {

                    unidade.setEscala(escalaDAO.comPorte(7L).get());

                }

                Municipio municipioBusca = Ebean.find(Municipio.class, formData.data().get("municipio"));

                unidade.setDataCadastro(Calendar.getInstance());
                unidade.setDataAlteracao(Calendar.getInstance());
                unidade.setMunicipio(municipioBusca);
                unidade.setStatus(models.Status.AVALIAR);

                unidade.save();

                if (usuarioAtual().isPresent()) {
                    formatter.format("Usuário Administrador: '%1s' cadastrou uma nova Unidade com codigo: '%2s'.", usuarioAtual().get().getEmail(), unidade.getCodigo());
                    logController.inserir(sb.toString());
                }

                flash("success", "Cadastrado unidade com codigo - " + unidade.getCodigo());
                return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
            } catch (Exception e) {
                Logger.error(e.getMessage());
                formData.reject("Erro interno de Sistema. Descrição: " + e);
                return badRequest(views.html.admin.unidades.create.render(formData,
                        Unidade.makeCategoriaMap(unidadeData),
                        Unidade.makeSituacaoMap(unidadeData),
                        Unidade.makeEstadoMap(unidadeData)));
            }

        }

    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result salvarCSV() {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart csv = body.getFile("csv");

        int contador = 0;
        int contadorIgnoradas = 0;
        String codigosIgnorados = "";

        try {
            if (csv != null) {

                /*Cada linha do arquivo CSV*/
                String linha;

                //Pega o arquivo pdf da requisicao recebida
                File file = csv.getFile();

                BufferedReader bReader = new BufferedReader(new FileReader(file));

                while ((linha = bReader.readLine()) != null) {

                    /*Linha de cada unidade do Csv*/
                    String[] unidadeCsv = linha.split(",+");

                    //fazer uma busca no banco de dados se existe essa unidade.
                    Optional<Unidade> unidadeBusca = unidadeDAO.comCodigoUnico(Integer.parseInt(unidadeCsv[1]));

                    if (!unidadeBusca.isPresent()) {

                        /*Cria instancia de cada unidade*/
                        Unidade unidade = new Unidade();

                        Optional<Categoria> categoria = categoriaDAO.comNome(unidadeCsv[8]);
                        Optional<Situacao> situacao = situacaoDAO.comNome(unidadeCsv[10]);
                        Optional<Municipio> municipio = municipioDAO.comNomeEUf(unidadeCsv[9], unidadeCsv[13]);

                        if (situacao.isPresent() && categoria.isPresent() && municipio.isPresent()) {

                            unidade.setAnoDeInicioOperacao(unidadeCsv[0]);
                            unidade.setCodigo(Integer.parseInt(unidadeCsv[1]));

                            if (unidadeCsv[2].equals("Sim")) {
                                unidade.setBiogasParaEnergiaTermica(true);
                            } else {
                                unidade.setBiogasParaEnergiaTermica(false);
                            }

                            if (unidadeCsv[3].equals("Sim")) {
                                unidade.setBiogasParaEnergiaEletrica(true);
                            } else {
                                unidade.setBiogasParaEnergiaEletrica(false);
                            }

                            if (unidadeCsv[4].equals("Sim")) {
                                unidade.setBiogasParaEnergiaMecanica(true);
                            } else {
                                unidade.setBiogasParaEnergiaMecanica(false);
                            }

                            if (unidadeCsv[5].equals("Sim")) {
                                unidade.setBiogasParaBiometano(true);
                            } else {
                                unidade.setBiogasParaBiometano(false);
                            }

                            //Producao Biogas
                            unidade.setProducaoBiogasAno(Float.parseFloat(unidadeCsv[6]));
                            unidade.setProducaoBiogasDia(Float.parseFloat(unidadeCsv[7]));

                            //Para calcular o porte da unidade
                            Float producaoAnual = Float.parseFloat(unidadeCsv[6]);

                            //Verificador de escala
                            /* < 500.000 Nm3/ano - Porte 1
                             * 500.001 a 1.000.000 Nm3/ano - Porte 2
                             * 1.000.001 a 3.500.000 Nm3/ano - Porte 3
                             * 3.500.001 a 5.000.000 Nm3/ano - Porte 4
                             * 5.000.001 a 30.000.000 Nm3/ano - Porte 5
                             * 30.000.001 a 125.000.000 Nm3/ano - Porte 6
                             * > 125.000.001 Nm3/ano - Porte 7
                             */
                            if ((producaoAnual > 0) && (producaoAnual <= 500000)) {

                                unidade.setEscala(escalaDAO.comPorte(1L).get());

                            } else if ((producaoAnual >= 500001) && (producaoAnual <= 1000000)) {

                                unidade.setEscala(escalaDAO.comPorte(2L).get());

                            } else if ((producaoAnual >= 1000001) && (producaoAnual <= 3500000)) {

                                unidade.setEscala(escalaDAO.comPorte(3L).get());

                            } else if ((producaoAnual >= 3500001) && (producaoAnual <= 5000000)) {

                                unidade.setEscala(escalaDAO.comPorte(4L).get());

                            } else if ((producaoAnual >= 5000001) && (producaoAnual <= 30000000)) {

                                unidade.setEscala(escalaDAO.comPorte(5L).get());

                            } else if ((producaoAnual >= 30000001) && (producaoAnual <= 125000000)) {

                                unidade.setEscala(escalaDAO.comPorte(6L).get());

                            } else {

                                unidade.setEscala(escalaDAO.comPorte(7L).get());

                            }

                            unidade.setSituacao(situacao.get());
                            unidade.setCategoria(categoria.get());
                            unidade.setMunicipio(municipio.get());

                            unidade.setLatitude(Double.parseDouble(unidadeCsv[11]));
                            unidade.setLongitude(Double.parseDouble(unidadeCsv[12]));

                            unidade.setStatus(models.Status.APROVADO);
                            unidade.setValorEstimado(false);

                            //Producao de substratos
                            unidade.setProducaoSubstratoAno((float) 0.0);
                            unidade.setProducaoSubstratoDia((float) 0.0);

                            //Data de cadastro e alteracao
                            unidade.setDataCadastro(Calendar.getInstance());
                            unidade.setDataAlteracao(Calendar.getInstance());

                            //Salva no banco de dados
                            Ebean.save(unidade);

                            contador++;

                        } else {
                            flash("danger", "Nao foi possivel cadastrar as unidades. Selecione um arquivo no formato .CSV com encoding UTF-8 e no formato valido.");
                            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
                        }

                    } else {
                        contadorIgnoradas++;
                        codigosIgnorados = codigosIgnorados.concat(unidadeBusca.get().getCodigo() + " ");
                    }
                }

                if (usuarioAtual().isPresent()) {
                    formatter.format("Usuário Administrador: '%1s' cadastrou um total de '%2s' unidades e o sistema ignorou as unidades com codigo '%3s'", usuarioAtual().get().getEmail(), contador, codigosIgnorados);
                    logController.inserir(sb.toString());
                }

                flash("info", "Cadastrado um total de " + contador + " novas unidades e ignorado um total de " + contadorIgnoradas + ".");
                return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
            } else {
                flash("danger", "Selecione um arquivo no formato .CSV");
                return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
            }
        } catch (Exception e) {
            Logger.error(e.toString());
            flash("danger", "Nao foi possivel ler o arquivo: " + e.getLocalizedMessage());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
        }

    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result telaEditar(Long id) {

        try {
            //logica onde instanciamos um objeto encontrado na base dados e o colocamos no formData
            UnidadeFormData unidadeFormData = (id == 0) ? new UnidadeFormData() : models.Unidade.makeUnidadeFormData(id);

            //apos o objeto ser instanciado passamos os dados para o formdata e os dados serao carregados no form edit
            Form<UnidadeFormData> formData = Form.form(UnidadeFormData.class).fill(unidadeFormData);

            return ok(views.html.admin.unidades.edit.render(id,
                    formData,
                    Unidade.makeCategoriaMap(unidadeFormData),
                    Unidade.makeSituacaoMap(unidadeFormData),
                    Unidade.makeEstadoMap(unidadeFormData),
                    Unidade.makeMunicipioPorEstado(unidadeFormData, formData.get().estado)));
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest(views.html.error.render(e.getMessage()));
        }
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result editar(Long id) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        //Cria uma instancia do formData Validador
        UnidadeFormData unidadeData = (id == 0) ? new UnidadeFormData() : models.Unidade.makeUnidadeFormData(id);

        //Resgata os dados do formulario atraves de uma requisicao
        Form<UnidadeFormData> formData = Form.form(UnidadeFormData.class).bindFromRequest();

        System.out.println("Estado: " + unidadeData.estado);

        //verificar se tem erros no formData, caso tiver retorna o formulario com os erros e caso não tiver continua o processo de alteracao do objeto
        if (formData.hasErrors()) {
            return badRequest(views.html.admin.unidades.edit.render(id,
                    formData,
                    Unidade.makeCategoriaMap(unidadeData),
                    Unidade.makeSituacaoMap(unidadeData),
                    Unidade.makeEstadoMap(unidadeData),
                    Unidade.makeMunicipioPorEstado(unidadeData, unidadeData.estado)));
        } else {

            try {

                if (formData.data().get("municipio") == null || formData.data().get("municipio").equals("0")) {
                    formData.reject("Selecione o estado e o municipio.");
                    return badRequest(views.html.admin.unidades.edit.render(id,
                            formData,
                            Unidade.makeCategoriaMap(unidadeData),
                            Unidade.makeSituacaoMap(unidadeData),
                            Unidade.makeEstadoMap(unidadeData),
                            Unidade.makeMunicipioPorEstado(unidadeData, formData.get().estado)));

                }

                //Converte os dados do formularios para uma instancia do Objeto
                Unidade unidade = Unidade.makeInstance(formData.get());

                Float producaoBiogasAnual = unidade.getProducaoBiogasAno();

                if ((producaoBiogasAnual > 0) && (producaoBiogasAnual <= 500000)) {

                    unidade.setEscala(escalaDAO.comPorte(1L).get());

                } else if ((producaoBiogasAnual >= 500001) && (producaoBiogasAnual <= 1000000)) {

                    unidade.setEscala(escalaDAO.comPorte(2L).get());

                } else if ((producaoBiogasAnual >= 1000001) && (producaoBiogasAnual <= 3500000)) {

                    unidade.setEscala(escalaDAO.comPorte(3L).get());

                } else if ((producaoBiogasAnual >= 3500001) && (producaoBiogasAnual <= 5000000)) {

                    unidade.setEscala(escalaDAO.comPorte(4L).get());

                } else if ((producaoBiogasAnual >= 5000001) && (producaoBiogasAnual <= 30000000)) {

                    unidade.setEscala(escalaDAO.comPorte(5L).get());

                } else if ((producaoBiogasAnual >= 30000001) && (producaoBiogasAnual <= 125000000)) {

                    unidade.setEscala(escalaDAO.comPorte(6L).get());

                } else {

                    unidade.setEscala(escalaDAO.comPorte(7L).get());

                }

                //Verificador caso o usuario altere todos os campos menos os selects de estado e municipio
                //Isso foi necessario porque ao carregar a pagina de edicao o map de selecao nao tem o ID da unidade
                //Mas quando um novo estado foi selecionado temos o id dos municipios
                if (isNumeric(formData.data().get("municipio"))) {
                    Municipio municipio = Ebean.find(Municipio.class, formData.data().get("municipio"));
                    unidade.setMunicipio(municipio);
                }

                unidade.setId(id);
                unidade.setDataAlteracao(Calendar.getInstance());
                unidade.update();

                if (usuarioAtual().isPresent()) {
                    formatter.format("Usuário Administrador: '%1s' alterou a unidade com codigo '%2s'.", usuarioAtual().get().getEmail(), unidade.getCodigo());
                    logController.inserir(sb.toString());
                }

                flash("success", "Alterado unidade com codigo - " + unidade.getCodigo());
                return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
            } catch (Exception e) {
                flash("danger", "Nao foi possivel alterar unidade. Erro: " + e.getLocalizedMessage());
                return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));

            }
        }

    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result remover(Long id) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        try {

            //busca a publicacao para ser excluido
            Unidade unidade = Ebean.find(Unidade.class, id);

            if (unidade == null) {
                return notFound(views.html.mensagens.erro.naoEncontrado.render("Unidade não encontrada"));
            }

            Ebean.delete(unidade);

            if (usuarioAtual().isPresent()) {
                formatter.format("Usuário: '%1s' excluiu a Unidade com codigo: '%2s'.", usuarioAtual().get().getEmail(), unidade.getCodigo());
                logController.inserir(sb.toString());
            }

            flash("success", "Removido unidade com codigo - " + unidade.getCodigo());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
        } catch (Exception e) {
            flash("danger", "Nao foi possivel remover unidade. Erro: " + e.getLocalizedMessage());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));

        }

    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result exportar() {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        Calendar agora = Calendar.getInstance();

        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd-MMMM-yyyy");

        String data = dataFormatada.format(agora.getTime());

        List<Unidade> unidades = null;

        try {

            if (usuarioAtual().get().isAdmin()) {

                //Criterio de busca
                unidades = Ebean.find(Unidade.class).orderBy("codigo").findList();

            }

            FileWriter fileWriter = new FileWriter(new File(System.getProperty("user.home") + "/biogasmap-" + usuarioAtual().get().getNome() + "-" + data + ".csv"));

            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Cada linha do csv
            //Nome,Municipio,Estado,Telefone,QuantidadeBiogas,QuantidadeEnergia,QuantidadeBotijao,QuantidadeCasa
            for (Unidade unidade : unidades) {

                fileWriter.append(Integer.toString(unidade.getCodigo()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(unidade.getAnoDeInicioOperacao());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(unidade.getSituacao().getNome());
                if (unidade.getEscala().getIntervalo() == 1) {
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("Pequeno");
                }
                if (unidade.getEscala().getIntervalo() == 2) {
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("Médio");
                }
                if (unidade.getEscala().getIntervalo() == 3) {
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("Grande");
                }
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(unidade.getCategoria().getNome());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(unidade.getMunicipio().getNome());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(unidade.getMunicipio().getUf());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Float.toString(unidade.getProducaoBiogasAno()));
                fileWriter.append(COMMA_DELIMITER);
                if (unidade.getBiogasParaEnergiaTermica()) {
                    fileWriter.append("Sim");
                    fileWriter.append(COMMA_DELIMITER);
                } else {
                    fileWriter.append("Não");
                    fileWriter.append(COMMA_DELIMITER);
                }
                if (unidade.getBiogasParaEnergiaEletrica()) {
                    fileWriter.append("Sim");
                    fileWriter.append(COMMA_DELIMITER);
                } else {
                    fileWriter.append("Não");
                    fileWriter.append(COMMA_DELIMITER);
                }
                if (unidade.getBiogasParaEnergiaMecanica()) {
                    fileWriter.append("Sim");
                    fileWriter.append(COMMA_DELIMITER);
                } else {
                    fileWriter.append("Não");
                    fileWriter.append(COMMA_DELIMITER);
                }
                if (unidade.getBiogasParaBiometano()) {
                    fileWriter.append("Sim");
                    fileWriter.append(COMMA_DELIMITER);
                } else {
                    fileWriter.append("Não");
                    fileWriter.append(COMMA_DELIMITER);
                }
                fileWriter.append(Double.toString(unidade.getLatitude()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(unidade.getLongitude()));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            fileWriter.flush();
            fileWriter.close();

            Logger.info("Arquivo CSV criado com sucesso!");

            formatter.format("Usuário: '%1s' fez o download das Unidades em formato CSV.", usuarioAtual().get().getEmail());
            logController.inserir(sb.toString());

            return ok(new File(System.getProperty("user.home") + "/biogasmap-" + usuarioAtual().get().getNome() + "-" + data + ".csv")).as("text/csv");
        } catch (IOException e) {
            Logger.error(e.toString());
            return badRequest(e.getMessage());
        }
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result exportarExcel() {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetUnidades = workbook.createSheet("Biogasmap");

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        Calendar agora = Calendar.getInstance();

        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd-MMMM-yyyy");

        String data = dataFormatada.format(agora.getTime());

        List<Unidade> unidades;

        try {

            if (usuarioAtual().get().isAdmin()) {

                //Criterio de busca
                unidades = Ebean.find(Unidade.class).orderBy("codigo").findList();

                int rownum = 0;
                Row row = sheetUnidades.createRow(rownum++);

                //Adiciona o header da planilha
                row.createCell(0).setCellValue("codigo_unico");
                row.createCell(1).setCellValue("ano");
                row.createCell(2).setCellValue("situacao");
                row.createCell(3).setCellValue("porte");
                row.createCell(4).setCellValue("categoria");
                row.createCell(5).setCellValue("municipio");
                row.createCell(6).setCellValue("estado");
                row.createCell(7).setCellValue("producao_biogas_ano");
                row.createCell(8).setCellValue("energia_termica");
                row.createCell(9).setCellValue("energia_eletrica");
                row.createCell(10).setCellValue("energia_mecanica");
                row.createCell(11).setCellValue("biometano");
                row.createCell(12).setCellValue("latitude");
                row.createCell(13).setCellValue("longitude");

                for (Unidade unidade : unidades) {
                    row = sheetUnidades.createRow(rownum++);
                    int cellnum = 0;
                    Cell cellCodigo = row.createCell(cellnum++);
                    cellCodigo.setCellValue(unidade.getCodigo());
                    Cell cellAno = row.createCell(cellnum++);
                    cellAno.setCellValue(unidade.getAnoDeInicioOperacao());
                    Cell cellSituacao = row.createCell(cellnum++);
                    cellSituacao.setCellValue(unidade.getSituacao().getNome());
                    if (unidade.getEscala().getIntervalo() == 1) {
                        Cell cellPorte = row.createCell(cellnum++);
                        cellPorte.setCellValue("Pequeno");
                    }
                    if (unidade.getEscala().getIntervalo() == 2) {
                        Cell cellPorte = row.createCell(cellnum++);
                        cellPorte.setCellValue("Médio");
                    }
                    if (unidade.getEscala().getIntervalo() == 3) {
                        Cell cellPorte = row.createCell(cellnum++);
                        cellPorte.setCellValue("Grande");
                    }
                    Cell cellCategoria = row.createCell(cellnum++);
                    cellCategoria.setCellValue(unidade.getCategoria().getNome());
                    Cell cellMunicipio = row.createCell(cellnum++);
                    cellMunicipio.setCellValue(unidade.getMunicipio().getNome());
                    Cell cellEstado = row.createCell(cellnum++);
                    cellEstado.setCellValue(unidade.getMunicipio().getUf());
                    Cell cellProducaoBiogasAno = row.createCell(cellnum++);
                    cellProducaoBiogasAno.setCellValue(unidade.getProducaoBiogasAno());
                    Cell cellEnergiaTermica = row.createCell(cellnum++);
                    if (unidade.getBiogasParaEnergiaTermica()) {
                        cellEnergiaTermica.setCellValue("Sim");
                    } else {
                        cellEnergiaTermica.setCellValue("Não");
                    }
                    Cell cellEnergiaEletrica = row.createCell(cellnum++);
                    if (unidade.getBiogasParaEnergiaEletrica()) {
                        cellEnergiaEletrica.setCellValue("Sim");
                    } else {
                        cellEnergiaEletrica.setCellValue("Não");
                    }
                    Cell cellEnergiaMecanica = row.createCell(cellnum++);
                    if (unidade.getBiogasParaEnergiaMecanica()) {
                        cellEnergiaMecanica.setCellValue("Sim");
                    } else {
                        cellEnergiaMecanica.setCellValue("Não");
                    }
                    Cell cellBiometano = row.createCell(cellnum++);
                    if (unidade.getBiogasParaBiometano()) {
                        cellBiometano.setCellValue("Sim");
                    } else {
                        cellBiometano.setCellValue("Não");
                    }
                    Cell cellLatitude = row.createCell(cellnum++);
                    cellLatitude.setCellValue(unidade.getLatitude());
                    Cell cellLongitude = row.createCell(cellnum++);
                    cellLongitude.setCellValue(unidade.getLongitude());
                }

            }

            FileOutputStream out = new FileOutputStream(new File(System.getProperty("user.home") + "/biogasmap-" + usuarioAtual().get().getNome() + "-" + data + ".xls"));

            workbook.write(out);
            out.close();

            formatter.format("Usuário: '%1s' fez o download das Unidades em formato Excel.", usuarioAtual().get().getEmail());
            logController.inserir(sb.toString());

            Logger.info("Arquivo Excel criado com sucesso!");

            return ok(new File(System.getProperty("user.home") + "/biogasmap-" + usuarioAtual().get().getNome() + "-" + data + ".xls"));

        } catch (IOException e) {
            Logger.error(e.toString());
            return badRequest(e.getMessage());
        }


    }

    /**
     * reprove unidade from id
     *
     * @param id identificador
     * @return a message with a form
     */
    @Security.Authenticated(SecuredAdmin.class)
    public Result reprovar(Long id) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        //Necessario para verificar se o usuario e gerente
        if (usuarioAtual().isPresent()) {
            Usuario usuario = usuarioAtual().get();
            if (usuario.isGerente()) {
                return forbidden(views.html.mensagens.erro.naoAutorizado.render());
            }
        }

        try {

            Unidade unidade = Ebean.find(Unidade.class, id);

            if (unidade == null) {
                return notFound(views.html.mensagens.erro.naoEncontrado.render("Unidade não encontrada"));
            }

            unidade.setStatus(models.Status.REPROVADO);
            unidade.update();

            if (usuarioAtual().isPresent()) {
                formatter.format("Usuário Administrador: '%1s' reprovou a Unidade com código: '%2s'.", usuarioAtual().get().getEmail(), unidade.getCodigo());
                logController.inserir(sb.toString());
            }

            flash("warning", "Reprovado unidade com código - " + unidade.getCodigo());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
        } catch (Exception e) {
            flash("danger", "Nao foi possivel alterar unidade. Erro: " + e.getLocalizedMessage());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
        }

    }

    /**
     * aprove unidade from id
     *
     * @param id identificador
     * @return a message with a form
     */
    @Security.Authenticated(SecuredAdmin.class)
    public Result aprovar(Long id) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        //Necessario para verificar se o usuario e gerente
        if (usuarioAtual().isPresent()) {
            Usuario usuario = usuarioAtual().get();
            if (usuario.isGerente()) {
                return forbidden(views.html.mensagens.erro.naoAutorizado.render());
            }
        }

        try {

            Unidade unidade = Ebean.find(Unidade.class, id);

            if (unidade == null) {
                return notFound(views.html.mensagens.erro.naoEncontrado.render("Unidade não encontrada"));
            }

            unidade.setStatus(models.Status.APROVADO);
            unidade.update();

            if (usuarioAtual().isPresent()) {
                formatter.format("Usuário Administrador: '%1s' reprovou a Unidade com código: '%2s'.", usuarioAtual().get().getEmail(), unidade.getCodigo());
                logController.inserir(sb.toString());
            }

            flash("warning", "Aprovado unidade com código - " + unidade.getCodigo());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
        } catch (Exception e) {
            flash("danger", "Nao foi possivel alterar unidade. Erro: " + e.getLocalizedMessage());
            return redirect(routes.UnidadeController.telaLista(0, "codigo", "asc", ""));
        }

    }
}
