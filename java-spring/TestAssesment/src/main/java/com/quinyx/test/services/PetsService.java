package com.quinyx.test.services;

import static java.util.stream.Collectors.mapping;

import com.quinyx.test.entities.Dog;
import com.quinyx.test.entities.Sound;
import com.quinyx.test.model.CreateDogRequest;
import com.quinyx.test.model.CatResponse;
import com.quinyx.test.model.DogSummary;
import com.quinyx.test.model.SortOrder;
import com.quinyx.test.repositories.CatRepository;
import com.quinyx.test.repositories.DogRepository;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

@Service
public class PetsService {

  private final DogRepository dogRepository;
  private final CatRepository catRepository;

  public PetsService(DogRepository dogRepository, CatRepository catRepository) {
    this.dogRepository = dogRepository;
    this.catRepository = catRepository;
  }

  public void addDog(CreateDogRequest request) throws FileNotFoundException {
    var maxAge = getMaxAge();
    if (maxAge >= request.getAge()) {
      var dogEntity = new Dog();
      dogEntity.setNickName(request.getNickName());
      dogEntity.setAge(request.getAge());
      dogEntity.setAddedAt(LocalDateTime.now());
      dogEntity.setPrice(request.getPrice());
      dogEntity.setSound(Sound.BARKING);
      dogEntity.setId(new Random().nextInt());
      dogRepository.save(dogEntity);
    }
  }

  @Transactional
  public void deleteDogsByIds(List<String> nicknames) {
    var dogs = dogRepository.getByNickNameOrderByCanSwimDesc(new HashSet<>(nicknames));
    dogs.forEach(dog -> {
      dogRepository.delete(dog);
    });
  }

  public List<CatResponse> findCatsByNickNames(List<String> nicks) {
    return nicks.stream()
        .map(catRepository::getCatByNickName)
        .collect(mapping(
            cat -> new CatResponse(cat.get().getId(), cat.get().getNickName(), cat.get().getAge()),
            Collectors.toUnmodifiableList()));
  }

  public Page<DogSummary> findDogsByMaxPrice(String price, int page, int size, SortOrder sortOrder) {
    return paginate(page, size)
        .andThen(convertToSummary())
        .andThen(convertToPagedResult(page, size, sortOrder))
        .apply(dogRepository.findAll().stream()
            .filter(dog -> dog.getAge() <= Double.parseDouble(price))
            .collect(Collectors.toList()));
  }

  private Integer getMaxAge() throws FileNotFoundException {
    Yaml yaml = new Yaml();
    InputStream inputStream = new FileInputStream("classpath:application.yml");
    HashMap yamlMap = yaml.load(inputStream);
    return (int) yamlMap.get("petsMaxAge");
  }

  private static Function<List<Dog>, List<Dog>> paginate(int page, int perPage) {
    return dogs -> {
      int startIndex = (page - 1) * perPage;
      int endIndex = startIndex + perPage;
      if (startIndex >= dogs.size()) {
        return List.of();
      }
      if (endIndex > dogs.size()) {
        endIndex = dogs.size();
      }
      return dogs.subList(startIndex, endIndex);
    };
  }

  private static Function<List<Dog>, List<DogSummary>> convertToSummary() {
    return dogs -> dogs.stream().map(dog -> new DogSummary(dog.getId(), dog.getNickName(), dog.getPrice()))
        .collect(Collectors.toList());
  }

  private static Function<List<DogSummary>, Page<DogSummary>> convertToPagedResult(int page,
      int size, SortOrder sortOrder) {
    return dogSummaries -> new PageImpl<>(dogSummaries, PageRequest.of(page, size,
        sortOrder == SortOrder.ASC ? Sort.by(Order.asc("price")) : Sort.by(Order.desc("price"))),
        dogSummaries.size());
  }

}
