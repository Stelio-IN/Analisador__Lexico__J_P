package view;

import controller.AnalisadorV1;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Token;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

public class controller implements Initializable {

    @FXML
    private TableColumn<Token, String> col_lexema;

    @FXML
    private TableColumn<Token, String> col_linha;

    @FXML
    private TableColumn<Token, String> col_token;

    @FXML
    private TableView<Token> tabela_Resultado;

    @FXML
    private TextField txtDuracao;

    @FXML
    private TextArea txt_area;

    @FXML
    private VBox lineNumbersBox; // Injetar o VBox do arquivo FXML

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtDuracao.setDisable(true);
        txt_area.setWrapText(true); // Habilita quebra automática de linha
        lineNumbersBox.setStyle("-fx-padding: 0 5 0 5;"); // Adiciona padding
        updateLineNumbers(txt_area.getText()); // Atualiza os contadores de linhas iniciais

        // Adicionar ouvinte para monitorar as mudanças no texto do TextArea
        txt_area.textProperty().addListener((observable, oldValue, newValue) -> {
            updateLineNumbers(newValue); // Atualiza os contadores de linhas quando o texto muda
        });

    }

    
    
    
    // Método para atualizar os contadores de linhas
    private void updateLineNumbers(String text) {
        lineNumbersBox.getChildren().clear(); // Limpar os contadores de linhas existentes

        // Dividir o texto em linhas e adicionar contadores de linhas para cada linha
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lineNumbersBox.getChildren().add(new javafx.scene.control.Label(String.valueOf(i + 1)));
        }

        // Adiciona um ouvinte para monitorar mudanças no texto do TextArea
        txt_area.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Chama o método para dividir o texto em linhas
                ArrayList<String> linhas = dividirTextoEmLinhas(newValue);
                // Exibe o número total de linhas
                System.out.println("Número de linhas: " + linhas.size());
            }
        });
    }

    @FXML
    void pause(ActionEvent event) {

    }

    @FXML
    void reload(ActionEvent event) {

    }

    @FXML
    void run(ActionEvent event) {
        long startTime = System.currentTimeMillis();
        List<Token> lista = new ArrayList<>();
        if (!txt_area.getText().equals(' ')) {
            ArrayList<String> linhas = dividirTextoEmLinhas(txt_area.getText());
            int i = 0;
            for (String linha : linhas) {
                //    System.out.println(linha);

                List<String> partes = analisarLexicamente(linha); // AQUI está a mudança

                AnalisadorV1 analisador = new AnalisadorV1();

                i++;
                for (String parte : partes) {
                    // Validando a parte atual

                    String resultado = analisador.validar(parte);
                    Token teste = new Token( resultado,parte, String.valueOf(i));
                    lista.add(teste);
                }
            }
            col_lexema.setCellValueFactory(new PropertyValueFactory<>("nome_lexema"));
            col_token.setCellValueFactory(new PropertyValueFactory<>("nome_token"));
            col_linha.setCellValueFactory(new PropertyValueFactory<>("linha_token"));

            ObservableList<Token> observableList = FXCollections.observableArrayList(lista);
            tabela_Resultado.setItems(observableList);

        }
        // Obter o tempo de término
        long endTime = System.currentTimeMillis();

        // Calcular o tempo de execução
        long duration = endTime - startTime;

        txtDuracao.setText(String.valueOf(duration));

    }

    private ArrayList<String> dividirTextoEmLinhas(String textoTextArea) {
        ArrayList<String> linhas = new ArrayList<>();
        String[] linhasTexto = textoTextArea.split("\\n");
        for (String linha : linhasTexto) {
            linhas.add(linha);
        }
        return linhas;
    }

    //Metodo para dividir e validar caracteres 
    public static List<String> analisarLexicamente(String linha) {
        List<String> partes = new ArrayList<>();
        int i = 0;

        while (i < linha.length()) {
            // Ignora espaços em branco
            while (i < linha.length() && Character.isWhitespace(linha.charAt(i))) {
                i++;
            }

            if (i == linha.length()) {
                break;
            }

            // Símbolos especiais
            char c = linha.charAt(i);
            if (c == '(' || c == ')' || c == '[' || c == ']') {
                partes.add(Character.toString(c));
                i++;
                continue;
            }

            // Operadores
            if (c == '+' || c == '-' || c == '*' || c == ';') {
                partes.add(Character.toString(c));
                i++;
                continue;
            }

            // Atribuição
            if (c == ':') {
                if (i + 1 < linha.length() && linha.charAt(i + 1) == '=') {
                    partes.add(":=");
                    i += 2;
                    continue;
                } else {
                    partes.add(Character.toString(c));
                }
            }else

            // Identificadores e palavras-chave
            if (Character.isLetter(c)) {
                int fimPalavra = i + 1;
                while (fimPalavra < linha.length() && (Character.isLetterOrDigit(linha.charAt(fimPalavra)) || linha.charAt(fimPalavra) == '_')) {
                    fimPalavra++;
                }
                String palavra = linha.substring(i, fimPalavra);
                partes.add(palavra);
                i = fimPalavra;
                continue;
            }else

            // Números
            if (Character.isDigit(c)) {
                int fimNumero = i + 1;
                while (fimNumero < linha.length() && Character.isDigit(linha.charAt(fimNumero))) {
                    fimNumero++;
                }
                String numero = linha.substring(i, fimNumero);
                partes.add(numero);
                i = fimNumero;
                continue;
            }else

            // Delimitadores
            if (c == '/' || c == ',') {
                partes.add(Character.toString(c));
                i++;
                continue;
            }else

            //erro para string que comeca com _
                //((c == '_')|| (c == '@') || c == '!') 
            if (c == '_') {
                int fimPalavra = i + 1;
                while (fimPalavra < linha.length() && (Character.isLetterOrDigit(linha.charAt(fimPalavra)) || linha.charAt(fimPalavra) == '_')) {
                    fimPalavra++;
                }
                String palavra = linha.substring(i, fimPalavra);
                partes.add(palavra);
                i = fimPalavra;
                continue;
            }else{
                partes.add(Character.toString(c));
                i++;
            }
            // Ignora outros caracteres
//            i++;
        }

        return partes;
    }
}
