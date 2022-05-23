package models;

import com.avaje.ebean.Model;
import java.io.Serializable;

public class Parametros extends Model implements Serializable {

    //Classe para encapsular os parametros do front-end e realizar os filtros

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/

    private Boolean validadorSituacao;
    private Boolean validadorAplicacao;
    private Boolean validadorCategoria;
    private Boolean validadorAno;
    private Boolean validadorEstado;
    private Boolean validadorMunicipio;
    private Boolean validadorEscala;
    private Long situacaoOperando;
    private Long situacaoConstruindo;
    private Long situacaoReformando;
    private Boolean aplicacaoTermica;
    private Boolean aplicacaoEletrica;
    private Boolean aplicacaoMecanica;
    private Boolean aplicacaoBiometano;
    private Long categoriaAgropecuaria;
    private Long categoriaIndustria;
    private Long categoriaEsgoto;
    private Long estado;
    private Long municipio;
    private String ano2003;
    private String ano2004;
    private String ano2005;
    private String ano2006;
    private String ano2007;
    private String ano2008;
    private String ano2009;
    private String ano2010;
    private String ano2011;
    private String ano2012;
    private String ano2013;
    private String ano2014;
    private String ano2015;
    private String ano2016;
    private String ano2017;
    private String ano2018;
    private String ano2019;
    private String ano2020;
    private Long escalaInicial;
    private Long escalaFinal;

    /*-------------------------------------------------------------------
     *						CONSTRUCTORS
     *-------------------------------------------------------------------*/

    public Parametros() {}

