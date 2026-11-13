package ContaBancaria;

public class Cliente {
    
    private String cpf;
    private String nome;
    private String senha;
    private String tipo;
    private boolean ativo;

    public Cliente(
        String cpf,
        String nome,
        String senha,
        String tipo
    ){
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
        this.ativo = true;
    }
    
    public String getCpf(){
        return cpf;
    }
    
    public String getNome(){
        return nome;
    }
    
    public Boolean isAtivo(){
        return ativoF;
    }

    public String getSenha(){
        return senha;
    }

    public boolean validarSenha(){
        return this.senha.equals(senha)
    }

    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public boolean validarSenha(String senha){
        return this.senha.equals(senha);
    }