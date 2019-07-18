package common.wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncryptWrapper extends HttpServletRequestWrapper {

	/**
	 * 부모타입에 기본생성자가 없기 때문에 파라미터 생성자 구현 강제함
	 * @param request : wrapping 하고자 하는 객체
	 */
	public EncryptWrapper(HttpServletRequest request) {
		super(request);
	}
	
	/**
	 * getParameter 메소드 중에 
	 * password에 대해서만 암호화 기능을 수행
	 */
	@Override
	public String getParameter(String key) {
		String value = "";
		if(key != null && (key.equals("password")) || 
				(key.equals("passwordOld")) ||
				(key.equals("passwordNew"))) {
			System.out.println("암호화 전: "+super.getParameter(key)); //key="password"
			
			value = getSha512(super.getParameter(key)); //암호화 방식 중 Sha512 방식 사용할 것
			System.out.println("암호화 후: "+value);
		}
		else {
			value = super.getParameter(key);
			System.out.println(value);
		}
		
		return value;
	}

	/**
	 * sha512 해시함수를 사용하여 암호화
	 * 단방향 (해독이 불가)
	 * 아웃풋으로 인풋을 추정하지 못함. 역추적이 불가능
	 * @param parameter
	 * @return
	 * */
	private String getSha512(String password) {
		String encPwd = null;
		
		//암호화객체 생성(sha-512)
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//전달받은 문자열 password를 byte[]로 변환
		//인코딩이 뭘로 되었는지 적어 주기
//		byte[] bytes = password.getBytes("utf-8");
		byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
		
		//md객체에 byte배열을 전달해서 갱신
		md.update(bytes);
		
		//java.util.Base64 인코더를 이용해서 암호화된 바이트 배열을 인코딩
		//문자열로 리턴
		encPwd = Base64.getEncoder().encodeToString(md.digest());
				//바이트 배열을 문자열로 바꿔 줌
				//digest() 이용해서 바이트배열 리턴 가능
		
		return encPwd;
	}

}