package com.theironyard.example.microservices.controllers;

public class PatchInstruction {
	private PatchInstructionOperation op;
	private String path;
	private String value;
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s}", op, path, value);
	}
	
	public PatchInstructionOperation getOp() {
		return op;
	}
	public void setOp(PatchInstructionOperation op) {
		this.op = op;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
