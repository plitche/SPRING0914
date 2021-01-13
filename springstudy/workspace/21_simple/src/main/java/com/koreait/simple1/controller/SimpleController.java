package com.koreait.simple1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreait.simple1.command.SimpleCommand;
import com.koreait.simple1.command.SimpleInsertCommand;
import com.koreait.simple1.command.SimpleListCommand;


@Controller
public class SimpleController {

	
	// 21_simple 프로젝트는 command들을 new로 생성하는 연습입니다.
	
	
	// field
	private SimpleCommand command;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="simpleList.do")
	public String simpleList(Model model) {
		// DB에서 list를 가져와서 simpleListPage.jsp로 이동시킵니다.
		// simpleListPage.jsp로 보낼 데이터인 list는 Model에 저장합니다.
		// Model에 저장된 데이터를 simpleListPage.jsp으로 보내려면 forward합니다.
		/*
			SimpleListCommand가 없다면 아래와 같이 작업할 수 있겠습니다.
			하지만, 이렇게 하시면 안 됩니다.
			SimpleDao simpleDao = SimpleDao.getInstance();
			model.addAttribute("list", simpleDao.simpleList());
			return "simple/simpleListPage";
		*/
		
		command = new SimpleListCommand();  // 개발자 직접 생성
		command.execute(model);
		return "simple/simpleListPage";  // simple 폴더 아래 simpleListPage.jsp로 forward합니다.
		
	}
	
	@RequestMapping(value="simpleInsertPage.do")
	public String simpleInsertPage() {
		return "simple/simpleInsertPage";  // simple 폴더 아래 simpleInsertPage.jsp로 forward합니다.
	}
	
	@RequestMapping(value="simpleInsert.do")
	public String simpleInsert(HttpServletRequest request, Model model) {
		
		// SimpleInsertCommand 가 삽입을 담당합니다.
		// 모든 command들은 model이 필요합니다.
		
		// SimpleInsertCommand에게 request를 전달하려면?
		// 모든 command들은 실행할 때 model만 전달 받습니다.
		// 따라서, model에 request를 저장해 둡니다. 그리고 model을 전달합니다.
		model.addAttribute("request", request);
		
		command = new SimpleInsertCommand();  // 개발자가 직접 생성
		command.execute(model);
		
		// return "simple/simpleListPage";  // insert 후에는 forward하면 안 됩니다.
		return "redirect:simpleList.do";  // insert 후에는 redirect 합니다.
		
	}
	
	
	
	
}