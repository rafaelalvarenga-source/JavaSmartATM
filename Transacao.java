package ContaBancaria;

public class Transacao {
    
    private String tipo;
    private Float valor;
    private LocalDateTime dataHora;

    public Transacao(String tipo, Float valor, String dataHora){
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public String getTipo(){
        return tipo;
    }

    public Float getValor(){
        return valor;
    }

    public String getDataHora(){
        return dataHora;
    }
}
