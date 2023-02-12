package pt.ulisboa.tecnico.rnl.dei.deiint.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.dto.CallDto;

@Entity
@Table(name = "calls")
public class Call {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "interviews_period_open", nullable = false)
	private boolean interviewsPeriodOpen;

	// TODO: maybe add more fields? ...or maybe not? what makes sense here?
	// COMMENT: IDK CHIEF :sob:

	protected Call() {
	}

	public Call(String name, boolean interviewsPeriodOpen) {
		this.name = name;
		this.interviewsPeriodOpen = interviewsPeriodOpen;
	}

	public Call(CallDto callDto) {
		this(callDto.getName(), callDto.getInterviewsPeriodOpen());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInterviewsPeriodOpen() {
		return this.interviewsPeriodOpen;
	}

	public void setInterviewsPeriodOpen(boolean interviewsPeriodOpen) {
		this.interviewsPeriodOpen = interviewsPeriodOpen;
	}

	@Override
	public String toString() {
		return "Call [id=" + id + ", name=" + name + ", interviewsPeriodOpen="
				+ interviewsPeriodOpen + "]";
	}
}
