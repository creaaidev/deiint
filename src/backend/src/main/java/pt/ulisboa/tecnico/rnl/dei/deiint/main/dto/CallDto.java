package pt.ulisboa.tecnico.rnl.dei.deiint.main.dto;

import java.io.Serializable;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.entity.Call;

// Data Transfer Object, to communicate with frontend
public class CallDto implements Serializable {
	private long id;
	private String name;
	private boolean interviewsPeriodOpen;

	public CallDto() {
	}

	public CallDto(long id, String name, boolean interviewsPeriodOpen) {
		this.id = id;
		this.name = name;
		this.interviewsPeriodOpen = interviewsPeriodOpen;
	}

	public CallDto(Call call) {
		this(call.getId(), call.getName(), call.isInterviewsPeriodOpen());
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getInterviewsPeriodOpen() {
		return this.interviewsPeriodOpen;
	}

	public void setInterviewsPeriodOpen(boolean interviewsPeriodOpen) {
		this.interviewsPeriodOpen = interviewsPeriodOpen;
	}

	@Override
	public String toString() {
		return "CallDto [id=" + id + ", name=" + name + ", interviewsPeriodOpen=" + interviewsPeriodOpen + "]";
	}
}
