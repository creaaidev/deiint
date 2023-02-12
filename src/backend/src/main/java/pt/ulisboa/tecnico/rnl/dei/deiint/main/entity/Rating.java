package pt.ulisboa.tecnico.rnl.dei.deiint.main.entity;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.dto.RatingDto;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "interviewId", nullable = false)
	private Long interviewId;

	@Column(name = "examinerName", nullable = false)
	private String examinerName;

	@Column(name = "ss", nullable = false)
	private int ss;

	@Column(name = "rp", nullable = false)
    private int rp;

	@Column(name = "lin", nullable = false)
    private int lin;

	@Column(name = "win", nullable = false)
    private int win;

	@Column(name = "hw", nullable = false)
    private int hw;

	protected Rating() {
	}

	public Rating(long interviewId, String examinerName, int ss, int rp, int lin, int win, int hw) {
		this.interviewId = interviewId;
		this.examinerName = examinerName;
		this.ss = ss;
		this.rp = rp;
		this.lin = lin;
		this.win = win;
		this.hw = hw;
	}

	public Rating(RatingDto ratingDto) {
		this(ratingDto.getId(), ratingDto.getExaminerName(), ratingDto.getSs(),
				ratingDto.getRp(), ratingDto.getLin(), ratingDto.getWin(), ratingDto.getHw());
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Long getInterviewId() { return interviewId; }
	public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }

	public String getExaminerName() { return examinerName; }
	public void setExaminerName(String examinerName) { this.examinerName = examinerName; }

	public int getSs() { return ss; }
	public void setSs(int ss) { this.ss = ss; }

	public int getRp() { return rp; }
	public void setRp(int rp) { this.rp = rp; }

	public int getLin() { return lin; }
	public void setLin(int lin) { this.lin = lin; }

	public int getWin() { return win; }
	public void setWin(int win) { this.win = win; }

	public int getHw() { return hw; }
	public void setHw(int hw) { this.hw = hw; }

	@Override
	public String toString() {
		return "Rating [id=" + id +
				", interviewId=" + interviewId +
				", examinerName='" + examinerName +
				", ss=" + ss +
				", rp=" + rp +
				", lin=" + lin +
				", win=" + win +
				", hw=" + hw + "]";
	}
}
