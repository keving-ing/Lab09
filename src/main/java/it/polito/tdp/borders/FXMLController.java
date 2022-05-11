
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void doCalcolaConfini(ActionEvent event) {
    	
    	int anno; 
    	try
    	{
    		anno = Integer.parseInt(txtAnno.getText());
    		if(anno < 1816 || anno > 2016)
    		{
    			txtResult.setText("L'anno deve essere compreso tra 1816 - 2016");
    			return;
    		}
    	}
    	catch(NumberFormatException e)
    	{
    		txtResult.setText("Inserisci un anno!");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	txtResult.appendText("Vertici: " + model.nV() + "\n");
    	txtResult.appendText("Archi: " + model.nA()+ "\n");
    	Map<Country,Integer> confini = model.numeroConfini();
    	for(Country c:confini.keySet())
    	{
    		txtResult.appendText(c.getNome() + " " + confini.get(c) + "\n");
    	}
    	txtResult.appendText("Numero componenti connesse: " + model.getComponenteConnessa() +"\n");
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
