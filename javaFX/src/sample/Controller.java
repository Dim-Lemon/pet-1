package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn;

    @FXML
    private TextField max_text;

    @FXML
    private TextField min_text;

    @FXML
    private VBox pVbox;

    @FXML
    private Label text_lb;

    private DB db = new DB();
    ResultSet res;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        res = db.getArticles();
        db.isConnected();
        createNewTable();


        btn.setOnAction(event -> {
            //первая проверка
            if(!max_text.getText().equals("")){
                //вторая проверка
                if (!min_text.getText().equals("")){
                    //третья проверка
                    try {
                        if(db.setArticles(max_text.getText(), min_text.getText())){
                            text_lb.setText("Успешно добавленно!");
                            try {
                                pVbox.getChildren().clear();
                                res = db.getArticles();
                                createNewTable();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }else {
                            //третье если
                            text_lb.setText("Укажите другое сокращение");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    //второе если
                    text_lb.setText("Введите сокращенную ссылку");
                }

            }else {
                //первое если
                text_lb.setText("Введите полную ссылку");
            }
        });

    }

    private void createNewTable () throws SQLException {
        while(res.next()) {
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/sample/article.fxml"));

                Label title = (Label) node.lookup("#abb_label");
                title.setText(res.getString("abbName"));

            } catch (IOException e) {
                e.printStackTrace();
            }

            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            pVbox.getChildren().add(hBox);
            pVbox.setSpacing(10);
        }
    }

    }




/*
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/sample/article.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hBox = new HBox();
            hBox.getChildren().add(nodes[i]);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVbox.getChildren().add(hBox);
            paneVbox.setSpacing(10);
        }

 */


        /*
        for (int i = 0; i < 10; i++) {
            Button btn1 = new Button();
            btn.setId("btn1");
            HBox hBox = new HBox();
            hBox.getChildren().add(btn1);
            hBox.setAlignment(Pos.BASELINE_LEFT);
            pVbox.getChildren().add(hBox);
            pVbox.setSpacing(10);
        }

         */