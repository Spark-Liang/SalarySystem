package com.lzh.salarysystem.repository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InheritedClassRepository<T,K extends Serializable> extends JpaRepository<T, Serializable>{
	
	@SuppressWarnings("unchecked")
	default <E  extends T> E findOne(K id,Class<E> targetClass) {
		T result = findOne(id);
		if(result != null && targetClass.isInstance(result)) {
			return (E)result;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	default <E extends T> List<E> findAll(Iterable<Serializable> ids,Class<E> targetClass) {
		List<T> result = findAll(ids);
		return result.stream()
				.filter(e -> e != null && targetClass.isInstance(e))
				.map(e -> (E)e)
				.collect(Collectors.toList());
	}
	
}
