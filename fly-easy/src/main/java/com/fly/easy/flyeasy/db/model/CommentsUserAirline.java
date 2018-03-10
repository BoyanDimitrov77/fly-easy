package com.fly.easy.flyeasy.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "comments_user_airline")
public class CommentsUserAirline {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_user_airline_id_seq")
	@SequenceGenerator(name = "comments_user_airline_id_seq", sequenceName = "comments_user_airline_id_seq", allocationSize = 1)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "airline_id")
	private Airline airline;

	@Column(name = "comment", nullable = false)
	private String comment;

	@Column(name = "count_likes")
	private long likes;

	@Column(name = "count_unlikes")
	private long unlikes;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

}
