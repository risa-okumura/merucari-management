package jp.co.rakus.domain;

/**
 * ユーザー情報を表すドメイン.
 * 
 * @author risa.okumura
 *
 */
public class User {

	/**	ユーザーID */
	private Integer id;
	/**	ユーザーのメールアドレス*/
	private String email;
	/**	ユーザーのパスワード */
	private String password;
	/**	ユーザーの権限 */
	private Integer authority;

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", authority=" + authority + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

}
