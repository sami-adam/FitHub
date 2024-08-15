package com.fithub.service.base;

import com.fithub.dto.base.RatingDTO;

import java.util.List;
import java.util.Map;

public interface RatingService {
    List<RatingDTO> getRatings();
    RatingDTO addRating(RatingDTO ratingDTO);
    RatingDTO updateRating(Long id, RatingDTO ratingDTO);
    Map<String, String> deleteRating(Long id);
    List<RatingDTO> searchRatings(String query);
}
