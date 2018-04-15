package com.magneto.mutantdetector.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Dna {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@NonNull
	@Column(length = 2048, nullable = false)
	private String dna;
	@NonNull
	@Column(nullable = false)
	private Boolean isMutant;
}