    /*-------------------------------------------------------------------
     *						GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    public Boolean getValidadorSituacao() {
        return validadorSituacao;
    }

    public void setValidadorSituacao(Boolean validadorSituacao) {
        this.validadorSituacao = validadorSituacao;
    }

    public Boolean getValidadorAplicacao() {
        return validadorAplicacao;
    }

    public void setValidadorAplicacao(Boolean validadorAplicacao) {
        this.validadorAplicacao = validadorAplicacao;
    }

    public Boolean getValidadorCategoria() {
        return validadorCategoria;
    }

    public void setValidadorCategoria(Boolean validadorCategoria) {
        this.validadorCategoria = validadorCategoria;
    }

    public Boolean getValidadorAno() {
        return validadorAno;
    }

    public void setValidadorAno(Boolean validadorAno) {
        this.validadorAno = validadorAno;
    }

    public Boolean getValidadorEstado() {
        return validadorEstado;
    }

    public void setValidadorEstado(Boolean validadorEstado) {
        this.validadorEstado = validadorEstado;
    }

    public Boolean getValidadorMunicipio() {
        return validadorMunicipio;
    }

    public void setValidadorMunicipio(Boolean validadorMunicipio) {
        this.validadorMunicipio = validadorMunicipio;
    }

    public Boolean getValidadorEscala() {
        return validadorEscala;
    }

    public void setValidadorEscala(Boolean validadorEscala) {
        this.validadorEscala = validadorEscala;
    }

    public Long getSituacaoOperando() {
        return situacaoOperando;
    }

    public void setSituacaoOperando(Long situacaoOperando) {
        this.situacaoOperando = situacaoOperando;
    }

    public Long getSituacaoConstruindo() {
        return situacaoConstruindo;
    }

    public void setSituacaoConstruindo(Long situacaoConstruindo) {
        this.situacaoConstruindo = situacaoConstruindo;
    }

    public Long getSituacaoReformando() {
        return situacaoReformando;
    }

    public void setSituacaoReformando(Long situacaoReformando) {
        this.situacaoReformando = situacaoReformando;
    }

    public Boolean getAplicacaoTermica() {
        return aplicacaoTermica;
    }

    public void setAplicacaoTermica(Boolean aplicacaoTermica) {
        this.aplicacaoTermica = aplicacaoTermica;
    }

    public Boolean getAplicacaoEletrica() {
        return aplicacaoEletrica;
    }

    public void setAplicacaoEletrica(Boolean aplicacaoEletrica) {
        this.aplicacaoEletrica = aplicacaoEletrica;
    }

    public Boolean getAplicacaoMecanica() {
        return aplicacaoMecanica;
    }

    public void setAplicacaoMecanica(Boolean aplicacaoMecanica) {
        this.aplicacaoMecanica = aplicacaoMecanica;
    }

    public Boolean getAplicacaoBiometano() {
        return aplicacaoBiometano;
    }

    public void setAplicacaoBiometano(Boolean aplicacaoBiometano) {
        this.aplicacaoBiometano = aplicacaoBiometano;
    }

    public Long getCategoriaAgropecuaria() {
        return categoriaAgropecuaria;
    }

    public void setCategoriaAgropecuaria(Long categoriaAgropecuaria) {
        this.categoriaAgropecuaria = categoriaAgropecuaria;
    }

    public Long getCategoriaIndustria() {
        return categoriaIndustria;
    }

    public void setCategoriaIndustria(Long categoriaIndustria) {
        this.categoriaIndustria = categoriaIndustria;
    }

    public Long getCategoriaEsgoto() {
        return categoriaEsgoto;
    }

    public void setCategoriaEsgoto(Long categoriaEsgoto) {
        this.categoriaEsgoto = categoriaEsgoto;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Long municipio) {
        this.municipio = municipio;
    }

    public String getAno2003() {
        return ano2003;
    }

    public void setAno2003(String ano2003) {
        this.ano2003 = ano2003;
    }

    public String getAno2004() {
        return ano2004;
    }

    public void setAno2004(String ano2004) {
        this.ano2004 = ano2004;
    }

    public String getAno2005() {
        return ano2005;
    }

    public void setAno2005(String ano2005) {
        this.ano2005 = ano2005;
    }

    public String getAno2006() {
        return ano2006;
    }

    public void setAno2006(String ano2006) {
        this.ano2006 = ano2006;
    }

    public String getAno2007() {
        return ano2007;
    }

    public void setAno2007(String ano2007) {
        this.ano2007 = ano2007;
    }

    public String getAno2008() {
        return ano2008;
    }

    public void setAno2008(String ano2008) {
        this.ano2008 = ano2008;
    }

    public String getAno2009() {
        return ano2009;
    }

    public void setAno2009(String ano2009) {
        this.ano2009 = ano2009;
    }

    public String getAno2010() {
        return ano2010;
    }

    public void setAno2010(String ano2010) {
        this.ano2010 = ano2010;
    }

    public String getAno2011() {
        return ano2011;
    }

    public void setAno2011(String ano2011) {
        this.ano2011 = ano2011;
    }

    public String getAno2012() {
        return ano2012;
    }

    public void setAno2012(String ano2012) {
        this.ano2012 = ano2012;
    }

    public String getAno2013() {
        return ano2013;
    }

    public void setAno2013(String ano2013) {
        this.ano2013 = ano2013;
    }

    public String getAno2014() {
        return ano2014;
    }

    public void setAno2014(String ano2014) {
        this.ano2014 = ano2014;
    }

    public String getAno2015() {
        return ano2015;
    }

    public void setAno2015(String ano2015) {
        this.ano2015 = ano2015;
    }

    public String getAno2016() {
        return ano2016;
    }

    public void setAno2016(String ano2016) {
        this.ano2016 = ano2016;
    }

    public String getAno2017() {
        return ano2017;
    }

    public void setAno2017(String ano2017) {
        this.ano2017 = ano2017;
    }

    public String getAno2018() {
        return ano2018;
    }

    public void setAno2018(String ano2018) {
        this.ano2018 = ano2018;
    }

    public String getAno2019() {
        return ano2019;
    }

    public void setAno2019(String ano2019) {
        this.ano2019 = ano2019;
    }

    public String getAno2020() {
        return ano2020;
    }

    public void setAno2020(String ano2020) {
        this.ano2020 = ano2020;
    }

    public Long getEscalaInicial() {
        return escalaInicial;
    }

    public void setEscalaInicial(Long escalaInicial) {
        this.escalaInicial = escalaInicial;
    }

    public Long getEscalaFinal() {
        return escalaFinal;
    }

    public void setEscalaFinal(Long escalaFinal) {
        this.escalaFinal = escalaFinal;
    }
}
