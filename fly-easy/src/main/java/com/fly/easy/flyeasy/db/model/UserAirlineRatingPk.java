package com.fly.easy.flyeasy.db.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UserAirlineRatingPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAirlineRatingPk(User user, Airline airline) {
		this.user = user;
		this.airline = airline;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", updatable = false, insertable = false)
	User user;

	@ManyToOne
	@JoinColumn(name = "airline_id", updatable = false, insertable = false)
	Airline airline;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAirlineRatingPk other = (UserAirlineRatingPk) obj;
		if (airline == null) {
			if (other.airline != null)
				return false;
		} else if (!airline.equals(other.airline))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
