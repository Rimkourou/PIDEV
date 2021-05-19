/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package tn.esprit.TuniShow.gui;


import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.services.ServicesUser;

public class LoginForm extends Form {
    private TextField login;
    private TextField password;
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        
        
        String n=SessionManager.getNom();
        
        
        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome ", "WelcomeWhite"),
                new Label(n, "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
        Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        profilePicLabel.setMask(mask.createMask());
        
      
        this.login=new TextField("","Login", 20, TextField.EMAILADDR);
        this.password=new TextField("", "Password", 1,TextField.PASSWORD);
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
//            String l=this.login.getText();
//            String m=this.password.getText();
            ServicesUser.getInstane().signin(login, password, theme);
            
//            //TODO
//                try {
//                 User user=null;
//                Cursor c1 =  MyApplication.base.executeQuery("SELECT * FROM user WHERE login='"+this.login.getText()+"' and password='"+this.password.getText()+"' ");
//                while(c1.next()){
//                    Row r = c1.getRow();
//                    user=new User();
//                    user.setId(r.getInteger(0));
//                    user.setNom(r.getString(1));
//                    user.setPrenom(r.getString(2));
//                   // user.setAge(r.getInteger(3));
//                    user.setEmail(r.getString(4));
//                    user.setPassword(r.getString(5));
//                }
//                System.out.println(user);
//                if(user==null){
//                    Dialog.show("Alert", "wrong credentials", new Command("OK"));
//                }
//                else{
//                    new FilmForm(theme).show();
//                }
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
        });
        
        //password forgetten
        Button mp=new Button("Password Forgotten");
        //mp.setUIID("mpButton");
        //password forgotten event
        mp.addActionListener(ex->{
           new ActivateForm(theme).show();
        });
        
        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener(ex->{
           SignUpForm suf=new SignUpForm("signup",this);
           suf.show();
        });
        
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                FlowLayout.encloseCenter(createNewAccount),
                FlowLayout.encloseCenter(mp)
                
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
