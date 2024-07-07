package com.studyprogramming.controller.admin;

import com.studyprogramming.constant.ConstantController;
import com.studyprogramming.constant.ConstantSecurity;
import com.studyprogramming.payload.UserLoginPayload;
import com.studyprogramming.utils.ControllerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
public class AdAuthController{
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @GetMapping(ConstantController.ROOT_PATH_ADMIN_LOGIN)
    public String login(Model model){
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserLoginPayload());
        }
        return "admin/auth/login";
    }

    @PostMapping(ConstantController.ROOT_PATH_ADMIN_LOGIN)
    public String login(@Valid @ModelAttribute("user") UserLoginPayload user, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return ControllerUtil.redirectTo(ConstantController.ROOT_PATH_ADMIN_LOGIN);
        }

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authenticationProvider.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(ConstantSecurity.SECURITY_CONTEXT_FOR_SESSION, securityContext);

        return ConstantController.PATH_ADMIN_DASHBOARD;
    }


}
