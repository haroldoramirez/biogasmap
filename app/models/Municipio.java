package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Municipio extends Model {

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/

    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    private String uf;

    //Muitos municipios tem um estado
    @ManyToOne
    private Estado estado;

    /*-------------------------------------------------------------------
     *				 		   GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    /*-------------------------------------------------------------------
     *				 		   UTILS
     *-------------------------------------------------------------------*/

    public static Finder<Long, Municipio> find = new Finder<>(Municipio.class);

    /**
     * Return the instance in the database with name 'nome' or null if not found.
     * @param nome The name.
     * @return The Categoria instance, or null if not found.
     */
    public static Municipio findMunicipio(String nome) {
        for (Municipio municipio : Ebean.find(Municipio.class).findList()) {
            if (nome.equals(municipio.getNome())) {
                return municipio;
            }
        }
        return null;
    }

}
