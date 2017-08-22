
package com.lilly.gss.mdit.reese.internal.service;

import com.lilly.gss.mdit.reese.internal.model.Greeting;
import com.lilly.gss.mdit.reese.internal.model.TokenGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;




/**
 * Created by C232018 on 8/3/2017.
 */

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

 @Autowired
        public MailContentBuilder(TemplateEngine templateEngine) {
            this.templateEngine = templateEngine;
        }



    public String build(TokenGeneration tokenGeneration) {

        Context context = new Context();
        context.setVariable("message", tokenGeneration);
        return templateEngine.process("mailTemplate", context);
    }

    public String tempPassGenerator(TokenGeneration tokenGeneration) {

        Context context = new Context();
        context.setVariable("message", tokenGeneration);
        return templateEngine.process("tempPassword", context);
    }


}




