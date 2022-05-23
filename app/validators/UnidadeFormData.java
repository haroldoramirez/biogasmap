package validators;

import models.Categoria;
import models.Estado;
import models.Municipio;
import models.Situacao;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class UnidadeFormData {

    public Integer codigo = 0;
    public String anoDeInicioOperacao = "";
    public Boolean biogasParaEnergiaTermica = false;
    public Boolean biogasParaEnergiaEletrica = false;
    public Boolean biogasParaEnergiaMecanica = false;
    public Boolean biogasParaBiometano = false;
    public Boolean valorEstimado = false;
    public Double latitude = 0.0;
    public Double longitude = 0.0;
    public Float producaoSubstratoDia = (float) 0.0;
    public Float producaoSubstratoAno = (float) 0.0;
    public Float producaoBiogasDia = (float) 0.0;
    public Float producaoBiogasAno = (float) 0.0;
    public String situacao = "";
    public String categoria = "";
    public String municipio = "";
    public String estado = "";

    /** Necessario para instanciar o form */
    public UnidadeFormData() {}

    public UnidadeFormData(Integer codigo,
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
                           Categoria categoria,
                           Municipio municipio,
                           Estado estado) {
        this.codigo = codigo;
        this.anoDeInicioOperacao = anoDeInicioOperacao;
        this.biogasParaEnergiaEletrica = biogasParaEnergiaEletrica;
        this.biogasParaEnergiaTermica = biogasParaEnergiaTermica;
        this.biogasParaEnergiaMecanica = biogasParaEnergiaMecanica;
        this.biogasParaBiometano = biogasParaBiometano;
        this.valorEstimado = valorEstimado;
        this.latitude = latitude;
        this.longitude = longitude;
        this.producaoSubstratoDia = producaoSubstratoDia;
        this.producaoSubstratoAno = producaoSubstratoAno;
        this.producaoBiogasDia = producaoBiogasDia;
        this.producaoBiogasAno = producaoBiogasAno;
        this.situacao = situacao.getNome();
        this.categoria = categoria.getNome();
        this.municipio = municipio.getNome();
        this.estado = estado.getNome();

    }

    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (codigo == null || codigo == 0) {
            errors.add(new ValidationError("codigo", "O Código nao pode estar vazio ou conter zero."));
        }

        if (categoria == null || categoria.length() == 0) {
            errors.add(new ValidationError("categoria", "Selecione a Categoria."));
        }

        if (situacao == null || situacao.length() == 0) {
            errors.add(new ValidationError("situacao", "Selecione a Situação."));
        }

        if (estado == null || estado.length() == 0) {
            errors.add(new ValidationError("estado", "Selecione o Estado."));
        }

        if (anoDeInicioOperacao == null || anoDeInicioOperacao.length() == 0) {
            errors.add(new ValidationError("anoDeInicioOperacao", "Preencha a ano"));
        } else if (anoDeInicioOperacao.length() > 5) {
            errors.add(new ValidationError("anoDeInicioOperacao", "Ano com no máximo 4 caractéres"));
        }

        if (producaoBiogasAno == null || producaoBiogasAno == 0) {
            errors.add(new ValidationError("producaoBiogasAno", "O campo vazio ou contém zero, este campo vai definir o porte da Unidade."));
        }

        if (producaoBiogasDia == null || producaoBiogasDia == 0) {
            errors.add(new ValidationError("producaoBiogasDia", "O campo vazio ou contém zero"));
        }

        if (producaoSubstratoDia == null) {
            errors.add(new ValidationError("producaoSubstratoDia", "O campo vazio"));
        }

        if (producaoSubstratoAno == null) {
            errors.add(new ValidationError("producaoSubstratoAno", "O campo vazio."));
        }

        if (latitude == null || latitude == 0) {
            errors.add(new ValidationError("latitude", "O campo vazio ou contém zero"));
        }

        if (longitude == null || longitude == 0) {
            errors.add(new ValidationError("longitude", "O campo vazio ou contém zero"));
        }

        return errors.isEmpty() ? null : errors;
    }
}
