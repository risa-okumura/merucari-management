package jp.co.rakus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.domain.LoginUser;
import jp.co.rakus.form.RegisterUserForm;
import jp.co.rakus.service.RegisterUserService;

@Controller
@Transactional
@RequestMapping("/registerUser")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;
	
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}
	
	
	
	@RequestMapping("/toRegister")
	public String registerForm() {
		return "register";
	}
	
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm registerUserForm,
							BindingResult result,
							Model model,
							@AuthenticationPrincipal LoginUser loginUser) {
		System.out.println(registerUserForm.toString());
		
		if(registerUserService.checkEmail(registerUserForm)) {
			System.out.println("重複しています。");
			result.rejectValue("email", "","すでにメールアドレスが登録されています");
		}
		
		if(result.hasErrors()) {
			return registerForm();
		}
		
		
		registerUserService.save(registerUserForm);
		
		return "redirect:/";
	}
	
	
}
