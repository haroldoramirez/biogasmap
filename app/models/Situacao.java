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
public class Situacao extends Model {

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

    public Situacao() {}

    public Situacao(Long id, String nome) {
        this.setId(id);
        this.setNome(nome);

    }

    /*-------------------------------------------------------------------
     *				 		   UTILS
     *-------------------------------------------------------------------*/

    public static Finder<Long, Situacao> find = new Finder<>(Situacao.class);

    /**
     * Return the Situacao instance in the database with name 'nome' or null if not found.
     * @param nome The name.
     * @return The Situacao instance, or null if not found.
     */
    public static Situacao findSituacao(String nome) {

        for (Situacao situacao : Ebean.find(Situacao.class).findList()) {
            if (nome.equals(situacao.getNome())) {
                return situacao;
            }
        }

        return null;
    }

    public static Map<String,String> options() {

        LinkedHashMap<String,String> options = new LinkedHashMap<>();

        for (Situacao s : Situacao.find.orderBy("nome").findList()) {
            options.put(s.id.toString(), s.nome);
        }

        return options;
    }
}
