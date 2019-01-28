package jp.co.rakus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * このメソッドをオーバーライドすることで
	 * 特定のリクエストに対して「セキュリティ設定」を
	 * 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無視する。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure (WebSecurity web) throws Exception{
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
		web.ignoring().antMatchers(
							"/images/**",
							"/css/**",
							"/javascript/**",
							"webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	// 認可の設定
	http.antMatcher("/**")
		.authorizeRequests()
		.antMatchers("/","/login","/registerUser/**").permitAll() // indexは全ユーザーアクセス許可
		.antMatchers("/**").hasRole("USER")
		.anyRequest().authenticated(); // それ以外は全て認証無しの場合アクセス不許可
	
	http.formLogin() 							// ログインに関する設定
		.loginPage("/") 					// ログイン画面に遷移させるパス（ログイン認証が必要なパスを指定してかつログインされていないとこのパスに遷移される）
		.loginProcessingUrl("/login")		 // ログインボタンを押した際に遷移させるパス（ここに遷移させれば自動的にログインが行われる）
		.failureUrl("/?error=true") 		// ログイン失敗に遷移させるパス
		.defaultSuccessUrl("/viewItemList/list", true) // 第1引数：デフォルトでログイン成功時に遷移させるパス
										// 第2引数： true :認証後常に第1引数のパスに遷移
										// false: 認証されてなくて一度ログイン画面に飛ばされてもログインしたら指定したURLに遷移
		.usernameParameter("email") 		// 認証時に使用するユーザー名のリクエストパラメーター名（今回はメールアドレス）
		.passwordParameter("password"); 	// 認証時に使用するパスワードのリクエストパラメータ名
	
	http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
		.logoutSuccessUrl("/viewItemList/list");
	
	http.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.ignoringAntMatchers("/login");
	
	}
	
	/**
	 * 「認証」に関する設定. 認証ユーザーを取得する「UserDetailsService」の設定や
	 * パスワード照合時に使う「PasswordEncoder」の設定
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * bcryptアルゴリズムで暗号化する実装を返します これをしていすることでパスワードの暗号化やマッチ確認する際に
	 * 
	 * @Autowired private PasswordEncoder passwordEncoder; と記載するとDIされるようになります。
	 * @return bcrypt アルゴリズムで暗号化する実装オブジェクト
	 * 
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
