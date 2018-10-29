package com.lzh.salarysystem.infrastructure.persistent.converter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthoritiesConverter implements AttributeConverter<Collection<? extends GrantedAuthority>, String>{

	public static final String AUTHORITY_SEPERATOR = ";";
	
	@Override
	public String convertToDatabaseColumn(Collection<? extends GrantedAuthority> attribute) {
		return attribute.stream()
				.map(e -> e.getAuthority())
				.collect(Collectors.joining(AUTHORITY_SEPERATOR));
	}

	@Override
	public Collection<? extends GrantedAuthority> convertToEntityAttribute(String dbData) {
		return dbData == null 
				? Collections.emptyList()
				: Arrays.stream(dbData.split(AUTHORITY_SEPERATOR))
					.flatMap(e -> AuthorityUtils.commaSeparatedStringToAuthorityList(e).stream())
					.collect(Collectors.toList());
	}

}
