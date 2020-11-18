package com.alsusp.cleveron.dao;

import com.alsusp.cleveron.model.Module;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleDao extends JpaRepository<Module, Integer> {

	public Optional<Module> findByName(String name);
}
