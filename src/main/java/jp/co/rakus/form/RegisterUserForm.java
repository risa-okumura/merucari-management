package jp.co.rakus.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ユーザー情報の登録フォーム.
 * 
 * @author risa.okumura
 *
 */
public class RegisterUserForm {
	
	@NotBlank(message="メールアドレスを入力してください")
	@Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$",message="メールアドレスの形式が間違っています")
	private String email;
	@NotBlank(message="パスワードを入力してください")
	private String password;
	
	@Override
	public String toString() {
		return "RegisterUserForm [email=" + email + ", password=" + password + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
