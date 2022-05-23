package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Escala extends Model implements Comparable<Escala> {

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/

    @Id
    @GeneratedValue
    private Long id;
    private Long valor; // valor de referencia para o MENOR QUE
    private Long intervalo; // 1, 2 ou 3 (Pequeno, Médio, Grande)
    private String legenda;
    /* < 500.000 Nm3/ano - Porte 1
     * 500.001 a 1.000.000 Nm3/ano - Porte 2
     * 1.000.001 a 3.500.000 Nm3/ano - Porte 3
     * 3.500.001 a 5.000.000 Nm3/ano - Porte 4
     * 5.000.001 a 30.000.000 Nm3/ano - Porte 5
     * 30.000.001 a 125.000.000 Nm3/ano - Porte 6
     * > 125.000.001 Nm3/ano - Porte 7
     */
    private Long porte; /* Porte 1 , Porte 2, Porte 3 */

    /*-------------------------------------------------------------------
     *						GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Long intervalo) {
        this.intervalo = intervalo;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Long getPorte() {
        return porte;
    }

    public void setPorte(Long porte) {
        this.porte = porte;
    }

    /*-------------------------------------------------------------------
     *						UTILS
     *-------------------------------------------------------------------*/

    @Override
    public int compareTo(Escala outraEscala) {
        return this.id.compareTo(outraEscala.id);
    }
}
