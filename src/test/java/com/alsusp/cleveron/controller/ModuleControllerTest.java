package com.alsusp.cleveron.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alsusp.cleveron.config.TestConfig;
import com.alsusp.cleveron.model.Module;
import com.alsusp.cleveron.service.ModuleService;

@ExtendWith(MockitoExtension.class)
class ModuleControllerTest {

	@Mock
	private ModuleService moduleService;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		InternalResourceViewResolver viewResolver = TestConfig.setupViewResolver();
		mockMvc = MockMvcBuilders.standaloneSetup(new ModuleController(moduleService))
				.setViewResolvers(viewResolver).build();
	}

	@Test
	void whenGetModules_thenCorrectModelAndViewInResponse() throws Exception {
		List<Module> expected = new ArrayList<>();
		Module module1 = new Module(1, "Module1");
		Module module2 = new Module(2, "Module2");
		expected.add(module1);
		expected.add(module2);
		when(moduleService.findAll()).thenReturn(expected);

		mockMvc.perform(get("/modules"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(model().attribute("modules", expected))
		.andExpect(view().name("modules"));
	}

	@Test
	void givenId_whenGetModule_thenCorrectModelAndViewInResponse() throws Exception {
		Module expected = new Module(1, "Module1");
		when(moduleService.findOne(expected.getId())).thenReturn(expected);

		mockMvc.perform(get("/modules").param("id", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(model().attribute("modules", expected))
		.andExpect(view().name("modules"));
	}

	@Test
	void givenAppService_whenSave_thenCorrectModelAndViewInResponse() throws Exception {
		Module module = new Module(1, "Module1");
		Module expected = new Module("newModule");
		expected.setParentModule(module);
		when(moduleService.findOne(1)).thenReturn(module);

		mockMvc.perform(post("/modules/new").flashAttr("module", expected))
		.andDo(print())
		.andExpect(status().is(302))
		.andExpect(view().name("redirect:/modules"));

		verify(moduleService).save(expected);
	}

	@Test
	void givenModule_whenUpdate_thenCorrectModelAndViewInResponse() throws Exception {
		Module module = new Module(1, "Module1");
		Module expected = new Module(2, "updatedModule");
		expected.setParentModule(module);
		when(moduleService.findOne(1)).thenReturn(module);

		mockMvc.perform(post("/modules/edit").flashAttr("module", expected))
		.andDo(print())
		.andExpect(status().is(302))
		.andExpect(view().name("redirect:/modules"));

		verify(moduleService).save(expected);
	}

	@Test
	void givenServiceCode_whenDelete_thenCorrectModelAndViewInResponse() throws Exception {
		mockMvc.perform(get("/modules/delete").param("id", "1"))
		.andExpect(status().is(302));

		verify(moduleService).delete(1);
	}
}
