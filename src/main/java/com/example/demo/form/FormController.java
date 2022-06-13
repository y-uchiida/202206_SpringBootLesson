package com.example.demo.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FormController {

	public FormController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/form")
	public String form(Model model)
	{
		return ("form/input");
	}

	@RequestMapping("/confirm")
	public String confirm(Model model, Form form)
	{
		return ("form/confirm");
	}
}
