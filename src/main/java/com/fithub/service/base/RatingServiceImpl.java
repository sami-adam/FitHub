package com.fithub.service.base;

import com.fithub.dto.base.RatingDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.base.Rating;
import com.fithub.repository.base.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService{
    private final RatingRepository ratingRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<RatingDTO> getRatings() {
        return ratingRepository.findAll().stream().map(rating -> mapper.map(rating, RatingDTO.class)).toList();
    }

    @Override
    public RatingDTO addRating(RatingDTO ratingDTO) {
        Rating rating = mapper.map(ratingDTO, Rating.class);
        return mapper.map(ratingRepository.save(rating), RatingDTO.class);
    }

    @Override
    public RatingDTO updateRating(Long id, RatingDTO ratingDTO) {
        Rating rating = ratingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rating with id " + id + " not found"));
        if(ratingDTO.getRating() != 0) {
            rating.setRating(ratingDTO.getRating());
        }
        if(ratingDTO.getComment() != null) {
            rating.setComment(ratingDTO.getComment());
        }
        return mapper.map(ratingRepository.save(rating), RatingDTO.class);
    }

    @Override
    public Map<String, String> deleteRating(Long id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rating with id " + id + " not found"));
        ratingRepository.delete(rating);
        return Map.of("message", "Rating with id " + id + " deleted successfully", "status", "success");
    }

    @Override
    public List<RatingDTO> searchRatings(String query) {
        return List.of();
    }
}
