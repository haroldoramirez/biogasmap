package models;

public enum Status {
    APROVADO(1), AVALIAR(2), REPROVADO(3);

    public int valorStatus;

    Status(int valor) {
        valorStatus = valor;
    }
}
