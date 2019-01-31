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

/**
 * ユーザーを登録するコントローラ.
 * 
 * @author risa.okumura
 *
 */
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
	
	/**
	 * ユーザー登録画面を表示する.
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/toRegister")
	public String registerForm() {
		return "register";
	}
	
	/**
	 * ユーザー登録を行う.
	 * 
	 * @param registerUserForm
	 * @param result
	 * @param model
	 * @param loginUser
	 * @return ログイン画面.
	 */
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm registerUserForm,
							BindingResult result,
							Model model,
							@AuthenticationPrincipal LoginUser loginUser) {
		
		//メールアドレスがすでに登録されている場合、エラーをひょうじさせる.
		if(registerUserService.checkEmail(registerUserForm)) {
			System.out.println("重複しています。");
			result.rejectValue("email", "","すでにメールアドレスが登録されています");
		}
		
		//入力チェック用.
		if(result.hasErrors()) {
			return registerForm();
		}
		
		registerUserService.save(registerUserForm);
		
		return "redirect:/";
	}
	
	
}
