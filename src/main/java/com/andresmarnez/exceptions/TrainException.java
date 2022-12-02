package com.andresmarnez.exceptions;

public class TrainException extends Exception{

	private final TRAINCODE code;

	public TrainException(String message) {
		super(message);
		this.code = TRAINCODE.UNDEFINED;
	}

	public TrainException(TRAINCODE code) {
		super("Undefined.");
		this.code = code;
	}

	public TrainException(String message, TRAINCODE code) {
		super(message);
		this.code = code;
	}

	@Override
	public String toString() {
		return code + ": " + getMessage();
	}
}
