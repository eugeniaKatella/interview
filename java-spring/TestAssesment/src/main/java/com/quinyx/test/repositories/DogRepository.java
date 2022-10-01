package com.quinyx.test.repositories;

import com.quinyx.test.entities.Dog;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DogRepository extends JpaRepository<Dog, String> {

  @Transactional
  List<Dog> getByNickNameOrderByCanSwimDesc(Set<String> nicknames);

  void saveAll(List<Dog> dogs);



}
