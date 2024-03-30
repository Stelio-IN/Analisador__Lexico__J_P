package controller;

/**
 *
 * @author steli
 */
import java.util.Arrays;
import java.util.List;

public class AnalisadorV1 {

    private final String RESERVADA = "Palavra Reservada";
    private final String DIGITO = "Número";
    private final String OPERADOR = "Operador Aritmetico";
    private final String DELIMITADOR = "Delimitador";
    private final String ERRO = "Erro";
    private final String IDENTIFICADOR = "Identificador";
    private final String OPERADOR_RELACIONAL = "Operador relacional";
    private final String SINAL_ATRIBUICAO = "Operador de atribuicao";
    
    private static final String DIGITOS = "[0-9]+";
    
    private static String IDENTIFICADOREXPRESSAO = "[a-zA-Z_]+([0-9_]*[a-zA-Z]*)*";

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

    private static final char especial = '\'';
    


    public String validar(String palavra) {
        // String lexema = palavra.trim(); // Remove espaços em branco do início e do fim

        if (verificarPalavraReservada(palavra)) {
            
            if(palavra.equalsIgnoreCase("integer")||palavra.equalsIgnoreCase("char")||palavra.equalsIgnoreCase("boolean")){
                return "<Value_Type>";
            }
            if(palavra.equalsIgnoreCase("div")){
                return "<>";
            }
            if(palavra.equalsIgnoreCase("if")){
                return "<>";
            }
            
            return RESERVADA;
        } else if (verificarDigito(palavra)) {
            return DIGITO;
        } else if (verificarOperador(palavra)) {
            return OPERADOR;
        } else if (verificarOperadorAtribuicao(palavra)) {
            return SINAL_ATRIBUICAO;
        } else if (verificarOperadorRelacional(palavra)) {
            return OPERADOR_RELACIONAL;
        } else if (verificarDelimitador(palavra)) {
            return DELIMITADOR;
        } else if (verificarIdentificador(palavra)) {
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
      private boolean verificarIdentificador(String palavra) {
        return palavra.matches(IDENTIFICADOREXPRESSAO);
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
    
       /*
    public static boolean isValidPascalIdentifier(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        char firstChar = str.charAt(0);
        if (!Character.isLetter(firstChar) && firstChar == '_') {
            return false; // O primeiro caractere deve ser uma letra ou sublinhado (_)
        }
        if (str.startsWith("@")||str.startsWith("!")||str.startsWith("#")||str.startsWith("~")||str.startsWith("'")
                ||str.startsWith("´") ||str.startsWith("º")||str.startsWith("ª") ||str.startsWith("á")
                ||str.startsWith("à")||str.startsWith("é")||str.startsWith("è")||str.startsWith("ã")||str.startsWith("â")
                ||str.startsWith("ê")||str.startsWith("õ")||str.startsWith("ó")||str.startsWith("ò")||str.startsWith("ç")) {
            return false;
        }

        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false; // Os caracteres subsequentes devem ser letras, dígitos ou sublinhados (_)
            }
        }
        return true;
    }*/
}
