package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Categoria extends Model {

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 60)
    private String nome;

    /*-------------------------------------------------------------------
     *				 		   GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /*-------------------------------------------------------------------
     *				 		   CONSTRUCTORS
     *-------------------------------------------------------------------*/

    public Categoria() {}

    public Categoria(Long id, String nome) {
        this.setId(id);
        this.setNome(nome);

    }

    /*-------------------------------------------------------------------
     *				 		   UTILS
     *-------------------------------------------------------------------*/

    public static Finder<Long, Categoria> find = new Finder<>(Categoria.class);

    /**
     * Return the Categoria instance in the database with name 'nome' or null if not found.
     * @param nome The name.
     * @return The Categoria instance, or null if not found.
     */
    public static Categoria findCategoria(String nome) {
        for (Categoria categoria : Ebean.find(Categoria.class).findList()) {
            if (nome.equals(categoria.getNome())) {
                return categoria;
            }
        }
        return null;
    }

    public static Map<String,String> options() {

        LinkedHashMap<String,String> options = new LinkedHashMap<>();

        for (Categoria c : Categoria.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }

        return options;
    }
}
