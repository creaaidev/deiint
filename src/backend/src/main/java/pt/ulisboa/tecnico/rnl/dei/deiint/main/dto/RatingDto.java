package pt.ulisboa.tecnico.rnl.dei.deiint.main.dto;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.entity.Rating;

import java.io.Serializable;

// Data Transfer Object, to communicate with frontend
public class RatingDto implements Serializable {
	private long id;
	private long interviewId;
	private String examinerName;

	private int ss;
	private int rp;
	private int lin;
	private int win;
	private int hw;

	public RatingDto() {
	}

	public RatingDto(long id, long interviewId, String examinerName, int ss, int rp, int lin, int win, int hw) {
		this.id = id;
		this.interviewId = interviewId;
		this.examinerName = examinerName;
		this.ss = ss;
		this.rp = rp;
		this.lin = lin;
		this.win = win;
		this.hw = hw;
	}

	public RatingDto(Rating rating) {
		this(rating.getId(), rating.getInterviewId(), rating.getExaminerName(), rating.getSs(), rating.getRp(),
				rating.getLin(), rating.getWin(), rating.getHw());
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInterviewId() {
		return this.interviewId;
	}

	public void setInterviewId(long interviewId) {
		this.interviewId = interviewId;
	}

	public String getExaminerName() {
		return this.examinerName;
	}

	public void setExaminerName(String examinerName) {
        this.examinerName = examinerName;
    }

	// Individual scores

	public int getSs() { return this.ss; }
	public void setSs(int ss) { this.ss = ss; }

	public int getRp() { return this.rp; }
	public void setRp(int rp) { this.rp = rp; }

	public int getLin() { return lin; }
	public void setLin(int lin) { this.lin = lin; }

	public int getWin() { return win; }
	public void setWin(int win) { this.win = win; }

	public int getHw() { return hw; }
	public void setHw(int hw) { this.hw = hw; }

	@Override
	public String toString() {
		return "RatingDto [id=" + id + ", interviewId=" + interviewId + ", examinerName=" + examinerName +
				", ss=" + ss + ", rp=" + rp + ", lin=" + lin + ", win=" + win + ", hw=" + hw + "]";
	}
}
