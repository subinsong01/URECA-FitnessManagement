package com.ureca.userhouse.controller;
import com.ureca.userhouse.entity.Manager;
import com.ureca.userhouse.entity.Member;
import com.ureca.userhouse.repository.MangerRepository;
import com.ureca.userhouse.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ManagerController {
    
    @Autowired
    private MangerRepository managerRepo;

    @Autowired
    private MemberRepository memberRepo;
    
    // 회원가입 기능
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Map<String, String>> signup(@RequestBody Manager manager) {
        System.out.println("Received signup request for: " + manager.getMid());
        Map<String, String> response = new HashMap<>();
        if (managerRepo.existsById(manager.getMid())) {
            response.put("code", "error");
            response.put("message", "이미 등록된 회원입니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            managerRepo.save(manager);
            response.put("code", "success");
            return ResponseEntity.ok(response);
        }
    }

    // 로그인기능
    @PostMapping("/member/login")
    @ResponseBody
    public Map<String, Object> login(
            @RequestParam("mid") String mid, 
            @RequestParam("mpwd") String mpwd,
            HttpSession session){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "error");
        
        Optional<Manager> mem = managerRepo.findById(mid);
        if(mem.isPresent()) {
            Manager mymember = mem.get();
            if(mymember.getMpwd().equals(mpwd)) {
                result.put("code", "success");
                result.put("message", "로그인완료");
                session.setAttribute("member", mymember); 
            } else {
                result.put("message", "암호틀림");
            }
        } else {
            result.put("message", "없거나 삭제된 아이디입니다");
        }
        System.out.println(result);
        return result;
    }

    // 로그아웃 기능
    @GetMapping("/member/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();
        Object object = session.getAttribute("member");
        String message = "";
        if(object == null) {
            message += "로그인 안된 상태.";
        } else {
            message += "로그인 된 상태.";
        }
     // invalidate()는  브라우저만의 정보를 담은 session 공단을 지우고 다시 만든다.
     // 이로서 session에 저장했던 모든정보를 없애버리니 로그인 안된상태로 바꾸는것이다.
     		session.invalidate();//로그인정보 삭제
        result.put("code", "success");
        result.put("message", message + "로그아웃완료");
        System.out.println(result);
        return result;
    }

    //로그인 확인 기능
    @GetMapping("/member/check_login")
	@ResponseBody
	public Map<String, Object> check_login( HttpSession session ){
		HashMap<String, Object> result = new HashMap<>();
		
		Object object = session.getAttribute("member");
		// 브라우저만의 공간인 session에 "member"키로 저장된게 있으면 로그인한것이고
		// 없으면 로그인 안 한 것이다.
		if(object==null) {
			result.put("code", "error");
			result.put("message", "Not Login");
		}else {
			result.put("code", "ok");
			result.put("message", "On Login");
			Manager mm = (Manager)object;
			mm.setMpwd(null);
			result.put("data", mm); //로그인된 사용자 정보
		}
		
		System.out.println(result);
		return result;
	}
    
    // 회원 등록 기능
    @PostMapping("/member/register")
    @ResponseBody
    public Map<String, String> registerMember(@RequestBody Member member, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        Manager manager = (Manager) session.getAttribute("member");
        if (manager == null) {
            response.put("code", "error");
            response.put("message", "회원을 등록해주세요");
        } else {
            member.setManager(manager);
            memberRepo.save(member);
            response.put("code", "success");
        }
        return response;
    }
    
    // 회원 목록 조회 기능
    @GetMapping("/member/list")
    @ResponseBody
    public List<Member> listMembers() {
        return memberRepo.findAll();
    }
    
    // 회원 삭제 기능
    @DeleteMapping("/member/delete/{id}")
    @ResponseBody
    public Map<String, String> deleteMember(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        Optional<Member> member = memberRepo.findById(id);
        if (member.isPresent()) {
            memberRepo.deleteById(id);
            response.put("message", "회원 삭제 완료");
        } else {
            response.put("message", "회원이 존재하지 않습니다");
        }
        return response;
    }
 
 }
