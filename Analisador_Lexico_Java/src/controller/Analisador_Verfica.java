/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author stelio
 * @author Sumeid
 */
import java.util.Arrays;
import java.util.List;

public class Analisador_Verfica {
    private final String RESERVADA = "Palavra Reservada";
    private final String DIGITO = "Número";
    private final String OPERADOR = "Operador Aritmetico";
    private final String DELIMITADOR = "Delimitador";
    private final String ERRO = "Erro";
    private final String IDENTIFICADOR = "Identificador";
    private final String OPERADOR_RELACIONAL = "Operador relacional";
    private final String SINAL_ATRIBUICAO = "Operador de atribuicao";

    private static final String DIGITOS = "[0-9]+";
    private static final String IDENTIFICADOR_REGEX = "[a-zA-Z_]+([0-9_]*[a-zA-Z]*)*";

    private static final List<String> PALAVRAS_RESERVADAS = Arrays.asList(
        "div", "or", "and", "not", "if", "then", "else", "of", "Record", "while",
        "do", "begin", "end", "writeln", "readln", "read", "const", "write",
        "var", "array", "function", "procedure", "program", "true", "false",
        "char", "integer", "boolean", "Uses"
    );

    private static final List<String> OPERADORES = Arrays.asList(
        "+", "-", "*", "/"
    );

    private static final String Sinal_Atrib = ":=";
    
    
    private static final List<String> OPERADORES_RELACIONAIS = Arrays.asList(
        ">", "<", ">=", "<=", "=", "<>"
    );

    private static final List<String> DELIMITADORES = Arrays.asList(
        "(", ")", "[", "]", ";", ".", ",", ":"
    );
    
   
    public String validar(String palavra) {
        String lexema = palavra.trim(); // Remove espaços em branco do início e do fim

        if (verificarPalavraReservada(lexema)) {
            return RESERVADA;
        } else if (verificarDigito(lexema)) {
            return DIGITO;
        } else if (verificarOperador(lexema)) {
            return OPERADOR;
        }else if (verificarOperadorAtribuicao(lexema)) {
            return SINAL_ATRIBUICAO;
        } else if (verificarOperadorRelacional(lexema)) {
            return OPERADOR_RELACIONAL;
        } else if (verificarDelimitador(lexema)) {
            return DELIMITADOR;
        } else if (verificarIdentificador(lexema)) {
            return IDENTIFICADOR;
        } else {
            return ERRO;
        }
    }

    private boolean verificarPalavraReservada(String palavra) {
        return PALAVRAS_RESERVADAS.contains(palavra.toLowerCase());
    }

    private boolean verificarDigito(String palavra) {
        return palavra.matches(DIGITOS);
    }

    private boolean verificarOperador(String palavra) {
        return OPERADORES.contains(palavra);
    }
     private boolean verificarOperadorAtribuicao(String palavra) {
        return Sinal_Atrib.equals(palavra);
    }

    private boolean verificarOperadorRelacional(String palavra) {
        return OPERADORES_RELACIONAIS.contains(palavra);
    }

    private boolean verificarDelimitador(String palavra) {
        return DELIMITADORES.contains(palavra);
    }

    private boolean verificarIdentificador(String palavra) {
        return palavra.matches(IDENTIFICADOR_REGEX);
    }
}
