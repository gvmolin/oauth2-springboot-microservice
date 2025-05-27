package com.oauth2proj.models;

import com.oauth2proj.utils.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="AUTH_SERV_USERS")
@EqualsAndHashCode(callSuper=true)
public class UserModel extends BaseEntity{
	@Column(name = "username")
	public String username;

	@Column(name = "password")
	public String password;

}
