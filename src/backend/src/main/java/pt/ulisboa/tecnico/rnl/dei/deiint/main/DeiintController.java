package pt.ulisboa.tecnico.rnl.dei.deiint.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.deiint.main.dto.CallDto;
import pt.ulisboa.tecnico.rnl.dei.deiint.main.service.CallService;
import pt.ulisboa.tecnico.rnl.dei.deiint.main.dto.InterviewDto;
import pt.ulisboa.tecnico.rnl.dei.deiint.main.service.InterviewService;
import pt.ulisboa.tecnico.rnl.dei.deiint.main.dto.RatingDto;
import pt.ulisboa.tecnico.rnl.dei.deiint.main.service.RatingService;

@RestController
public class DeiintController {
	@Autowired
	private CallService callService;

	@Autowired
    private InterviewService interviewService;

	@Autowired
    private RatingService ratingService;

	@GetMapping("/calls")
	public List<CallDto> getCalls() {
		return callService.getAllCalls();
	}

	@PostMapping("/calls")
	public CallDto createCall(@RequestBody CallDto callDto) {
		return callService.createCall(callDto);
	}

	@GetMapping("/calls/{id}")
	public CallDto getCall(@PathVariable long id) {
		return callService.getCall(id);
	}

	@PutMapping("/calls/{id}")
	public CallDto updateCall(@PathVariable long id, @RequestBody CallDto callDto) {
		return callService.updateCall(id, callDto);
	}

	@DeleteMapping("/calls/{id}")
	public void deleteCall(@PathVariable long id) {
		callService.deleteCall(id);
	}

	@GetMapping("/interviews")
    public List<InterviewDto> getInterviews() {
        return interviewService.getAllInterviews();
    }

	@PostMapping("/interviews")
    public InterviewDto createInterview(@RequestBody InterviewDto interviewDto) {
        return interviewService.createInterview(interviewDto);
    }

	@GetMapping("/interviews/{id}")
    public InterviewDto getInterview(@PathVariable long id) {
        return interviewService.getInterview(id);
    }

	@PutMapping("/interviews/{id}")
    public InterviewDto updateInterview(@PathVariable long id, @RequestBody InterviewDto interviewDto) {
        return interviewService.updateInterview(id, interviewDto);
    }

	@DeleteMapping("/interviews/{id}")
    public void deleteInterview(@PathVariable long id) {
        interviewService.deleteInterview(id);
    }

	@GetMapping("/interviews/{id}/ratings")
    public List<RatingDto> getRatingsByInterviewId(@PathVariable long id) {
        return ratingService.getRatingsByInterviewId(id);
    }

	@GetMapping("/interviews/ratings")
	public List<RatingDto> getAllRatings() {
        return ratingService.getAllRatings();
    }

	@GetMapping("/interviews/ratings/{id}")
	public RatingDto getRating(@PathVariable long id) {
        return ratingService.getRating(id);
    }

	@PutMapping("/interviews/ratings/{id}")
    public RatingDto updateRating(@PathVariable long id, @RequestBody RatingDto ratingDto) {
        return ratingService.updateRating(id, ratingDto);
    }

	@DeleteMapping("/interviews/ratings/{id}")
    public void deleteRating(@PathVariable long id) {
        ratingService.deleteRating(id);
    }

	@PostMapping("/interviews/ratings")
	public RatingDto createRating(@RequestBody RatingDto ratingDto) {
        return ratingService.createRating(ratingDto);
    }

}
