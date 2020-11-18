package com.alsusp.cleveron.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import com.alsusp.cleveron.model.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alsusp.cleveron.dao.ModuleDao;
import com.alsusp.cleveron.exception.NotFoundException;
import com.alsusp.cleveron.exception.NotUniqueNameException;
import com.alsusp.cleveron.exception.NotValidDepthException;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

	@Mock
	private ModuleDao moduleDao;

	@InjectMocks
	private ModuleService moduleService;

	@Test
	void whenFindAll_thenAllExistingModulesFound() {
		List<Module> expected = new ArrayList<>();
		Module module1 = new Module(1, "Module1");
		Module module2 = new Module(2, "Module2");
		expected.add(module1);
		expected.add(module2);
		when(moduleDao.findAll()).thenReturn(expected);
		
		List<Module> actual = moduleService.findAll();

		assertEquals(expected, actual);
		verify(moduleDao).findAll();
	}

	@Test
	void givenExistingId_whenFindOne_thenModuleFound() {
		Module expected = new Module(1, "Module1");
		Optional<Module> module = Optional.of(expected);
		when(moduleDao.findById(1)).thenReturn(module);

		Module actual = moduleService.findOne(1);

		assertEquals(expected, actual);
		verify(moduleDao).findById(1);
	}
	
	@Test
	void givenNotExistingId_whenFindOne_thenNotFoundException() {
		assertThrows(NotFoundException.class, () -> moduleService.findOne(1));
	}
	
	@Test
	void givenModule_whenSave_thenModuleWasSaved() throws NotUniqueNameException, NotValidDepthException {
		Module module = new Module("Module1");

		moduleService.save(module);

		verify(moduleDao).save(module);
	}
	
	@Test
	void givenModule_whenSave_thenModuleWasUpdated() throws NotUniqueNameException, NotValidDepthException {
		Module module = new Module(1, "Module1");

		moduleService.save(module);

		verify(moduleDao).save(module);
	}
	
	@Test
	void givenModuleWithExistingName_whenSave_thenNotUniqueName() throws NotUniqueNameException, NotValidDepthException {
		Module module = new Module(1, "Module");
		Optional<Module> ExistingModule = Optional.of(module);
		Module newModule = new Module("Module");
		when(moduleDao.findByName("Module")).thenReturn(ExistingModule);
		
		assertThrows(NotUniqueNameException.class, () -> moduleService.save(newModule));

		verify(moduleDao, never()).save(newModule);
	}
	
	@Test
	void givenModule_whenSave_thenNotValidDepthException() throws NotUniqueNameException, NotValidDepthException {
		Module module1 = new Module(1, "Module1");
		module1.setParentModule(null);
		Module module2 = new Module(2, "Module2", module1);
		Module module3 = new Module(3, "Module3", module2);
		Module module4 = new Module("Module4", module3);
		
		assertThrows(NotValidDepthException.class, () -> moduleService.save(module4));

		verify(moduleDao, never()).save(module4);
	}
	
	@Test
	void givenModuleWithChild_whenSave_thenNotValidDepthException() throws NotUniqueNameException, NotValidDepthException {
		Module module1 = new Module(1, "Module1");
		module1.setParentModule(null);
		Module module2 = new Module(2, "Module2", module1);
		Module module3 = new Module(3, "Module3", module2);
		Module module4 = new Module(4, "Module4", module3);
		module3.setChildModules(List.of(module4));
		
		assertThrows(NotValidDepthException.class, () -> moduleService.save(module3));

		verify(moduleDao, never()).save(module3);
	}
	
	@Test
	void givenModuleWith2LevelsOfChilds_whenSave_thenNotValidDepthException() throws NotUniqueNameException, NotValidDepthException {
		Module module1 = new Module(1, "Module1");
		module1.setParentModule(null);
		Module module2 = new Module(2, "Module2", module1);
		Module module3 = new Module(3, "Module3", module2);
		module2.setChildModules(List.of(module3));
		Module module4 = new Module(4, "Module4", module3);
		module3.setChildModules(List.of(module4));
		
		assertThrows(NotValidDepthException.class, () -> moduleService.save(module2));

		verify(moduleDao, never()).save(module2);
	}
	
	@Test
	void givenModuleWith3LevelsOfChilds_whenSave_thenNotValidDepthException() throws NotUniqueNameException, NotValidDepthException {
		Module module1 = new Module(1, "Module1");
		Module module2 = new Module(2, "Module2", module1);
		module1.setChildModules(List.of(module2));
		Module module3 = new Module(3, "Module3", module2);
		module2.setChildModules(List.of(module3));
		Module module4 = new Module(4, "Module4", module3);
		module3.setChildModules(List.of(module4));
		
		assertThrows(NotValidDepthException.class, () -> moduleService.save(module2));

		verify(moduleDao, never()).save(module2);
	}
	
	@Test
	void givenModule_whenDelete_thenModuleWasDeleted() {
		Module expected = new Module(1, "Module");
		Optional<Module> module = Optional.of(expected);
		when(moduleDao.findById(1)).thenReturn(module);

		moduleService.delete(1);

		verify(moduleDao).delete(expected);
	}
	
	@Test
	void givenName_whenFindByName_thenModuleFound() {
		Module expected = new Module(1, "Module");
		Optional<Module> module = Optional.of(expected);
		when(moduleDao.findByName("Module")).thenReturn(module);

		Module actual = moduleService.findByName("Module");

		assertEquals(expected, actual);
		verify(moduleDao).findByName("Module");
	}
}
