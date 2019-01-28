package jp.co.rakus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.rakus.domain.LoginUser;
import jp.co.rakus.domain.User;
import jp.co.rakus.service.RegisterUserService;

@Controller
@RequestMapping("/")
@SessionAttributes(types = {User.class})
public class LoginUserController {
	
	
	@RequestMapping("/")
	public String login(Model model ,
						@RequestParam(required = false) String error,
						@AuthenticationPrincipal LoginUser loginUser) {
		
		System.out.println(loginUser);
		System.err.println("login error :" + error);
		
		if(error != null) {
			System.err.println("user:login failed");
			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います");
		}
		
		return "login";
	}
	
}
