package com.lzh.salarysystem.domain.entity;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.dto.form.UserDetailForm;
import com.lzh.salarysystem.exception.BusinessException;
import com.lzh.salarysystem.exception.ErrorCodeConstants;
import com.lzh.salarysystem.infrastructure.persistent.po.entity.UserDetailPO;
import com.lzh.salarysystem.repository.UserDetailEntityRepository;

public class UserDetailEntity {
	
	public static class UserDetailEntityPOExtractor{
		
		private UserDetailPO po;
		
		private UserDetailEntityPOExtractor() {}

		public UserDetailPO getPo() {
			return po;
		}

		public void setPo(UserDetailPO po) {
			this.po = po;
		}
	}
	
	@Component
	public static class UserDetailEntityContext{
		
		@Autowired
		private UserDetailEntityRepository userDetailEntityRepository;
		
	}
	
	private final UserDetailEntityContext context;
	
	private final UserDetailPO userDetailPO;

	public UserDetailEntity(UserDetailEntityContext context, UserDetailPO userDetailPO) {
		super();
		this.context = context;
		this.userDetailPO = userDetailPO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userDetailPO == null) ? 0 : userDetailPO.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserDetailEntity))
			return false;
		UserDetailEntity other = (UserDetailEntity) obj;
		if (userDetailPO == null) {
			if (other.userDetailPO != null)
				return false;
		} else if (!userDetailPO.equals(other.userDetailPO))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDetailEntity [");
		if (context != null)
			builder.append("context=").append(context).append(", ");
		if (userDetailPO != null)
			builder.append("userDetailPO=").append(userDetailPO);
		builder.append("]");
		return builder.toString();
	}
	
	
	public void save() {
		if(userDetailPO.getUsername() == null) {
			throw new BusinessException(ErrorCodeConstants.USERNAME_ISNULL);
		}
		String username = userDetailPO.getUsername();
		if(checkUsernameDuplication(username)) {
			throw new BusinessException(ErrorCodeConstants.USERNAME_IS_DUPLICATED);
		}
		if(userDetailPO.getPassword() == null) {
			throw new BusinessException(ErrorCodeConstants.PASSWORD_ISNULL);
		}
		UserDetailEntityPOExtractor extractor = new UserDetailEntityPOExtractor();
		context.userDetailEntityRepository.save(this, extractor);
	}

	public boolean checkUsernameDuplication(String username) {
		return context.userDetailEntityRepository.isUsernameDuplicated(username);
	}

	public void merge(UserDetailForm form) {
		Map<String, Object> sourceProperties = new HashMap<>();
		BeanUtils.copyProperties(form, sourceProperties);
		Map<String, Object> notNullProperties 
			= sourceProperties.entrySet().stream()
				.filter(entry -> entry.getValue() != null)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
		BeanUtils.copyProperties(notNullProperties, userDetailPO);
	}
}
