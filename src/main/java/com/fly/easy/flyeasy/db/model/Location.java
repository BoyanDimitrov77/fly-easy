package com.fly.easy.flyeasy.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "location")
public class Location {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
	@SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

}
