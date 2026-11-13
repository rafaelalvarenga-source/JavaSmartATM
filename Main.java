package ContaBancaria;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
  public static void main(String[]args){

    Scanner sc = new Scanner(System.in);
    int opcao;

    System.out.println("Digite seu nome: ");
    String nome = sc.nextLine();

    System.out.println("Informe seu depósito: ");
    float depositoInicial = sc.nextFloat();

    Cliente cliente= new Cliente(
      "12345678900",
      nome, 
      "1234",
    "CLIENTE"
    );

    Cliente gerente = new Cliente(
      "00000000000",
      "Gerente",
      "admin123",
      "GERENTE"
    );

      ContaBancaria conta = new ContaBancaria(
        cliente, 
        depositoInicial
      );

      ContaBancaria contaGerente = new ContaBancaria(gerente, 10000);


      ArrayList<ContaBancaria> contas = new ArrayList<>();

      contas.add(conta);
      contas.add(contaGerente);


      ContaBancaria contaAtual = conta;

      Cliente cliente2 = new Cliente(
        "99999999999",
        "Maria",
        "5678",
        "CLIENTE"
      );

      ContaBancaria conta2 = new ContaBancaria(cliente2,
        depositoInicial);

        contas.add(conta2);

    do{
      System.out.println("==MENU==");
      System.out.println("1 - Consultar saldo");
      System.out.println("2 - Consultar cheque especial");
      System.out.println("3 - Depositar dinheiro");
      System.out.println("4 - Sacar dinheiro");
      System.out.println("5 - Pagar boleto");
      System.out.println("6 - Verificar uso do cheque especial");
      System.out.println("7 - Consultar extrato");
      System.out.println("8 - Consultar dados da conta");
      System.out.println("9 - Transferir");
      if (contaAtual.getCliente().getTipo().equals("GERENTE")) {
            
      System.out.println("10 - Cadastrar Cliente");
      System.out.println("11 - Lista Contas");
      System.out.println("12 - Trocar Conta Atual");
      }

      System.out.println("0 - Sair");

      opcao = sc.nextInt();

      switch(opcao){

      case 1:
        System.out.println("Saldo: R$" + contaAtual.consultarSaldo());
        break;

      case 2:
        System.out.println("Cheque Especial: R$" + contaAtual.consultarChequeEspecial());
        System.out.println("Utilizado: R$" + contaAtual.consultarValorUsadoChequeEspecial());
        System.out.println("Disponível: R$" + contaAtual.consultarChequeEspecialDisponivel());
        break;

      case 3:
        System.out.println("Informe o valor do depósito: ");
        float valorDeposito = sc.nextFloat();

        if (contaAtual.depositar(valorDeposito)) {
          System.out.println("Depósito realizado com sucesso.");          
        } else {
          System.out.println("Valor inválido para depósito.");
        } break;

      case 4:
        System.out.println("Informe o valor do saque: ");
        float valorSaque = sc.nextFloat();

        if (contaAtual.sacar(valorSaque)) {
          System.out.println("Saque realizado com sucesso");
        } else {
          System.out.println("Saldo insuficiente ou valor inválido.");
        } 
        
        break;

      case 5:
        System.out.println("Informe o valor do pagamento");
        float valorBoleto = sc.nextFloat();

        if(contaAtual.pagarBoleto(valorBoleto)){
          System.out.println("Pagamento realizado com sucesso.");
        } else {
          System.out.println("Saldo insuficiente ou valor inválido.");
        }
        
        break;

      case 6:
        if (contaAtual.estaUsandoChequeEspecial()) {
          System.out.println("A conta está utilizando cheque especial.");         
        } else {
          System.out.println("A conta não está utilizando cheque especial.");
        }

        break;

      case 7:
        contaAtual.consultarExtrato();
        break;

      case 8:
        contaAtual.consultarDadosConta();
        break;

      case 9:
        System.out.println("Valor da tranferencia: ");
        float ValorTransferencia = sc.nextFloat();

        if(contaAtual.transferencia(ValorTransferencia, conta2)){
          System.out.println(
            "Transferencia realizada com sucesso.");
        } else {
          System.out.println("Saldo insuficiente.");
        }
        break;

      case 10:
        sc.nextLine();

        if (!contaAtual.getCliente().getTipo().equals("GERENTE")) {
            System.out.println("Acesso negado.");
            break;
        }

        System.out.print("Nome: ");
        String nomeNovo = sc.nextLine();

        System.out.println("CPF: ");
        String cpfNovo = sc.nextLine();

        boolean cpfExiste = false;
          for(ContaBancaria c : contas){
            cpfExiste = true;
            break;
          }
          if (cpfExiste) {
            System.out.println("CPF já cadastrado.");
            break;
          }

        System.out.println("Senha: ");
        String senhaNova = sc.nextLine();

        System.out.println("Deposito Inicial: ");
        float depositoInicialNovo = sc.nextFloat();

        Cliente novoCliente = new Cliente(
          cpfNovo,
          nomeNovo,
          senhaNova
        );

        ContaBancaria novaConta = new ContaBancaria(
          novoCliente,
          depositoInicialNovo
        );

        contas.add(novaConta);

        System.out.println("Cliente cadastrado com sucesso!");

        break;

      case 11:
        if (!contaAtual.getCliente().getTipo().equals("GERENTE")) {
            System.out.println("Acesso negado.");
            break;
        }
        System.out.println("=== CONTAS CADASTRADAS ===");
          for(ContaBancaria c : contas){
            System.out.println(
              "Contas: " + c.getNumeroConta()
              + " | Titular: "
              + c.getCliente().getNome()
            );
          }
          break;

      case 12:
            if (!contaAtual.getCliente().getTipo().equals("GERENTE")) {
            System.out.println("Acesso negado.");
            break;
        }

        System.out.println("=== CONTAS DISPONÍVEIS ===");
        
        for(ContaBancaria c : contas){
          
          System.out.println(
            c.getNumeroConta()
            + " - "
            + c.getCliente().getNome()
          );
        
        }

        System.out.println("Informe o número da conta: ");
        String numeroConta = sc.nextLine();

        boolean encontrada = false;

        for(ContaBancaria c : contas){
            if (c.getNumeroConta().equals(numeroConta)) {
              
              contaAtual = c;
              encontrada = true;

              System.out.println(
                "Conta selecionada: "
                + c.getCliente().getNome()
              );

              break;
            }
        }

        if (!encontrada) {
          
          System.out.println("Conta não encontrada.");

        }

        break;

      case 0:
        System.out.println("Programa encerrado.");
        break;

        default:
          System.out.println("Opção inválida.");
      }
      
    }while (opcao != 0);




  }
}
