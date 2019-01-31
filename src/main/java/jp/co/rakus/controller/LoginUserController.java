package jp.co.rakus.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.rakus.domain.LoginUser;
import jp.co.rakus.domain.User;

/**
 * ユーザー情報を元にログイン処理をするコントローラー
 * @author risa.okumura
 *
 */
@Controller
@RequestMapping("/")
@SessionAttributes(types = {User.class})
public class LoginUserController {
	
	
	/**
	 * ログイン画面を表示する.
	 * @param model
	 * @param error
	 * @param loginUser
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String login(Model model ,
						@RequestParam(required = false) String error,
						@AuthenticationPrincipal LoginUser loginUser) {
		
		if(error != null) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います");
		}
		
		return "login";
	}
	
}
