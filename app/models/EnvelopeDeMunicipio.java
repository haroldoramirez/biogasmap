package models;

public class EnvelopeDeMunicipio {

    private Long id;
    private String nome;

    /*-------------------------------------------------------------------
     *				 		   CONSTRUCTORS
     *-------------------------------------------------------------------*/

    public EnvelopeDeMunicipio() {}

    public EnvelopeDeMunicipio(Long id, String nome) {
        this.setId(id);
        this.setNome(nome);

    }

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

}
