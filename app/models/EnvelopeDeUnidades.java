package models;

import java.util.List;

public class EnvelopeDeUnidades {

    private final List<Unidade> unidades;

    public EnvelopeDeUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }
}
