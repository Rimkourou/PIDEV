package InterfaceReservation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import static com.stripe.param.checkout.SessionCreateParams.ShippingAddressCollection.AllowedCountry.SC;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.stripe.model.Charge;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InterfacePaiementController implements Initializable {
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCin;

    @FXML
    private TextField tfCcNum;

    @FXML
    private TextField tfExpM;

    @FXML
    private TextField tfExpY;

    @FXML
    private TextField tfCvv;

    @FXML
    private Button payBtn;

    private static Charge charge;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfCcNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,16}?")) {
                    tfCcNum.setText(oldValue);
                }
            }
        });

        tfExpM.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}?")) {
                    tfExpM.setText(oldValue);
                }
            }
        });

        tfExpY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,4}?")) {
                    tfExpY.setText(oldValue);
                }
            }
        });

        tfCvv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,4}?")) {
                    tfCvv.setText(oldValue);
                }
            }
        });

        tfCin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,8}?")) {
                    tfCin.setText(oldValue);
                }
            }
        });
    }

    @FXML
    void Pay() throws AWTException {
        try {
            Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
            Customer a = Customer.retrieve("sk_test_4eC39HqLyjWDarjtT1zdp7dc");
            Map <String, Object> cardParam = new HashMap<String, Object>();
            if(!(tfCcNum.getText().equals("") || tfExpM.getText().equals("") || tfExpY.getText().equals("") || tfCvv.getText().equals(""))){
                cardParam.put("number", tfCcNum.getText());
                cardParam.put("exp_month", Integer.parseInt(tfExpM.getText()));
                cardParam.put("exp_year", Integer.parseInt(tfExpY.getText()));
                cardParam.put("cvc", tfCvv.getText());
                cardParam.put("name", tfName.getText());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please check your card information!");
                return;
            }
            Map <String, Object> tokenParam = new HashMap<String, Object>();
            tokenParam.put("card", cardParam);

            Token token = Token.create(tokenParam);

            Map<String, Object> source = new HashMap<String, Object>();
            source.put("source", token.getId());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(token));

            Map<String,Object> chargeParam = new HashMap<String, Object>();
            chargeParam.put("amount", 0.50);
            chargeParam.put("currency", "usd");
            chargeParam.put("source", token.getId());
            chargeParam.put("receipt_email",tfEmail.getText() );

            if (SystemTray.isSupported()) {
                JavaMailTransaction.sendMail(tfEmail.getText());
                JOptionPane.showMessageDialog(null, "payment done with success!");
                System.err.println("notiff paie ");
            } else {
                JOptionPane.showMessageDialog(null, "Please check your card info!");
                System.err.println("Erreur!!!!");
            }
            Charge.create(chargeParam);
        }catch(StripeException e){
            System.out.println(e.getMessage());} catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button linkRes;

    @FXML
    private AnchorPane reservationPage;

    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("InterfaceMyReservationList.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }

}
