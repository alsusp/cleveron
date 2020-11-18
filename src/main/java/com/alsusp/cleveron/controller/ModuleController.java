package com.alsusp.cleveron.controller;

import com.alsusp.cleveron.exception.NotUniqueNameException;
import com.alsusp.cleveron.exception.NotValidDepthException;
import com.alsusp.cleveron.model.Module;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alsusp.cleveron.service.ModuleService;

@Controller
public class ModuleController {

	private ModuleService moduleService;

	public ModuleController(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@GetMapping("/modules")
	public String getModules(Model model) {
		model.addAttribute("modules", moduleService.findAll());
		return "modules";
	}

	@GetMapping(value = "/modules", params = "id")
	public String getModule(@RequestParam int id, Model model) {
		model.addAttribute("modules", moduleService.findOne(id));
		return "modules";
	}

	@GetMapping("/modules/new")
	public String getNewModuleForm(Model model) {
		model.addAttribute("module", new Module());
		return "newModuleForm";
	}

	@PostMapping(value = "/modules/new")
	public String saveModule(@ModelAttribute("module") Module module)
			throws NotUniqueNameException, NotValidDepthException {
		if (module.getParentModule().getId() != 0) {
			module.setParentModule(moduleService.findOne(module.getParentModule().getId()));
		} else {
			module.setParentModule(null);
		}
		moduleService.save(module);
		return "redirect:/modules";
	}

	@GetMapping("/modules/edit")
	public String getModuleEditForm(@RequestParam int id, Model model) {
		model.addAttribute("module", moduleService.findOne(id));
		return "moduleEditForm";
	}

	@PostMapping(value = "/modules/edit")
	public String saveEditedModule(@ModelAttribute("module") Module module)
			throws NotUniqueNameException, NotValidDepthException {
		if (module.getParentModule().getId() != 0) {
			module.setParentModule(moduleService.findOne(module.getParentModule().getId()));
		} else {
			module.setParentModule(null);
		}
		module.setChildModules(moduleService.findOne(module.getId()).getChildModules());
		moduleService.save(module);
		return "redirect:/modules";
	}

	@GetMapping("/modules/delete")
	public String delete(@RequestParam int id) {
		moduleService.delete(id);
		return "redirect:/modules";
	}
}
