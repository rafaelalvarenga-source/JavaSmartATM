package ContaBancaria;

import java.util.ArrayList;
import java.time.LocalDateTime; 

public class ContaBancaria {
    
    private Cliente cliente;
    private float chequeEspecial;
    private float saldo;
    private float valorUsadoChequeEspecial;
    private float dividaChequeEspecial;
    private String numeroConta;
    private ArrayList<Transacao> transacoes;
    private static int proximoNumeroConta = 1;

    public static int getProximoNumeroConta(){
        return proximoNumeroConta;
    }
   
    public ContaBancaria(Cliente cliente, float depositoInicial){
        this.cliente = cliente;
        this.numeroConta = String.format("%04d", proximoNumeroConta);
        proximoNumeroConta++;
        this.saldo = depositoInicial;
        this.valorUsadoChequeEspecial = 0;
        this.dividaChequeEspecial = 0;
        this.transacoes = new ArrayList<>();

        if(depositoInicial <= 500){
            this.chequeEspecial = 50;
        } else {
            this.chequeEspecial = depositoInicial * 0.5f;   
        }
    }

    public void adicionarTransacao(Transacao transacao){
        transacoes.add(transacao);
    }
    
    public Cliente getCliente(){
        return cliente;
    }
  
    public float consultarSaldo(){
        return saldo;
    }

    public float consultarChequeEspecial(){
        return chequeEspecial;
    }
    
    public float consultarValorUsadoChequeEspecial(){
        return valorUsadoChequeEspecial;
    }

    public float consultarChequeEspecialDisponivel(){
        return chequeEspecial - valorUsadoChequeEspecial;
    }

    public String getNumeroConta(){
        return numeroConta;
    }

    public boolean sacar(float valor){
        
        boolean sucesso = debitar(valor);

        if(sucesso){

            transacoes.add(
                new Transacao(
                    "SAQUE",
                    valor,
                    LocalDateTime.now().toString()
                )
            );
        }
        return sucesso;

    }

    public boolean debitar(float valor){
        if(valor <=0){
            return false;
        } else if(valor <= saldo){
           saldo -= valor;
           return true;
        } else if(valor <= saldo + (chequeEspecial - valorUsadoChequeEspecial)){
            float usado = valor - saldo;
            valorUsadoChequeEspecial += usado;
            dividaChequeEspecial+= usado * 1.2f;
            saldo = 0;
            return true;
        } else {
            return false;
        }
    }

    public boolean depositar(float valor){

        if(valor <= 0){

        return false;

    } 
    else if(valorUsadoChequeEspecial >0){

            if(valor >= dividaChequeEspecial){

            float restante = valor - dividaChequeEspecial;

            dividaChequeEspecial = 0;
            valorUsadoChequeEspecial = 0;
            saldo += restante;

        }else {

            dividaChequeEspecial -= valor;
            valorUsadoChequeEspecial -= valor;

            if(valorUsadoChequeEspecial < 0){
                valorUsadoChequeEspecial = 0;
            }
            
        }
    } else {
        saldo += valor;
    }
        transacoes.add(
            new Transacao(
                "DEPOSITO",
                valor,
                LocalDateTime.now().toString()
            )
        );
        return true;
    }
    public boolean pagarBoleto(float valor){
        Boolean sucesso = debitar(valor);
        if (sucesso) {
            transacoes.add(
            new Transacao(
                "PAGAMENTO",
                valor,
                LocalDateTime.now().toString()
                )
            );    
        }
        
        return sucesso;
    }
    public boolean estaUsandoChequeEspecial(){
        return dividaChequeEspecial > 0;
    }
    
    public void consultarExtrato(){
         if (transacoes.isEmpty()) {
        System.out.println("Nenhuma transação encontrada.");
        return;
    }

    System.out.println("=== EXTRATO ===");

    for (Transacao transacao : transacoes) {

        System.out.println(
            transacao.getTipo()
            + " | R$ "
            + transacao.getValor()
            + " | "
            + transacao.getDataHora()
        );
    }
    }

    public ArrayList<Transacao> getTransacoes(){
        return transacoes;
    }

    public void consultarDadosConta() {

    System.out.println("Titular: "
        + cliente.getNome());

    System.out.println("CPF: "
        + cliente.getCpf());

    System.out.println("Conta: "
        + numeroConta);
        
        System.out.println("Saldo: R$ "
        + saldo);

        System.out.println("Cheque Especial: R$ "
        + chequeEspecial);

        System.out.println("Cheque Especial Utilizado: R$ "
        + valorUsadoChequeEspecial);
        
    }

    public boolean transferencia(float valor, ContaBancaria destino){

        boolean sucesso = debitar(valor);

        if (sucesso) {
            destino.depositar(valor);

            transacoes.add(
                new Transacao(
                    "TRANSFERENCIA",
                    valor,
                    LocalDateTime.now().toString()
                )
            );
        }
        return sucesso;
    }


}
