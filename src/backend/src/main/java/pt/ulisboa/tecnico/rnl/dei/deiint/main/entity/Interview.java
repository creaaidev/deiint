package pt.ulisboa.tecnico.rnl.dei.deiint.main.entity;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.dto.InterviewDto;

import javax.persistence.*;

@Entity
@Table(name = "interviews")
public class Interview {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "callId", nullable = false)
	private long callId;

	@Column(name = "roomId", nullable = false)
	private long roomId;

	@Column(name = "candidateId", nullable = false)
	private long candidateId;

	@Column(name = "status", nullable = false)
	private String status;

	protected Interview() {
	}

	public Interview(long callId, long roomId, long candidateId, String status) {
		this.callId = callId;
		this.roomId = roomId;
		this.candidateId = candidateId;
		this.status = status;
	}

	public Interview(InterviewDto interviewDto) {
		this(interviewDto.getCallId(), interviewDto.getRoomId(), interviewDto.getCandidateId(),
				interviewDto.getStatus());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCallId() {
		return this.callId;
	}

	public void setCallId(long callId) {
		this.callId = callId;
	}

	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

	public long getCandidateId() {
        return this.candidateId;
    }

	public void setCandidateId(long candidate) {
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
		return "Interview [id=" + id + ", callId=" + callId + ", roomId=" + roomId + ", candidateId=" +
				candidateId + ", status=" + status + "]";
	}
}
