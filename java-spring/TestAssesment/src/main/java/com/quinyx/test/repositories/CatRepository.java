package com.quinyx.test.repositories;

import com.quinyx.test.entities.Cat;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends CrudRepository<Cat, Integer> {

  @Override
  Optional<Cat> findById(Integer id);

  @Query("select c FROM Cat c WHERE c.nickName = :nickName")
  Optional<Cat> getCatByNickName(String nick);

  @Override
  <S extends Cat> S save(S entity);
}
