package com.view.pessoa;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.DAO.CarroDAO;
import com.Entity.Carro;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerCarro extends Application implements Initializable {

	@FXML
	private TextField textFieldModelo;

	@FXML
	private TextField textFieldValor;

	@FXML
	private TextField textFieldMarca;

	@FXML
	private TextField textFieldAno;

	@FXML
	private TextArea textAreaCarros;

	@FXML
	private TextField textField_ID;
	@FXML
	private Label labelTextId;

	@FXML
	private Label labelTextIdInserted;
	@FXML
	private Label qtd;

	@FXML
	private Label labelQtd;
	

	@FXML
	void ExcluirCarro(ActionEvent event) {
		  Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Este carro foi vendido???");
	        alert.setHeaderText("Realmente deseja excluir do estoque?");
	        alert.setContentText("OK???");
	        Optional<ButtonType> resultado = alert.showAndWait();
	        if (resultado.get() == ButtonType.OK){
	    		Carro carro = pegaDadosID();
	    		int qtde = new CarroDAO().deletar(carro.getId());		
	    		limpaCampos();
	    		listarCarros();
	        }
	}

	@FXML
	void PesquisarCarroID(ActionEvent event) {
		String idString = textField_ID.getText();
		Carro carro = null;
		if (!idString.equals("")) {
			try {
				int id = Integer.valueOf(idString);
				carro = new CarroDAO().findByID(id);
			} catch (Exception e) {

			}

			if (carro != null) {
				labelTextId.setVisible(true);
				labelTextIdInserted.setVisible(true);
				labelTextId.setText(carro.getId() + "");
				textFieldMarca.setText(carro.getMarca());
				textFieldModelo.setText(carro.getModelo());
				textFieldAno.setText("" + carro.getAno());
				textFieldValor.setText(carro.getValor() + "");
			}

		}
		textField_ID.clear();

	}

	@FXML
	void alterarCarros(ActionEvent event) {
		Carro carro = pegaDadosID();
		limpaCampos();
		int qtde = new CarroDAO().alterar(carro);
		listarCarros();
	}

	private void limpaCampos() {
		textFieldModelo.clear();
		textFieldMarca.clear();
		textFieldAno.clear();
		textFieldValor.clear();
		textFieldMarca.requestFocus();
		labelTextId.setVisible(false);
		labelTextIdInserted.setVisible(false);
		labelTextIdInserted.setText("");

	}

	private Carro pegaDadosID() {
		return new Carro(Integer.valueOf(labelTextId.getText()), textFieldMarca.getText(), textFieldModelo.getText(),
				Integer.valueOf(textFieldAno.getText()), Float.valueOf(textFieldValor.getText()));
	}

	private Carro pegaCarros() {
		return new Carro(textFieldMarca.getText(), textFieldModelo.getText(), Integer.valueOf(textFieldAno.getText()),
				Float.valueOf(textFieldValor.getText()));
	}

	public void execute() {
		launch();
	}

	@FXML
	void inserirCarros(ActionEvent event) {
		Carro carro = pegaCarros();
		limpaCampos();
		int qtde = new CarroDAO().inserirCarro(carro);
		listarCarros();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		listarCarros();
	}

	private void listarCarros() {

		textAreaCarros.clear();
		List<Carro> listaCarro = new CarroDAO().listAll();
		listaCarro.forEach(carro -> {
			textAreaCarros.appendText(carro.toString() + "\n");
		});
		labelQtd.setText(listaCarro.size()+"");

	}

	@Override
	public void start(Stage stage) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("Carro.fxml"));
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
