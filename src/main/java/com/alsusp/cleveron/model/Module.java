package com.alsusp.cleveron.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

@Entity
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	int id;

	@Column
	@Pattern(regexp = "^[A-Za-z0-9\s]{1,50}$")
	String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	Module parentModule;

	@OneToMany(mappedBy = "parentModule")
	List<Module> childModules;

	public Module() {
	}

	public Module(String name) {
		this(0, name, new Module(), new ArrayList<>());
	}

	public Module(int id, String name) {
		this(id, name, new Module(), new ArrayList<>());
	}

	public Module(String name, Module parentModule) {
		this(0, name, parentModule, new ArrayList<>());
	}

	public Module(String name, List<Module> childModules) {
		this(0, name, new Module(), childModules);
	}

	public Module(int id, String name, Module parentModule) {
		this(id, name, parentModule, new ArrayList<>());
	}

	public Module(int id, String name, List<Module> childModules) {
		this(id, name, new Module(), childModules);
	}

	public Module(int id, String name, Module parentModule, List<Module> childModules) {
		this.id = id;
		this.name = name;
		this.parentModule = parentModule;
		this.childModules = childModules;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Module getParentModule() {
		return parentModule;
	}

	public void setParentModule(Module parentModule) {
		this.parentModule = parentModule;
	}

	public List<Module> getChildModules() {
		return childModules;
	}

	public void setChildModules(List<Module> childModules) {
		this.childModules = childModules;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Module module = (Module) obj;
		return id == module.id && (name == module.name || (name != null && name.equals(module.getName())))
				&& (parentModule == module.parentModule
						|| (parentModule != null && parentModule.equals(module.getParentModule())))
				&& (childModules == module.childModules
						|| (childModules != null && childModules.equals(module.getChildModules())));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentModule == null) ? 0 : parentModule.hashCode());
		result = prime * result + ((childModules == null) ? 0 : childModules.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Module ID: {0}, name: {1}", id, name);
	}
}
