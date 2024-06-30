package com.project.recommend.service;


import com.project.recommend.exception.recommendNotFoundException;
import com.project.recommend.model.recommend;
import com.project.recommend.repository.recommendRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class recommendImpl {
@Autowired
    private recommendRepo recommendRepo;


    public recommend createRecommendation(recommend aRecommendation){
        return recommendRepo.save(aRecommendation);
    }
    public recommend createRecommendation(Long destId, String author, long rate, String content){
        recommend recommendationA = new recommend(destId, author, rate, content);
        return recommendRepo.save(recommendationA);
    }

    public recommend getRecommendation(Long recommendationId){
        Optional<recommend> tempRecommendation = recommendRepo.findById(recommendationId);
        if(tempRecommendation.isPresent()){
            System.out.println(tempRecommendation.get());
            return tempRecommendation.get();
        }
        else{
            throw new recommendNotFoundException(recommendationId);
        }
    }

    public List<recommend> getAllRecommendationsByLocationId(Long destId){
        return recommendRepo.findByDestId(destId);
    }


    public recommend updateRecommendation(Long recommendationId, recommend recommendationA){
        Optional<recommend> temp = recommendRepo.findById(recommendationId);
        if(temp.isPresent()){
            temp.get().setDestId(recommendationA.getDestId());
            temp.get().setAuthor(recommendationA.getAuthor());
            temp.get().setRate(recommendationA.getRate());
            temp.get().setContent(recommendationA.getContent());

            recommendRepo.save(temp.get());
            return recommendationA;
        }
        else{
            throw new recommendNotFoundException(recommendationId);
        }
    }




    public void deleteRecommendation(Long recommendationId){
        recommendRepo.deleteById(recommendationId);
    }

}
