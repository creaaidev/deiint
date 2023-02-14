package pt.ulisboa.tecnico.rnl.dei.deiint.main.dto;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.entity.Interview;

import java.io.Serializable;

// Data Transfer Object, to communicate with frontend
public class InterviewDto implements Serializable {
	private long id;
	private long callId;
	private long roomId;
	private long candidateId;
	private String status;

	public InterviewDto() {
	}

	public InterviewDto(long id, long callId, long roomId, long candidateId, String status) {
		this.id = id;
        this.callId = callId;
        this.roomId = roomId;
        this.candidateId = candidateId;
        this.status = status;
	}

	public InterviewDto(Interview interview) {
		this(interview.getId(), interview.getCallId(), interview.getRoomId(), interview.
				getCandidateId(), interview.getStatus());
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCallId() { return this.callId; }

	public void setCallId(long callId) {
		this.callId = callId;
	}

	public long getRoomId() { return this.roomId; }

	public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

	public long getCandidateId() { return this.candidateId; }

	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InterviewDto [id=" + id + ", callId=" + callId + ", roomId" + roomId +
				", candidateId=" + candidateId + ", status=" + status + "]";
	}
}
