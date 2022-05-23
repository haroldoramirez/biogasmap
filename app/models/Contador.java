package models;

import com.avaje.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contador extends Model {

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/

    @Id
    @GeneratedValue
    private Long id;

    //Contador de downloads de documentos do front-end
    private Integer csv;
    private Integer excel;
    private Integer pdf;
    private Integer biogasdata;
    private Integer notatecnica;

    /*-------------------------------------------------------------------
     *				 		   GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCsv() {
        return csv;
    }

    public void setCsv(Integer csv) {
        this.csv = csv;
    }

    public Integer getExcel() {
        return excel;
    }

    public void setExcel(Integer excel) {
        this.excel = excel;
    }

    public Integer getPdf() {
        return pdf;
    }

    public void setPdf(Integer pdf) {
        this.pdf = pdf;
    }

    public Integer getBiogasdata() {
        return biogasdata;
    }

    public void setBiogasdata(Integer biogasdata) {
        this.biogasdata = biogasdata;
    }

    public Integer getNotatecnica() {
        return notatecnica;
    }

    public void setNotatecnica(Integer notatecnica) {
        this.notatecnica = notatecnica;
    }
}
