package com.magneto.mutantdetector.exception;

public class InvalidDnaSequenceException extends RuntimeException {

	public InvalidDnaSequenceException() {
		super("The provided DNA sequence is not valid (must be a NxN matrix and contain only valid bases A, T, C or G)");
	}
}
