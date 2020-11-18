package com.alsusp.cleveron.service;

import com.alsusp.cleveron.model.Module;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alsusp.cleveron.dao.ModuleDao;
import com.alsusp.cleveron.exception.NotFoundException;
import com.alsusp.cleveron.exception.NotUniqueNameException;
import com.alsusp.cleveron.exception.NotValidDepthException;

@Service
public class ModuleService {

	private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);

	private ModuleDao moduleDao;

	ModuleService(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public List<Module> findAll() {
		logger.info("Find all modules");
		return moduleDao.findAll();
	}

	public Module findOne(int id) {
		logger.info("Find module by ID {}", id);
		return moduleDao.findById(id).orElseThrow(() -> new NotFoundException("Module not found by ID"));
	}

	public void save(Module module) throws NotUniqueNameException, NotValidDepthException {
		logger.info("Save module");
		validateDepth(module);
		validateNameUnique(module);
		module.setName(StringUtil.capitalize(module.getName()));
		moduleDao.save(module);
	}

	public void delete(int id) {
		logger.info("Delete module by ID {}", id);
		moduleDao.delete(findOne(id));
	}

	public Module findByName(String name) {
		logger.info("Find module by name {}", name);
		return moduleDao.findByName(StringUtil.capitalize(name))
				.orElseThrow(() -> new NotFoundException("Module not found by name"));
	}

	private void validateNameUnique(Module module) throws NotUniqueNameException {
		Optional<Module> existingModule = moduleDao.findByName(module.getName());
		if (existingModule.isPresent() && existingModule.get().getId() != module.getId()) {
			throw new NotUniqueNameException("Module with name " + module.getName() + " already exists");
		}
	}
	
	private void validateDepth(Module module) throws NotValidDepthException {
		if (module.getParentModule() != null && module.getParentModule().getParentModule() != null && module.getParentModule().getParentModule().getParentModule() != null
				|| module.getChildModules() != null && !module.getChildModules().isEmpty() && module.getParentModule() != null && module.getParentModule().getParentModule() != null
				|| module.getChildModules() != null && !module.getChildModules().isEmpty() && module.getChildModules().stream().anyMatch(child -> child.getChildModules() != null && !child.getChildModules().isEmpty()) && module.getParentModule() != null) {
			throw new NotValidDepthException("Not valid depth! Max 3 levels");
		}
	}
}
