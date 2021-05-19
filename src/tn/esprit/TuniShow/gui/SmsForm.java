package tn.esprit.TuniShow.gui;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

import java.io.IOException;
import java.io.InputStream;

public class SmsForm extends Form{
    public SmsForm(Form previous){
    setTitle("Confirm your reservation");
    setLayout(BoxLayout.y());
    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack());
    TextField telNumber = new TextField("","+216 ** *** ***");
    Button btn = new Button("send sms");
    btn.setUIID("LoginButton");
        btn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            String myURL = "https://rest.nexmo.com/sms/json?api_key=a1a54fce&api_secret=9o9T4DTrabArd7Qg&to=21625895814&from=TuniShow&text=Your_booking_has_been_confirmed";
            ConnectionRequest cntRqst = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream in) throws IOException {
                }

                @Override
                protected void postResponse() {
                    Dialog.show("SMS", "sms successfully sent", "OK", null);
                }
            };
            cntRqst.setUrl(myURL);
            NetworkManager.getInstance().addToQueue(cntRqst);
        }
    });
        addAll(telNumber,btn);
}}
