package common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import member.model.vo.Member;

//singletone : 단 하나의 객체만을 생성할려고 할때

public class UserListSingletone {
	
	private static UserListSingletone instance;
	private static Set<String> userList;
	
	private UserListSingletone() {
		userList = new HashSet<>();
	}
	
	public static UserListSingletone getInstance() {
		if(instance==null) {
			instance = new UserListSingletone();
		}
		
		return instance;
	}
	
	public Set<String> getUserList(){
		return userList;
	}

}
