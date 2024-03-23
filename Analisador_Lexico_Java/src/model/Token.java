
package model;

/**
 *
 * @author steli
 */
public class Token {
    private String nome_token;
    private String nome_lexema;
    private String linha_token;

    public Token(String nome_token, String nome_lexema, String linha_token) {
        this.nome_token = nome_token;
        this.nome_lexema = nome_lexema;
        this.linha_token = linha_token;
    }

    public String getNome_token() {
        return nome_token;
    }

    public void setNome_token(String nome_token) {
        this.nome_token = nome_token;
    }

    public String getNome_lexema() {
        return nome_lexema;
    }

    public void setNome_lexema(String nome_lexema) {
        this.nome_lexema = nome_lexema;
    }

    public String getLinha_token() {
        return linha_token;
    }

    public void setLinha_token(String linha_token) {
        this.linha_token = linha_token;
    }
    
}
