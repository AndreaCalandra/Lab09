
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> cbxCountry;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	int x;
    	try {
    		x = Integer.parseInt(txtAnno.getText());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un numero");
    		return;
    	}
    	model.creaGrafo(x);
    	txtResult.appendText("il numero di componenti connesse è: " + Integer.toString(model.getNumeroComponentiConnesse()));
    	txtResult.appendText("\nIl numero di vertici è: " + Integer.toString(model.numVertici()));
    	txtResult.appendText("\nIl numero di archi è: " + Integer.toString(model.numArchi()) + "\n");
    	txtResult.appendText(model.rotte(x));

    	cbxCountry.getItems().addAll(this.model.getVertici(x)) ;
    }
    
    @FXML
    void doAnalizza(ActionEvent event) {
    	Country c = cbxCountry.getValue();
    	txtResult.clear();
    	txtResult.appendText("posso raggiungere i seguenti stati: \n");
    	txtResult.appendText(model.statiRaggiungibiliGetParent(c).toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
