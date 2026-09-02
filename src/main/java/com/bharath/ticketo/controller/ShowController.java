package com.bharath.ticketo.controller;

import com.bharath.ticketo.dto.show.ShowRequest;
import com.bharath.ticketo.dto.show.ShowResponse;
import com.bharath.ticketo.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/show")
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(@RequestBody @Valid ShowRequest request){
        ShowResponse showResponse = showService.createShow(request);
        return new ResponseEntity<>(showResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> findShowById(@PathVariable Long id){
        ShowResponse showResponse = showService.findShowById(id);
        return new ResponseEntity<>(showResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ShowResponse>> findAllShows(){
        List<ShowResponse> showResponses = showService.findAllShows();
        return new ResponseEntity<>(showResponses, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> findShowByMovie(@PathVariable Long movieId) {
        List<ShowResponse> showResponses = showService.getShowByMovie(movieId);
        return new ResponseEntity<>(showResponses, HttpStatus.OK);
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<ShowResponse>> findShowByTheatre(@PathVariable Long theatreId) {
        List<ShowResponse> showResponses = showService.getShowByTheatre(theatreId);
        return new ResponseEntity<>(showResponses, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public  ResponseEntity<List<ShowResponse>> findShowByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowResponse> showResponses = showService.getShowByDate(date);
        return new ResponseEntity<>(showResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowResponse> updateShow(@PathVariable Long id, @RequestBody @Valid ShowRequest request){
        ShowResponse showResponse = showService.updateShow(id, request);
        return new ResponseEntity<>(showResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        String response = showService.deleteShow(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
