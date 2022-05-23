package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.Query;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.DynamicForm;
import validators.UnidadeFormData;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class Unidade extends Model {

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    //Codigo unico da unidade
    @Column(nullable = false, unique = true)
    private Integer codigo;

    @Column(nullable = false)
    private String anoDeInicioOperacao;

    //Aplicacao do biogas
    @Column(nullable = false)
    private Boolean biogasParaEnergiaTermica;

    @Column(nullable = false)
    private Boolean biogasParaEnergiaEletrica;

    @Column(nullable = false)
    private Boolean biogasParaEnergiaMecanica;

    @Column(nullable = false)
    private Boolean biogasParaBiometano;

    @Column(nullable = false)
    private Boolean valorEstimado;

    //Coordenadas
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    //Producao de substratos
    @Column(nullable = false)
    private Float producaoSubstratoDia;

    @Column(nullable = false)
    private Float producaoSubstratoAno;

    //valor utilizado como base para calcular o porte da Unidade/Escala
    @Column(nullable = false)
    private Float producaoBiogasDia;

    @Column(nullable = false)
    private Float producaoBiogasAno;

    @ManyToOne
    @Column(nullable = false)
    private Situacao situacao;

    @ManyToOne
    @Column(nullable = false)
    private Escala escala;

    @ManyToOne
    @Column(nullable = false)
    private Categoria categoria;

    @ManyToOne
    @Column(nullable = false)
    private Municipio municipio;

    //Apenas para auxiliar na tela de cadastro e edicao
    @Transient
    @JsonIgnore
    private Estado estado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar dataAlteracao;

    /*-------------------------------------------------------------------
     *						CONSTRUCTORS
     *-------------------------------------------------------------------*/

    /**
     * Precisa obedecer a sequencia no makeinstance para receber os dados corretamente do form para no banco
     *  id;
     *     codigo;
     *     anoDeInicioOperacao;
     *     biogasParaEnergiaTermica;
     *     biogasParaEnergiaEletrica;
     *     biogasParaEnergiaMecanica;
     *     biogasParaBiometano;
     *     valorEstimado;
     *     latitude;
     *     longitude;
     *     producaoSubstratoDia;
     *     producaoSubstratoAno;
     *     producaoBiogasDia;
     *     producaoBiogasAno;
     *     situacao;
     *     escala;
     *     categoria;
     *     Municipio municipio;
     *
     */

    public Unidade() {}

    public Unidade(Long id,
                   Integer codigo,
                   String anoDeInicioOperacao,
                   Boolean biogasParaEnergiaTermica,
                   Boolean biogasParaEnergiaEletrica,
                   Boolean biogasParaEnergiaMecanica,
                   Boolean biogasParaBiometano,
                   Boolean valorEstimado,
                   Double latitude,
                   Double longitude,
                   Float producaoSubstratoDia,
                   Float producaoSubstratoAno,
                   Float producaoBiogasDia,
                   Float producaoBiogasAno,
                   Situacao situacao,
                   Escala escala,
                   Categoria categoria,
                   Municipio municipio,
                   Estado estado) {

        this.setId(id);
        this.setCodigo(codigo);
        this.setAnoDeInicioOperacao(anoDeInicioOperacao);
        this.setBiogasParaEnergiaTermica(biogasParaEnergiaTermica);
        this.setBiogasParaEnergiaEletrica(biogasParaEnergiaEletrica);
        this.setBiogasParaEnergiaMecanica(biogasParaEnergiaMecanica);
        this.setBiogasParaBiometano(biogasParaBiometano);
        this.setValorEstimado(valorEstimado);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setProducaoSubstratoDia(producaoSubstratoDia);
        this.setProducaoSubstratoAno(producaoSubstratoAno);
        this.setProducaoBiogasDia(producaoBiogasDia);
        this.setProducaoBiogasAno(producaoBiogasAno);
        this.setSituacao(situacao);
        this.setEscala(escala);
        this.setCategoria(categoria);
        this.setMunicipio(municipio);
        this.setEstado(estado);

    }

    /**
     * Return a PublicacaoFormData instance constructed from a obj instance.
     * @param id The ID of a video instance.
     * @return The PublicacaoFormData instance, or throws a RuntimeException.
     */
    public static UnidadeFormData makeUnidadeFormData(Long id) {

        Unidade unidade = Ebean.find(Unidade.class, id);

        if (unidade == null) {
            throw new RuntimeException("Unidade não encontrada.");
        }

        return new UnidadeFormData(unidade.codigo,
                unidade.anoDeInicioOperacao,
                unidade.biogasParaEnergiaTermica,
                unidade.biogasParaEnergiaEletrica,
                unidade.biogasParaEnergiaMecanica,
                unidade.biogasParaBiometano,
                unidade.valorEstimado,
                unidade.latitude,
                unidade.longitude,
                unidade.producaoSubstratoDia,
                unidade.producaoSubstratoAno,
                unidade.producaoBiogasDia,
                unidade.producaoBiogasAno,
                unidade.situacao,
                unidade.categoria,
                unidade.municipio,
                //Pega o estado
                unidade.municipio.getEstado());
    }

    /**
     * @return a objeto atraves da um formData onde o parametro FormData que validou os campos inputs
     * Cria uma instancia estatica do obj passando por parametro o objeto formData com os dados preenchidos
     */
    public static Unidade makeInstance(UnidadeFormData formData) {
        Unidade unidade = new Unidade();
        unidade.setCodigo(formData.codigo);
        unidade.setAnoDeInicioOperacao(formData.anoDeInicioOperacao);
        unidade.setBiogasParaEnergiaTermica(formData.biogasParaEnergiaTermica);
        unidade.setBiogasParaEnergiaEletrica(formData.biogasParaEnergiaEletrica);
        unidade.setBiogasParaEnergiaMecanica(formData.biogasParaEnergiaMecanica);
        unidade.setBiogasParaBiometano(formData.biogasParaBiometano);
        unidade.setValorEstimado(formData.valorEstimado);
        unidade.setLatitude(formData.latitude);
        unidade.setLongitude(formData.longitude);
        unidade.setProducaoSubstratoDia(formData.producaoSubstratoDia);
        unidade.setProducaoSubstratoAno(formData.producaoSubstratoAno);
        unidade.setProducaoBiogasDia(formData.producaoBiogasDia);
        unidade.setProducaoBiogasAno(formData.producaoBiogasAno);
        unidade.setSituacao(Situacao.findSituacao(formData.situacao));
        //Nao temos apenas a escala que sera definida na hora de salvar a unidade no banco
        unidade.setCategoria(Categoria.findCategoria(formData.categoria));
        unidade.setMunicipio(Municipio.findMunicipio(formData.municipio));
        return unidade;
    }

    /*-------------------------------------------------------------------
     *						GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getAnoDeInicioOperacao() {
        return anoDeInicioOperacao;
    }

    public void setAnoDeInicioOperacao(String anoDeInicioOperacao) {
        this.anoDeInicioOperacao = anoDeInicioOperacao;
    }

    public Boolean getBiogasParaEnergiaTermica() {
        return biogasParaEnergiaTermica;
    }

    public void setBiogasParaEnergiaTermica(Boolean biogasParaEnergiaTermica) {
        this.biogasParaEnergiaTermica = biogasParaEnergiaTermica;
    }

    public Boolean getBiogasParaEnergiaEletrica() {
        return biogasParaEnergiaEletrica;
    }

    public void setBiogasParaEnergiaEletrica(Boolean biogasParaEnergiaEletrica) {
        this.biogasParaEnergiaEletrica = biogasParaEnergiaEletrica;
    }

    public Boolean getBiogasParaEnergiaMecanica() {
        return biogasParaEnergiaMecanica;
    }

    public void setBiogasParaEnergiaMecanica(Boolean biogasParaEnergiaMecanica) {
        this.biogasParaEnergiaMecanica = biogasParaEnergiaMecanica;
    }

    public Boolean getBiogasParaBiometano() {
        return biogasParaBiometano;
    }

    public void setBiogasParaBiometano(Boolean biogasParaBiometano) {
        this.biogasParaBiometano = biogasParaBiometano;
    }

    public Boolean getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(Boolean valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Float getProducaoSubstratoDia() {
        return producaoSubstratoDia;
    }

    public void setProducaoSubstratoDia(Float producaoSubstratoDia) {
        this.producaoSubstratoDia = producaoSubstratoDia;
    }

    public Float getProducaoSubstratoAno() {
        return producaoSubstratoAno;
    }

    public void setProducaoSubstratoAno(Float producaoSubstratoAno) {
        this.producaoSubstratoAno = producaoSubstratoAno;
    }

    public Float getProducaoBiogasDia() {
        return producaoBiogasDia;
    }

    public void setProducaoBiogasDia(Float producaoBiogasDia) {
        this.producaoBiogasDia = producaoBiogasDia;
    }

    public Float getProducaoBiogasAno() {
        return producaoBiogasAno;
    }

    public void setProducaoBiogasAno(Float producaoBiogasAno) {
        this.producaoBiogasAno = producaoBiogasAno;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Calendar getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /*-------------------------------------------------------------------
     *				 		   FINDERS
     *-------------------------------------------------------------------*/

    public static Finder<Long, Unidade> find = new Finder<>(Unidade.class);

    /**
     * Return a page of obj
     *  @param page Page to display
     * @param pageSize Number of obj per page
     * @param sortBy obj number property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     * @param listForm value to new filter type if have
     */
    public static PagedList<Unidade> page(int page, int pageSize, String sortBy, String order, String filter, DynamicForm listForm) {

        if (filter.isEmpty()) {
            return
                    find.where()
                            .ilike("ano_de_inicio_operacao", "%" + filter + "%")
                            .orderBy(sortBy + " " + order)
                            .findPagedList(page, pageSize);
        } else {
            //ao consultar por codigo
            return
                    find.where()
                            .eq("codigo", Integer.valueOf(filter))
                            .findPagedList(page, pageSize);
        }

    }

    /*-------------------------------------------------------------------
     *				 		   UTILS
     *-------------------------------------------------------------------*/

    /**
     * Create a map of Categoria name -> boolean where the boolean is true if the Categoria corresponds to the Site.
     * @param cadastro A Categoria with a Unidade.
     * @return A map of Categoria to boolean indicating which one is the Unidade Categoria.
     */
    public static Map<String, Boolean> makeCategoriaMap(UnidadeFormData cadastro) {

        Map<String, Boolean> categoriaMap = new TreeMap<>();

        for (Categoria categoria : Ebean.find(Categoria.class).findList()) {
            categoriaMap.put(categoria.getNome(), cadastro != null && (cadastro.categoria != null && cadastro.categoria.equals(categoria.getNome())));
        }

        return categoriaMap;
    }

    /**
     * Create a map of Situaco name -> boolean where the boolean is true if the Situacao corresponds to the Site.
     * @param cadastro A Situacao with a Unidade.
     * @return A map of Situacao to boolean indicating which one is the Unidade Situacao.
     */
    public static Map<String, Boolean> makeSituacaoMap(UnidadeFormData cadastro) {

        Map<String, Boolean> situacaoMap = new TreeMap<>();

        for (Situacao situacao : Ebean.find(Situacao.class).findList()) {
            situacaoMap.put(situacao.getNome(), cadastro != null && (cadastro.situacao != null && cadastro.situacao.equals(situacao.getNome())));
        }

        return situacaoMap;
    }

    /**
     * Create a map of Situaco name -> boolean where the boolean is true if the Situacao corresponds to the Site.
     * @param cadastro A Situacao with a Unidade.
     * @return A map of Situacao to boolean indicating which one is the Unidade Situacao.
     */
    public static Map<String, Boolean> makeEstadoMap(UnidadeFormData cadastro) {

        Map<String, Boolean> estadoMap = new TreeMap<>();

        for (Estado estado : Ebean.find(Estado.class).findList()) {
            estadoMap.put(estado.getNome(), cadastro != null && (cadastro.estado != null && cadastro.estado.equals(estado.getNome())));
        }

        return estadoMap;
    }

    public static Map<String, Boolean> makeMunicipioPorEstado(UnidadeFormData cadastro, String estadoNome) {

        Query<Municipio> municipioQuery = Ebean.createQuery(Municipio.class, "find municipio " +
                "where estado.nome = :estado");

        municipioQuery.setParameter("estado", estadoNome);

        Map<String, Boolean> municipioEstadoMap = new TreeMap<>();

        for (Municipio municipio : municipioQuery.findList()) {
            municipioEstadoMap.put(municipio.getNome(), cadastro != null && (cadastro.municipio != null && cadastro.municipio.equals(municipio.getNome())));
        }

        return municipioEstadoMap;
    }

    /**
     * Return a list of last nota tecnica created
     *
     */
    public static List<Unidade> last() {
        return find.where().orderBy("dataCadastro desc").setMaxRows(6).findList();
    }

    public static Integer quantidade() {
        return find.where().findRowCount();
    }

    @JsonIgnore
    public boolean isAprovado() {
        return status == Status.APROVADO;
    }

    @JsonIgnore
    public boolean isReprovado() {
        return status == Status.REPROVADO;
    }

    @JsonIgnore
    public boolean isAvaliar() {
        return status == Status.AVALIAR;
    }
}
