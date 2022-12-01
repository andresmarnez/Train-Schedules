package com.andresmarnez.exceptions;

public class TrainException extends Exception{

	private final CODE code;

	public TrainException(String message) {
		super(message);
		this.code = CODE.UNDEFINED;
	}

	public TrainException(CODE code) {
		super("Undefined.");
		this.code = code;
	}

	public TrainException(String message, CODE code) {
		super(message);
		this.code = code;
	}

	@Override
	public String toString() {
		return code + ": " + getMessage();
	}
}
