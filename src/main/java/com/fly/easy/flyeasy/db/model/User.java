package com.fly.easy.flyeasy.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profile_picture")
	private Picture profilePicture;

	@Column(name = "birth_date")
	private Date birthDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private Location location;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private List<Bonus> bonuses;

	 @Override
	    public boolean equals(Object u) {

	        if(!(u instanceof User)) {
	            return false;
	        }

	        return getId() == ((User) u).getId();
	    }


	    @Override
	    public int hashCode() {

	        return new HashCodeBuilder(17, 31)
	                .append((int)(id ^ (id >>> 32)))
	                .toHashCode();
	    }
}
