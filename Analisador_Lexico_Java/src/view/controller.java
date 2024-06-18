package view;

import controller.AnalisadorV1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Token_Erro;

public class controller implements Initializable {

    @FXML
    private TableColumn<Token, String> col_lexema;
    @FXML
    private TableColumn<Token, String> col_categoria;

    @FXML
    private TableColumn<Token, String> col_linha;

    @FXML
    private TableColumn<Token, String> col_token;

    @FXML
    private TableColumn<Token, String> col_Erro;

    @FXML
    private TableColumn<Token, String> col_Sugestao;

    @FXML
    private TableView<Token> tabela_Resultado;

    @FXML
    private TableView<Token_Erro> tabela_Erro;

    @FXML
    private TextField txtDuracao;

    String pascalProgram = "program GreetUser;\n"
            + "var\n"
            + "  userName: string;\n"
            + "\n"
            + "begin\n"
            + "  write('Enter your name: ');\n"
            + "  readln(userName);\n"
            + "\n"
            + "  writeln('Hello, ', userName, '!');\n"
            + "end.";

    @FXML
    private TextArea txt_area;

    @FXML
    private VBox lineNumbersBox; // Injetar o VBox do arquivo FXML

    @FXML
    private TextField txtErros;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        txtDuracao.setDisable(true);
        txtErros.setDisable(true);
        txt_area.setWrapText(true); // Habilita quebra automática de linha
        lineNumbersBox.setStyle("-fx-padding: 0 5 0 5;"); // Adiciona padding
        updateLineNumbers(txt_area.getText()); // Atualiza os contadores de linhas iniciais

        // Adicionar ouvinte para monitorar as mudanças no texto do TextArea
        txt_area.textProperty().addListener((observable, oldValue, newValue) -> {
            updateLineNumbers(newValue); // Atualiza os contadores de linhas quando o texto muda
        });
        txt_area.setText(pascalProgram);
        //  txt_area.setPromptText(pascalProgram);
        Label placeholText = new Label("TOKENS");
        placeholText.getStyleClass().add("table-view-placeholder");

        tabela_Resultado.setPlaceholder(placeholText);

        //  tabela_Erro.setPlaceholder(new Label(""));
        Label placeholderLabel = new Label("ERROS");
        placeholderLabel.getStyleClass().add("table-view-placeholder");
        tabela_Erro.setPlaceholder(placeholderLabel);

