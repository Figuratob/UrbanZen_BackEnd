package ee.urbanzen.backoffice.service;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.User;

import ee.urbanzen.backoffice.domain.Entry;
import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String LESSON = "lesson";

    private static final String LESSON_NAME = "lessonName";

    private static final String LESSON_DATE = "lessonDate";

    private static final String ENTRIES = "entries";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties,
                       JavaMailSender javaMailSender,
                       MessageSource messageSource,
                       SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplateLessonCancellation(User user, Lesson lesson, String templateName,
                                                        String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(LESSON, lesson);

        if (user.getLangKey().equals("en")) {
            context.setVariable(LESSON_NAME, lesson.getNameEng());
        }
        else if (user.getLangKey().equals("ru")) {
            context.setVariable(LESSON_NAME, lesson.getNameRus());
        }
        else {
            context.setVariable(LESSON_NAME, lesson.getName());
        }

        java.util.Date lessonDate = Date.from(lesson.getStartDate());
        context.setVariable(LESSON_DATE, lessonDate);

        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
    private void sendEmailFromTemplateLessonTemplateCancellation(
        User user, List<Entry> entries, String templateName, String titleKey) {
        try {
            Locale locale = Locale.forLanguageTag(user.getLangKey());
            Context context = new Context(locale);
            context.setVariable(USER, user);
            context.setVariable(ENTRIES, entries);
            context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
            String content = templateEngine.process(templateName, context);
            String subject = messageSource.getMessage(titleKey, null, locale);
            sendEmail(user.getEmail(), subject, content, false, true);
        } catch (Exception ignored) {
            log.warn("Exception while sending email ", ignored);
        }
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        try {
            sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
        } catch (Exception ignored) {
            log.warn("Exception while sending email ", ignored);
        }
    }

    @Async
    public void sendLessonCancellationEmail(User user, Lesson lesson) {
        log.debug(
            "Sending info email to '{}' about lesson '{}','{}','{}','{}','{}', cancellation ",
            user.getEmail(),
            lesson.getName(),
            lesson.getNameEng(),
            lesson.getNameRus(),
            lesson.getStartDate(),
            lesson.getTeacher());
        try {
            sendEmailFromTemplateLessonCancellation(
                user,
                lesson,
                "mail/lessonCancellationEmail",
                "email.lessonCancellation.title");
        } catch (Exception ignored) {
            log.warn("Exception while sending email ", ignored);
        }
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug(
            "Sending creation email to '{}'",
            user.getEmail());
        try {
            sendEmailFromTemplate(
                user,
                "mail/creationEmail",
                "email.activation.title");
        } catch (Exception ignored) {
            log.warn("Exception while sending email ", ignored);
        }
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug(
            "Sending password reset email to '{}'",
            user.getEmail());
        try {
            sendEmailFromTemplate(
                user,
                "mail/passwordResetEmail",
                "email.reset.title");
        } catch (Exception ignored) {
            log.warn("Exception while sending email ", ignored);
        }
    }

    public void sendLessonTemplateCancellationEmail(User user, List<Entry> entries) {
        log.debug("Sending info email to '{}' about lessons '{}' cancellation ", user.getFirstName(), entries);
        try {
            sendEmailFromTemplateLessonTemplateCancellation(
                user,
                entries,
                "mail/lessonTemplateCancellationEmail",
                "email.lessonTemplateCancellation.title");
        } catch (Exception ignored) {
            log.warn("Exception while sending email ", ignored);
        }
    }
}
