package com.fly.easy.flyeasy.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fly.easy.flyeasy.db.model.Bonus;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BonusDto {

	private long id;

	private long userId;

	private BigDecimal percent;

	private Date expiredDate;

	private Boolean isUsed;

	public BonusDto() {
		super();
	}

	public BonusDto(long id, long userId, BigDecimal percent, Date expiredDate, Boolean isUsed) {
		super();
		this.id = id;
		this.userId = userId;
		this.percent = percent;
		this.expiredDate = expiredDate;
		this.isUsed = isUsed;
	}

	public static List<BonusDto> of(List<Bonus> bonuses) {

		return bonuses.stream()
				.map(bonus -> FlyEasyApp.ofNullable(bonus,
						b -> BonusDto.builder().id(b.getId()).userId(b.getUser().getId()).percent(b.getPercent())
								.expiredDate(b.getExpiredDate()).isUsed(b.getIsUsed()).build()))
				.collect(Collectors.toList());

	}

}
