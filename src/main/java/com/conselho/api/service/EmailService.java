package com.conselho.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String to, String resetLink) throws MessagingException {
        String htmlContent = "<!DOCTYPE html>"
                + "<html lang=\"pt-br\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Recuperação de Senha</title>"
                + "<style type=\"text/css\">"
                + "  body { margin: 0; padding: 0; background-color: #f4f4f4; color: #333; font-family: 'Noto Sans', sans-serif; }"
                + "  .email-container { max-width: 500px; background-color: white; border-radius: 12px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); margin: 40px auto; }" /* Margem Vertical (40px) para Simular Centralização Vertical em E-mails */
                + "  .email-content { padding: 30px; }"
                + "  .email-header h1 { font-size: 28px; color: #333; font-family: 'Noto Sans', sans-serif; text-align: center; margin-bottom: 20px; }"
                + "  .email-body p { font-size: 16px; line-height: 1.6; color: #555; }"
                + "  .button { display: block; width: 100%; background-color: #007BFF; color: #ffffff !important; text-align: center; padding: 12px; font-size: 18px; text-decoration: none; border-radius: 6px; margin-top: 20px; margin-bottom: 20px; font-weight: bold; }"
                + "  .footer { text-align: center; font-size: 14px; color: #777; padding-top: 20px; }"
                + "  /* Responsividade: Reduz o container em telas pequenas */"
                + "  @media (max-width: 600px) {"
                + "    .email-container { width: 90% !important; margin: 20px auto !important; }" /* Aumenta a margem em mobile */
                + "    .email-content { padding: 20px !important; }"
                + "    .email-header h1 { font-size: 24px !important; }"
                + "    .button { font-size: 16px !important; padding: 10px !important; }"
                + "  }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "  <div class=\"email-container\">"
                + "    <div class=\"email-content\">"
                + "      <div class=\"email-header\">"
                + "        <h1>Recuperação de Senha</h1>"
                + "      </div>"
                + "      <div class=\"email-body\">"
                + "        <p>Olá,</p>"
                + "        <p>Você solicitou a redefinição da sua senha. Para prosseguir, clique no link abaixo:</p>"
                + "        <a href=\"" + resetLink + "\" class=\"button\" style=\"color: #ffffff;\">Redefinir Senha</a>"
                + "        <p>Se você não solicitou a redefinição da senha, ignore este e-mail.</p>"
                + "        <p>Atenciosamente,<br>Equipe de Suporte</p>"
                + "      </div>"
                + "      <div class=\"footer\">"
                + "        <p>Se você tiver alguma dúvida, entre em contato com o nosso suporte.</p>"
                + "      </div>"
                + "    </div>"
                + "  </div>"
                + "</body>"
                + "</html>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(to);
            helper.setSubject("Redefinição de Senha");
            helper.setText(htmlContent, true);
            helper.setFrom("portaldoconselhosenai@gmail.com");

            javaMailSender.send(message);

        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao enviar o e-mail de redefinição de senha.", e);
        }
    }
}