        lineNumbersBox.getStyleClass().add("bordered-vbox");

    }

    // Método para atualizar os contadores de linhas
    private void updateLineNumbers(String text) {
        lineNumbersBox.getChildren().clear(); // Limpar os contadores de linhas existentes

        // Dividir o texto em linhas e adicionar contadores de linhas para cada linha
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lineNumbersBox.getChildren().add(new javafx.scene.control.Label(String.valueOf(i + 1)));
        }

        /*
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
         */
    }

    @FXML
    void limpar(ActionEvent event) {
        txt_area.clear();
        txtDuracao.clear();
        tabela_Resultado.getItems().clear();
        tabela_Erro.getItems().clear();
    }

    private Stage stage;

    @FXML
    void fechar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void minimizar(ActionEvent event) {
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void reload(ActionEvent event) {
        tabela_Resultado.getItems().clear();
        txtDuracao.clear();

        // Criar uma instância de Timer
        Timer timer = new Timer();

        // Criar uma tarefa que será executada após 3 segundos
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // código executado após a espera
                Platform.runLater(() -> executar_codigo(event)); // Usar Platform.runLater() para executar código na thread da interface gráfica
                timer.cancel(); // Cancelar o Timer após a execução da tarefa
            }
        };

        // Agendar a tarefa para ser executada 
        timer.schedule(task, 1000);
    }

    @FXML
    void carregar_arquivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo de Texto");
        File file = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                // Ler o conteúdo do arquivo e exibi-lo no TextArea
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                txt_area.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void salvar_ficheiro(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Arquivo de Texto");
        File file = fileChooser.showSaveDialog((Stage) ((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(txt_area.getText()); // Obtém o texto do TextArea e o escreve no arquivo
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void executar_codigo(ActionEvent event) {
        long startTime = System.currentTimeMillis();

        int quantidade_erros = 0;
        List<Token> lista = new ArrayList<>();

        List<Token_Erro> lista_erro = new ArrayList<>();

        if (!txt_area.getText().equals(' ')) {

            ArrayList<String> linhas = dividirTextoEmLinhas(txt_area.getText());
            int i = 0;

            for (String linha : linhas) {
                //    System.out.println(linha);

                List<String> partes = dividirValidarCaracteres(linha);

                AnalisadorV1 analisador = new AnalisadorV1();

                i++;
                for (String parte : partes) {
                    // Validando a parte atual

                    String[] resultado = analisador.validar(parte);

                    if (resultado[1].equalsIgnoreCase("erro")) {
                        quantidade_erros++;

                        Token_Erro token_erro = new Token_Erro(parte, classificadorErros(parte));
                        lista_erro.add(token_erro);

                    }
                    Token teste = new Token(resultado[0], parte, i, resultado[1]);
                    lista.add(teste);
                }
            }
            col_lexema.setCellValueFactory(new PropertyValueFactory<>("nome_lexema"));
            col_token.setCellValueFactory(new PropertyValueFactory<>("nome_token"));
            col_linha.setCellValueFactory(new PropertyValueFactory<>("linha_token"));
            col_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria_token"));

            ObservableList<Token> observableList = FXCollections.observableArrayList(lista);
            tabela_Resultado.setItems(observableList);
            // Configurar a fábrica de linhas da tabela para aplicar estilos às linhas com erros
            tabela_Resultado.setRowFactory(tv -> new TableRow<Token>() {
                @Override
                protected void updateItem(Token token, boolean empty) {
                    super.updateItem(token, empty);
                    if (token == null || token.getCategoria_token() == null) {
                        return;
                    }
                    // Verificar se a categoria do token é "erro"
                    if (token.getCategoria_token().equalsIgnoreCase("erro")) {
                        // Aplicar estilo à linha
                        setStyle("-fx-background-color: #FFCCCC;"); // Cor de fundo vermelha para indicar erro
                    } else {
                        // Se não for um erro, limpar o estilo
                        setStyle("");
                    }
                }
            });

            col_Erro.setCellValueFactory(new PropertyValueFactory<>("erro"));
            col_Sugestao.setCellValueFactory(new PropertyValueFactory<>("sugestao"));

            ObservableList<Token_Erro> observableListErros = FXCollections.observableArrayList(lista_erro);
            tabela_Erro.setItems(observableListErros);

        }
        // Obter o tempo de término
        long endTime = System.currentTimeMillis();

        // Calcular o tempo de execução
        long duration = endTime - startTime;

        txtDuracao.setText(String.valueOf(duration));
        txtErros.setText(String.valueOf(quantidade_erros));

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
    public static List<String> dividirValidarCaracteres(String linha) {
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
                int fimPalavra = i + 1;
                if (fimPalavra < linha.length() && linha.charAt(fimPalavra) == '=') {
                    partes.add(":=");
                    i += 2;
                    continue;
                } else {
                    partes.add(Character.toString(c));
                    i++;
                    continue;
                }
            }

            //comparacao 
            if (c == '=') {
                partes.add(Character.toString(c));
                i++;
                continue;
            }

            // Delimitadores
            if (c == '/' || c == ',' || c == '/' || c == '.') {
                partes.add(Character.toString(c));
                i++;
                continue;
            }

            if (c == '\'') {
                int fimPalavra = i + 1;
                while (fimPalavra < linha.length() && linha.charAt(fimPalavra) != '\'') {
                    fimPalavra++;
                }
                // Include the closing quote
                if (fimPalavra < linha.length()) {
                    fimPalavra++;
                }
                String palavra = linha.substring(i, fimPalavra);
                partes.add(palavra);
                i = fimPalavra; // Move to the character after the closing quote
                continue;
            }

            //condicionais maior e maior_igual
            if (c == '>') {
                int fimPalavra = i + 1;
                if (fimPalavra < linha.length() && linha.charAt(fimPalavra) == '=') {
                    partes.add(">=");
                    i += 2;
                    continue;
                } else {
                    partes.add(Character.toString(c));
                    i++;
                    continue;
                }
            }

            //condicionais menor e menor_igual
            if (c == '<') {
                int fimPalavra = i + 1;
                if (fimPalavra < linha.length() && linha.charAt(fimPalavra) == '=') {
                    partes.add("<=");
                    i += 2;
                    continue;
                } else if (fimPalavra < linha.length() && linha.charAt(fimPalavra) == '>') {
                    partes.add("<>");
                    i += 2;
                    continue;
                } else {
                    partes.add(Character.toString(c));
                    i++;
                    continue;
                }
            }

            if (Character.isLetter(c)) {
                int fimPalavra = i + 1;
                while (fimPalavra < linha.length() && linha.charAt(fimPalavra) != ' '
                        && linha.charAt(fimPalavra) != '+'
                        && linha.charAt(fimPalavra) != '-'
                        && linha.charAt(fimPalavra) != '*'
                        && linha.charAt(fimPalavra) != ';'
                        && linha.charAt(fimPalavra) != '('
                        && linha.charAt(fimPalavra) != ')'
                        && linha.charAt(fimPalavra) != '['
                        && linha.charAt(fimPalavra) != ']'
                        && linha.charAt(fimPalavra) != '='
                        && linha.charAt(fimPalavra) != ':'
                        && linha.charAt(fimPalavra) != '/'
                        && linha.charAt(fimPalavra) != ','
                        && linha.charAt(fimPalavra) != '\''
                        && linha.charAt(fimPalavra) != '.') {
                    fimPalavra++;
                }
                String palavra = linha.substring(i, fimPalavra);
                partes.add(palavra);
                i = fimPalavra;
                continue;
            }

            // Números
            if (Character.isDigit(c)) {
                int fimNumero = i + 1;
                while (fimNumero < linha.length() && linha.charAt(fimNumero) != ' '
                        && linha.charAt(fimNumero) != '+'
                        && linha.charAt(fimNumero) != '-'
                        && linha.charAt(fimNumero) != '*'
                        && linha.charAt(fimNumero) != ';'
                        && linha.charAt(fimNumero) != '('
                        && linha.charAt(fimNumero) != ')'
                        && linha.charAt(fimNumero) != '['
                        && linha.charAt(fimNumero) != ']'
                        && linha.charAt(fimNumero) != '='
                        && linha.charAt(fimNumero) != ':'
                        && linha.charAt(fimNumero) != '/'
                        && linha.charAt(fimNumero) != ','
                        && linha.charAt(fimNumero) != '\''
                        && linha.charAt(fimNumero) != '.') {
                    fimNumero++;
                }
                String numero = linha.substring(i, fimNumero);
                partes.add(numero);
                i = fimNumero;
                continue;
            } else {
                int fimAleatorio = i + 1;
                while (fimAleatorio < linha.length() && linha.charAt(fimAleatorio) != ' '
                        && linha.charAt(fimAleatorio) != '+'
                        && linha.charAt(fimAleatorio) != '-'
                        && linha.charAt(fimAleatorio) != '*'
                        && linha.charAt(fimAleatorio) != ';'
                        && linha.charAt(fimAleatorio) != '('
                        && linha.charAt(fimAleatorio) != ')'
                        && linha.charAt(fimAleatorio) != '['
                        && linha.charAt(fimAleatorio) != ']'
                        && linha.charAt(fimAleatorio) != '='
                        && linha.charAt(fimAleatorio) != ':'
                        && linha.charAt(fimAleatorio) != '/'
                        && linha.charAt(fimAleatorio) != ','
                        && linha.charAt(fimAleatorio) != '.') {
                    fimAleatorio++;
                }
                String numero = linha.substring(i, fimAleatorio);
                partes.add(numero);
                i = fimAleatorio;
                continue;
            }
        }

        return partes;
    }

    @FXML
    void menu_1(ActionEvent event) {
        //  carregar_arquivo(event); // Chama o método carregar_arquivo passando o evento original
    }

    @FXML
    void menu_2(ActionEvent event) {
        // salvar_ficheiro(event); // Chama o método salvar_ficheiro passando o evento original
    }

    @FXML
    void new_file(ActionEvent event) {
        txt_area.clear();
        txt_area.setFocusTraversable(true);
    }

    public String classificadorErros(String erro) {
        if (erro.isEmpty()) {
            return "<Erro: A string está vazia>";
        }

        // Verifica se começa com aspas e não tem fechamento
        if (erro.charAt(0) == '\'') {
            if (!erro.endsWith("'")) {
                return "<Erro: Feche a abertura de aspas>";
            } else if (erro.length() == 1) {
                return "<Erro: String de aspas vazia>";
            } else {
                return "<Erro: Remova a aspa inicial>";
            }
        }
        
           // Verifica se tem mais de um caractere especial consecutivo
        for (int i = 0; i < erro.length() - 1; i++) {
            char c = erro.charAt(i);
            char nextC = erro.charAt(i + 1);

            if ("!@#$&%".indexOf(c) != -1 && "!@#$&%".indexOf(nextC) != -1) {
                return "<Erro: Remova os caracteres especiais consecutivos>";
            }
        }


        // Verifica se começa com caracteres especiais como !@#$&%
        char firstChar = erro.charAt(0);
        if ("!@#$&%".indexOf(firstChar) != -1) {
            return "<Erro: Remova os caracteres especiais do início>";
        }

        // Verifica se termina com caracteres especiais como !@#$&%
        char lastChar = erro.charAt(erro.length() - 1);
        if ("!@#$&%".indexOf(lastChar) != -1) {
            return "<Erro: Remova os caracteres especiais do final>";
        }

        // Verifica se há números seguidos de letras ou caracteres especiais no meio da string
        boolean hasNumber = false;
        boolean hasLetter = false;
        boolean hasSpecial = false;

        for (int i = 0; i < erro.length(); i++) {
            char c = erro.charAt(i);

            if (Character.isDigit(c)) {
                hasNumber = true;
                if (hasLetter || hasSpecial) {
                    return "<Erro: Remova o número seguido de letra ou caractere especial>";
                }
            } else if (Character.isLetter(c)) {
                hasLetter = true;
                if (hasNumber) {
                    return "<Erro: Remova o texto depois do número>";
                }
            } else if ("!@#$&%".indexOf(c) != -1) {
                hasSpecial = true;
                if (hasLetter) {
                    return "<Erro: Remova os caracteres especiais>";
                }
            } else if (!Character.isLetterOrDigit(c) && c != '_') {
                return "<Erro: Caractere inválido encontrado>";
            }
        }

     
        // Caso não se enquadre nos erros conhecidos
        return "<Sem sugestão: Erro não classificado>";
    }

}
