package com.project.recommend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.recommend.model.recommend;

import java.util.List;

@Repository
public interface recommendRepo extends CrudRepository<recommend, Long> {
    public List<recommend> findByDestId(Long destId);
}
