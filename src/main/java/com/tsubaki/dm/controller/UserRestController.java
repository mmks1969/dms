package com.tsubaki.dm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.xdevapi.Result;
import com.tsubaki.dm.model.User;
import com.tsubaki.dm.service.RestService;

import ch.qos.logback.core.joran.conditional.IfAction;

@RestController
public class UserRestController {
	@Autowired
	RestService service;
	
	// ユーザー全件取得
	@GetMapping("/rest/get")
	public List<User> getUserMany(){
		
		// ユーザー全件取得
		return service.selectMany();
	}
	
	// ユーザー1件取得
	@GetMapping("/rest/get/{id:.+}")
	public User getUserOne(@PathVariable("id")String userId) {
		
		// ユーザー1件取得
		return service.selectOne(userId);
	}
	
	// RequestBody
	@PostMapping("/rest/insert")
	public String postUserOne(@RequestBody User user) {
		
		// ユーザーを1件登録
		boolean result = service.insert(user);
		
		String str = "";
		
		if(result == true) {
			str="{\"result\":\"ok\"}";
		} else {
			str="{\"result\":\"error\"}";
		}
		
		// 結果用の文字列をリターン
		return str;
		
	}
	
	@PutMapping("/rest/update")
	public String putUserOne(@RequestBody User user) {
		
		// ユーザーを1件更新
		boolean result = service.update(user);
		
		String str = "";
		
		if(result == true) {
			str="{\"result\":\"ok\"}";
		} else {
			str="{\"result\":\"error\"}";
		}
		
		// 結果用の文字列をリターン
		return str;

	}
	
	@DeleteMapping("/rest/delete/{id:..+}")
	public String deleteUserOne(@PathVariable("id")String userId) {
		
		// ユーザーを1件削除
		boolean result = service.delete(userId);

		String str = "";
		
		if(result == true) {
			str="{\"result\":\"ok\"}";
		} else {
			str="{\"result\":\"error\"}";
		}
		
		// 結果用の文字列をリターン
		return str;
		
	}
	

}
