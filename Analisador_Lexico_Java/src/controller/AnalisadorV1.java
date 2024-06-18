package controller;

/**
 *
 * @author steli
 */
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;
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

    public String[] validar(String palavra) {
        String[] strings = new String[2];
        strings[0] = "";
        strings[1] = "";

        if (verificarPalavraReservada(palavra)) {

            if (palavra.equalsIgnoreCase("div")) {
                strings[0] = "<div>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("or")) {
                strings[0] = "<or>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("and")) {
                strings[0] = "<and>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("not")) {
                strings[0] = "<not>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("if")) {
                strings[0] = "<if>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("then")) {
                strings[0] = "<then>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("else")) {
                strings[0] = "<else>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("of")) {
                strings[0] = "<of>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("while")) {
                strings[0] = "<while>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("do")) {
                strings[0] = "<do>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("begin")) {
                strings[0] = "<begin>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("end")) {
                strings[0] = "<end>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("read")) {
                strings[0] = "<read>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("write")) {
                strings[0] = "<write>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("var")) {
                strings[0] = "<var>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("array")) {
                strings[0] = "<array>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("function")) {
                strings[0] = "<function>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("procedure")) {
                strings[0] = "<procedure>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("program")) {
                strings[0] = "<program>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("true")) {
                strings[0] = "<true>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("false")) {
                strings[0] = "<false>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("char")) {
                strings[0] = "<char>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("integer")) {
                strings[0] = "<integer>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("boolean")) {
                strings[0] = "<boolean>";
                strings[1] = "special_symbol";
            }

        } else if (isDigitsOnly(palavra)) {
            strings[0] = "<digito>";
            strings[1] = "digito";
        } else if (verificarOperador(palavra)) {
            //  "+", "-", "*", "/"
            if (palavra.equalsIgnoreCase("+")) {
                strings[0] = "<soma>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("-")) {
                strings[0] = "<subtração>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("/")) {
                strings[0] = "<divisão>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("*")) {
                strings[0] = "<multiplicação>";
                strings[1] = "special_symbol";
            }
        } else if (verificarOperadorAtribuicao(palavra)) {
            strings[0] = "<atribuicao>";
            strings[1] = "special_symbol";
        } else if (verificarOperadorRelacional(palavra)) {
            // ">", "<", ">=", "<=", "=", "<>"
            if (palavra.equalsIgnoreCase(">")) {
                strings[0] = "<maior>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("<")) {
                strings[0] = "<menor>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase(">=")) {
                strings[0] = "<maior_igual>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("<=")) {
                strings[0] = "<menor_igual>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("=")) {
                strings[0] = "<igual>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("<>")) {
                strings[0] = "<diferente>";
                strings[1] = "special_symbol";
            }
        } else if (verificarDelimitador(palavra)) {
            //  "(", ")", "[", "]", ";", ".", ",", ":"
            if (palavra.equalsIgnoreCase(")")) {
                strings[0] = "<fechar_parenteses>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("(")) {
                strings[0] = "<abrir_parenteses>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("[")) {
                strings[0] = "<abrir_conchetes>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase("]")) {
                strings[0] = "<fechar_conchetes>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase(";")) {
                strings[0] = "<ponto_virgula>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase(".")) {
                strings[0] = "<ponto>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase(",")) {
                strings[0] = "<virgula>";
                strings[1] = "special_symbol";
            } else if (palavra.equalsIgnoreCase(":")) {
                strings[0] = "<dois_pontos>";
                strings[1] = "special_symbol";
            }
        } else if (isValidIdentifier(palavra)) {
            strings[0] = "<identificador>";
            strings[1] = "identificador";
        } else if (verificarString(palavra)) {
            strings[0] = "<String>";
            strings[1] = "texto";
        }  else {
            strings[0] = "<erro>";
            strings[1] = "erro";
        }
        return strings;
    }

    private boolean verificarPalavraReservada(String palavra) {
        return PALAVRAS_RESERVADAS.contains(palavra.toLowerCase());
    }
/*
    private boolean verificarDigito(String palavra) {
        return palavra.matches(DIGITOS);
    }*/

   /* private boolean verificarIdentificador(String palavra) {
        return palavra.matches(IDENTIFICADOREXPRESSAO);
    }*/

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

    private boolean verificarString(String palavra) {
        if (palavra.charAt(0) == '\'' && palavra.charAt(palavra.length() - 1) == '\'' && palavra.length()>=3) {
            return true;
        } else {
            return false;

        }

    }
      public static boolean isValidIdentifier(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        // Verifica o primeiro caractere
        char firstChar = s.charAt(0);
        if (!isLetter(firstChar)) {
            return false;
        }

        // Verifica os caracteres subsequentes
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        return true;
    }
      
       public static boolean isDigitsOnly(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
