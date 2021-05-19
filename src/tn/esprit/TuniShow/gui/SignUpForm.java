/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import tn.esprit.TuniShow.MyApplication;
import tn.esprit.TuniShow.services.ServicesUser;

import java.io.IOException;

public class SignUpForm extends Form{
    private TextField login;
    private TextField mdp;
    private TextField nom;
    private TextField prenom;
    private Picker age;
    //private TextField age;
    private Button back;
    private Button sign_up;
    
    public SignUpForm(String title,LoginForm lf) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome, ", "WelcomeWhite"),
                new Label("", "WelcomeBlue")
        );
        
        
        this.setLayout(new FlowLayout(CENTER, CENTER));
        Container c=new Container(BoxLayout.y());
        this.nom=new TextField("","UserName");
        this.prenom=new TextField("","LastName");
        this.login=new TextField("","Email");
        this.mdp=new TextField("", "Password", 1,TextField.PASSWORD);
        this.age=new Picker();
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        //this.age=new TextField("","Date of Birth");
//        Container c=BoxLayout.encloseY(
//                
//        new FloatingHint(nom),
//        new FloatingHint(prenom),
//        new FloatingHint(login),
//        new FloatingHint(mdp)
//        );
//       

        
        sign_up=new Button("Sign up");
        sign_up.setUIID("LoginButton");
        sign_up.requestFocus();
        back=new Button("Back");
        
        sign_up.addActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent evt) {
//                if((nom.getText().length()==0) || (prenom.getText().length()==0) || (login.getText().length()==0) || mdp.getText().length()==0)
//                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
//                else
//                {
//                    DateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
//                    String strDate=dateFormat.format(age.getDate());
//                    String req = "INSERT INTO user (nom,prenom,age,login,password) "
//                    + "VALUES( '"+nom.getText()+"' , '"+prenom.getText()+"' ,'"+strDate+"','"+login.getText()+"','"+mdp.getText()+"' );";
//                    try {
//                        MyApplication.base.execute(req);
//                        Dialog.show("Success", "user added successfully", new Command("OK"));
//                    } catch (IOException ex) {
//                        System.err.println("person not stored");
//                        System.err.println(ex);
//                    }
                    try {
                      
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        String strDate=dateFormat.format(age.getDate());
                        
                        ServicesUser.getInstane().signup(nom, prenom, login, mdp,strDate);
//                        InfiniteProgress ip=new InfiniteProgress(); //loading after insert dat
//                        final Dialog iDialog=ip.showInfiniteBlocking();
//                        
//                        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//                        String strDate=dateFormat.format(datePicker.getDate());
//                        
//                        User user=new User(String.valueOf(nom.getText()).toString(),
//                                String.valueOf(prenom.getText()).toString(),
//                                String.valueOf(login.getText()).toString(),
//                                String.valueOf(mdp.getText()).toString(),
//                                datePicker.getDate());
                        Dialog.show("Success", "user added successfully", new Command("OK"));
                    } catch (Exception e) {
                        System.err.println("person not stored");
                        System.err.println(e);
                    }
                    
  
//                }
            }
        });
        
        back.addActionListener(t->{
            lf.showBack();
        });
        c.addAll(nom,prenom,login,mdp,datePicker,sign_up,back);
        this.add(c);
    }

    
}
