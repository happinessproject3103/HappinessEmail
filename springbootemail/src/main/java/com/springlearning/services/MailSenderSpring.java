package com.springlearning.services;

import com.springlearning.model.EMail;
import freemarker.template.Configuration;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class MailSenderSpring {
    @Autowired
    JavaMailSender javaMailSender;
    @Qualifier("getFreeMarkerConfiguration")
    @Autowired
    Configuration fmConfiguration;

    @Value("classpath:/2.png")
    Resource resourceFile;

    public void sendEmail(EMail mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getTo());
        msg.setFrom(mail.getFrom());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getContent());
        javaMailSender.send(msg);
    }

    @SneakyThrows
    public void sendEmailWithAttachment(EMail mail) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

        helper.setTo(mail.getTo());
        helper.setFrom(mail.getFrom());
        helper.setSubject(mail.getSubject());
       // helper.setText(mail.getContent());

        File fileToInsert = getFileToInsert();
        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.setHeader("Content-ID", "AbcXyz123");
        imagePart.setDisposition(MimeBodyPart.INLINE);
//// attach the image file
       imagePart.attachFile(fileToInsert);

        StringBuffer body
                = new StringBuffer("<html>This message contains two inline images.<br>");
        body.append("Hello:<br>");
        body.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
        body.append("The second one is a cube:<br>");
        body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
        body.append("End of message.");
        body.append("</html>");

        // inline images
        Map<String, String> inlineImages = new HashMap<String, String>();
        inlineImages.put("image1", "M:/Candice Webb/2022 Hackathon/All/5.jpg");
        inlineImages.put("image2", "/resources/images/2.jpg");
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body.toString(), "text/html");


        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds inline image attachments
        if (inlineImages != null && inlineImages.size() > 0) {
            Set<String> setImageID = inlineImages.keySet();

            for (String contentId : setImageID) {
                //MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);

//                String imageFilePath = inlineImages.get(contentId);
//                try {
//                    imagePart.attachFile(imageFilePath);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }

                multipart.addBodyPart(imagePart);
            }
        }

        msg.setContent(multipart);
        javaMailSender.send(msg);
    }

    private File getFileToInsert() throws URISyntaxException {
        Map map = new HashMap();
        map.put(1,"1.htm");
        map.put(2,"2.htm");
        map.put(3,"3.htm");
        map.put(4,".jpg");
        map.put(5,"5.jpg");
        map.put(6,"6.jpg");
        map.put(7,"7.png");
        map.put(8,"8.png");
        map.put(9,"9.png");
        map.put(10,"10.jpg");
        map.put(11,"11.jpg");
        map.put(12,"12.jpg");
        map.put(13,"13.jpg");
        map.put(14,"14.jpg");
        map.put(15,"15.jpg");
        map.put(16,"16.jpg");
        map.put(17,"17.jpg");
        map.put(18,"18.jpg");
        map.put(19,"19.jpg");
        map.put(20,"20.jpg");
        map.put(21,"21.jpg");
        map.put(22,"22.jpg");

        Random rand = new Random();
        int imageName = rand.nextInt(map.size());

        URL resource = getClass().getClassLoader().getResource("images/All/"+map.get(imageName));
        File fileToInsert = new File(resource.toURI());
        return fileToInsert;
    }

    public void sendEmailWithTemplate(EMail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setTo(mail.getTo());
            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(geContentFromTemplate(mail.getModel()), "text/html");


            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            File fileToInsert = getFileToInsert();
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setHeader("Content-ID", "AbcXyz123");
            imagePart.setDisposition(MimeBodyPart.INLINE);
                //// attach the image file
            imagePart.attachFile(fileToInsert);
            multipart.addBodyPart(imagePart);
            mimeMessage.setContent(multipart);
//            mimeMessageHelper.setText(mail.getContent(), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(Map<String, Object> model) {
        StringBuilder content = new StringBuilder();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("template1.html"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
