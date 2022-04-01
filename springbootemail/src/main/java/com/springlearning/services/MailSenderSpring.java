package com.springlearning.services;

import com.springlearning.model.EMail;
import com.springlearning.model.User;
import freemarker.template.Configuration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
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

        File fileToInsert = getFileToInsert((User)mail.getModel().get("user"));
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

    private File getFileToInsert(User user) throws URISyntaxException {
        List<String> files = new ArrayList<>();
        files.add("animals/1.jpg");
        files.add("animals/2.jpg");
        files.add("animals/3.jpg");

        files.add("nature/1.jpg");
        files.add("nature/2.jpg");
        files.add("nature/3.jpg");

        files.add("quotes/1.jpg");
        files.add("quotes/2.jpg");
        files.add("quotes/3.jpg");
        files.add("quotes/4.jpg");
        files.add("quotes/5.jpg");
        files.add("quotes/6.jpg");

        files.add("recipes/1.jpg");
        files.add("recipes/2.jpg");
        files.add("recipes/3.jpg");
        files.add("recipes/4.jpg");

        files.add("jokes/1.png");
        files.add("jokes/2.png");
        files.add("jokes/3.png");

        List<String> userLikes = user.getLikes().keySet().stream().filter(key -> user.getLikes().get(key) == 1).collect(Collectors.toList());
        List<String> filesThatUserLike = files.stream().filter(file -> doesUserLike(file, userLikes)).collect(Collectors.toList());

        Random rand = new Random();
        int imageName = rand.nextInt(filesThatUserLike.size());

        URL resource = getClass().getClassLoader().getResource("images/" +filesThatUserLike.get(imageName));
        File fileToInsert = new File(resource.toURI());
        return fileToInsert;
    }

    private boolean doesUserLike(String file, List<String> userLikes) {
        for(String likes : userLikes){
            if(file.contains(likes)){
                return true;
            }
        }
        return false;
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
            log.info("attempt to send email");
            messageBodyPart.setContent(geContentFromTemplate(mail.getModel()), "text/html");


            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            File fileToInsert = getFileToInsert((User)mail.getModel().get("user"));
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
