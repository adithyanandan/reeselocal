package com.lilly.gss.mdit.reese.internal.controller;

import com.lilly.gss.mdit.reese.internal.config.TokenGenerator;
import com.lilly.gss.mdit.reese.internal.model.Greeting;
import com.lilly.gss.mdit.reese.internal.model.TokenGeneration;
import com.lilly.gss.mdit.reese.internal.service.EmailService;
import com.lilly.gss.mdit.reese.internal.service.TokenServiceImpl;
import com.lilly.gss.mdit.reese.internal.service.UserServiceImpl;
import com.lilly.gss.mdit.reese.internal.util.ReeseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by C232018 on 7/31/2017.
 */
@Controller
public class MailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    @Autowired
    Environment env;


    @GetMapping("/test")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @GetMapping("/mail")
    public String sendMail(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "mailTemplate";
    }

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid TokenGeneration tokenGeneration, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        TokenGeneration tokengen = tokenServiceImpl.findUserByEmail(tokenGeneration.getEmail());

        String tokenPasswordCompare = tokenGeneration.getTempPassword();
        String tokenEmailCompare = tokenGeneration.getEmail();
        String tokenPasswordCompareRepo = null;
        if(tokengen != null){
        tokenPasswordCompareRepo = tokengen.getTempPassword();}

        if ((tokenEmailCompare != null) && (tokengen == null)) {
            bindingResult
                    .rejectValue("email", "error.user.empty",
                            "The email address you entered is not registered with the System ");
        }
        if((tokenPasswordCompare != null ) && (tokenPasswordCompareRepo != null )){
            if(tokenPasswordCompare.equalsIgnoreCase(tokenPasswordCompareRepo)  ){
                     System.out.println("no issue");
            }else{

            bindingResult
                    .rejectValue("tempPassword", "error.password",
                            "The Temporary password doesnot match..Please check and try again ");}
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("tokenValidity");
        } else {

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("tokenGeneration", new TokenGeneration());
            modelAndView.setViewName("Success");

        }
        return modelAndView;
    }


    @PostMapping("/test")
    public ModelAndView greetingSubmit(@Valid @ModelAttribute Greeting greeting, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        Greeting greetUser = userServiceImpl.findUserByEmail(greeting.getEmail());

        if (greetUser != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with this email ");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("greeting");}

        else {


            String token = tokenGenerator.randomToken();
            greeting.setToken(token);
            greeting.setCreationDate(ReeseUtil.getCurrentDateUtc());
            greeting.setValidityDate(ReeseUtil.getCurrentDateUTCByAddingMinutes(10));
            //cal.add(Calendar.MINUTE, 10);
            //greeting.setValidityDate(cal.getTime());


            System.out.println("Generated Date is" + greeting.getCreationDate());


            System.out.println("Generated greeting token is" + greeting.getToken());
            System.out.println("Generated email is" + greeting.getEmail());
            System.out.println("Generated content is" + greeting.getContent());
            System.out.println("Generated Date is" + greeting.getCreationDate());

            userServiceImpl.saveUser(greeting);

            String urlForRegistration = env.getRequiredProperty("register.url");

            System.out.println("URL is " + urlForRegistration);

            StringBuilder sb = new StringBuilder();
            sb.append(urlForRegistration);
            sb.append("/");
            sb.append(greeting.getEmail());
            sb.append("/");
            sb.append(greeting.getToken());
            String registrationLink = sb.toString();
            System.out.println("Registration Link Checking" + registrationLink);
            TokenGeneration tokenGeneration = new TokenGeneration();
            tokenGeneration.setRegistrationLink(registrationLink);
            tokenGeneration.setEmail(greeting.getEmail());


            emailService.sendVerificationEmail(tokenGeneration);

            String tempPass = tokenGenerator.tempPassword();
            System.out.println("Temporary Password" + tempPass);
            tokenGeneration.setTempPassword(tempPass);
            tokenServiceImpl.saveTokenURL(tokenGeneration);
            emailService.sendTempPassword(tokenGeneration);
            modelAndView.setViewName("results");
        }

        return modelAndView;
    }






    @RequestMapping(value = "/test/{email}/{token}" , method = RequestMethod.GET)
    public ModelAndView showtokenList(
            HttpServletRequest request,
            HttpServletResponse response,@PathVariable("email") String email,
            @PathVariable("token") String token, @ModelAttribute("tokenGeneration") TokenGeneration tokenGeneration,
            BindingResult result, ModelMap model) throws Exception {

        HttpSession session = request.getSession();

        ModelAndView modelAndView = new ModelAndView("redirect:/tokenValidCheck");



        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calend = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(calend.getTime());
        String formatted = format1.format(calend.getTime());
        System.out.println(formatted);

        TokenGeneration tokengen = tokenServiceImpl.findUserByEmail(email);

        System.out.println("Comparison Date: " + format1.parse(formatted));
        tokengen.setAccessDate(format1.parse(formatted));
        tokenServiceImpl.saveTokenURL(tokengen);



        session.setAttribute("tokenGenerationSession", tokenGeneration);

        model.addAttribute("tokenGeneration", tokenGeneration);
        modelAndView.addObject("tokenGenerationSession", tokenGeneration);

        return modelAndView;

    }


    @RequestMapping(value = "/tokenValidCheck", method = RequestMethod.GET)
    public ModelAndView tokenValidity(
            HttpServletRequest request,
            HttpServletResponse response,  @ModelAttribute("tokenGeneration") TokenGeneration tokenGeneration,
            BindingResult result, ModelMap model) throws Exception {

        ModelAndView modelAndView = new ModelAndView("tokenValidity");

        HttpSession session = request.getSession();
        tokenGeneration=(TokenGeneration) session.getAttribute("tokenGenerationSession");




        Greeting greetUser = userServiceImpl.findUserByEmail(tokenGeneration.getEmail());
        TokenGeneration tokengen = tokenServiceImpl.findUserByEmail(tokenGeneration.getEmail());
        String dbTempPassword=tokengen.getTempPassword();
        String userTempPassword =tokenGeneration.getTempPassword();


        System.out.println("Creation Date: " + greetUser.getCreationDate());
        System.out.println("Validity Date: " + greetUser.getValidityDate());
        System.out.println("Access Date: " + tokengen.getAccessDate());

        int comparison = greetUser.getValidityDate().compareTo(tokengen.getAccessDate());

        System.out.println("Comaprison Date: " + comparison);


        if (comparison == 1) {
            tokengen.setStatus("ACTIVE");
            tokenServiceImpl.saveTokenURL(tokengen);
            System.out.println("ValidTOKENURL ");
            modelAndView = new ModelAndView("tokenValidity");
        } else {
            tokengen.setStatus("EXPIRED");
            tokenServiceImpl.saveTokenURL(tokengen);
            System.out.println("InValidTOKENURL ");
            modelAndView = new ModelAndView("tokenExpire");
        }





        return modelAndView;


    }






}
