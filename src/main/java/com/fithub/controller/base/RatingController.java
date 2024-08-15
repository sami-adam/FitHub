package com.fithub.controller.base;

import com.fithub.dto.base.RatingDTO;
import com.fithub.service.base.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin( origins = "*" )
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    // Get All Ratings
    @GetMapping("/ratings")
    public ResponseEntity<List<RatingDTO>> getRatings(){
        return ResponseEntity.ok(ratingService.getRatings());
    }

    // Add Rating
    @PostMapping("/rating")
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO ratingDTO){
        return ResponseEntity.ok(ratingService.addRating(ratingDTO));
    }

    // Update Rating
    @PutMapping("/rating/{id}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO){
        return ResponseEntity.ok(ratingService.updateRating(id, ratingDTO));
    }

    // Delete Rating
    @DeleteMapping("/rating/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id){
        return ResponseEntity.ok(ratingService.deleteRating(id));
    }

    // Search Ratings
    @GetMapping("/ratings/search/{keyword}")
    public ResponseEntity<List<RatingDTO>> searchRatings(@PathVariable String keyword){
        return ResponseEntity.ok(ratingService.searchRatings(keyword));
    }
}
