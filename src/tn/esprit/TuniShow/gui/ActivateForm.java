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

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;


import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;

import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.services.ServicesUser;


import javax.mail.Authenticator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ActivateForm extends Form {
    static int currentUser;
    TextField emails;
    public ActivateForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb=new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("IMGLogin");
        getTitleArea().setUIID("Container");
        Form previous=Display.getInstance().getCurrent();
        tb.setBackCommand("",e->previous.showBack());
        setUIID("LoginForm");
        
        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome, ", "WelcomeWhite"),
                new Label("Wifek", "WelcomeBlue")
        );
        add(BorderLayout.NORTH,
                BoxLayout.encloseY(
                    new Label(theme.getImage("user.png"),"LogoLabel")
                )
                );
        
        //getTitleArea().setUIID("Container");
        
        
        
        
//        Image profilePic = theme.getImage("user-picture.jpg");
//        Image mask = theme.getImage("round-mask.png");
//        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
//        Label profilePicLabel = new Label(profilePic, "ProfilePic");
//        profilePicLabel.setMask(mask.createMask());
        
    
       //léhné nabdéw
       this.emails=new TextField("","Enter Your Email",20,TextField.ANY);
       emails.setSingleLineTextArea(false);
       
       
       Button valider=new Button("Valider");
       valider.setUIID("LoginButton");
       Label haveAnAccount=new Label("Return to Connect?");
       haveAnAccount.setUIID("CreateNewAccountButton");
       Button signIn=new Button("Sign In");
       signIn.addActionListener( t-> previous.showBack());
       //signIn.setUIID("CenterLink");
       
        Label loginIcon = new Label("", "TextField");
        
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        
       
       // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
       
       Container content=BoxLayout.encloseY(
       
               new FloatingHint(emails),
               valider,
               spaceLabel,
               FlowLayout.encloseCenter(haveAnAccount),
               FlowLayout.encloseCenter(signIn)
       );
       
       content.setScrollableY(true);
       add(BorderLayout.CENTER,content);
       valider.requestFocus();
       valider.addActionListener(e->{
           InfiniteProgress ip=new InfiniteProgress();
           final Dialog ipDialog=ip.showInfiniteBlocking();
           //api mailing
          
           String emaill=this.emails.getText();
           String mp= ServicesUser.getInstane().getPasswordByEmail(emails.getText(), theme);
           
           //System.out.println(mp);
           //System.out.println(emails.getText());
           try {
               sendMail(emaill,"Welcome to TUNISHOW: Enter your password: "+mp+" and then validate");
           } catch (Exception ex) {
           }
           
           ipDialog.dispose();
           Dialog.show("Password","We sent you your Password to your email address,Please verify your Email",new Command("ok"));
          
           
       });
      
        content.setScrollableY(true);
        content.setScrollVisible(false);
    }
    //send mail
       public void sendMail(/*Resources theme*/ String reception,String msg1){
//           try {
//               
//               Properties props=new Properties();
//                props.put("mail.transport.protocol","smtp");
//                props.put("mail.transport.host","smtp.gmail.com");
//                props.put("mail.transport.auth","true");
//                
//               Session session=Session.getInstance(props,null);
//               Message message=new MimeMessage(session);
//               
//            String myEmail="tunishow.tn@gmail.com";
//            String email=emails.getText();
//            
//            message.setFrom(new InternetAddress(myEmail));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
//            message.setSubject("TUNISHOW : Confirm Password");
//            
//            String mp=ServicesUser.getInstane().getPasswordByEmail(emails.getText(), theme);
//            String txt="Welcome to TUNISHOW: Enter this new password: "+mp+" and then validate";
//            message.setText(txt);
//            SMTPTransport st=(SMTPTransport)session.getTransport("smtps");
//            st.connect("smtp.gmail",587,"tunishow.tn@gmail.com","tunishow1234");
//            
//            st.sendMessage(message, message.getAllRecipients());
//            
//               System.out.println("server response :"+st.getLastServerResponse());
//                
//           } catch (Exception e) {
//               e.printStackTrace();
//           }
//       }

        System.out.println("preparing to send mail");
        Properties properties =new Properties ();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        
        String myEmail="tunishow.tn@gmail.com";
        String password="tunishow1234";
        
        Session session=Session.getInstance(properties,new Authenticator() {
        @Override 
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(myEmail,password);
        }
        });
        
        try {
        Message message =prepareMessage(session,myEmail,reception,msg1);
        
            Transport.send(message);
        } catch (MessagingException ex) {
           
        }
        System.out.println("message sent ");     
}
       public static Message prepareMessage(Session session,String myEmail,String reception,String msg) throws MessagingException {
       
        
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reception) );
            message.setSubject("WELCOME TO TUNISHOW");
            message.setText(msg);
            return message;
        } catch (AddressException ex) {
           
        }  
       return null;
        
    }
}


