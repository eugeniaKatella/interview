package com.quinyx.test.controllers;

import com.quinyx.test.model.CreateDogRequest;
import com.quinyx.test.model.CatResponse;
import com.quinyx.test.model.DogSummary;
import com.quinyx.test.model.SortOrder;
import com.quinyx.test.services.PetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.FileNotFoundException;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Pets management API")
@RestController
@RequestMapping(value = "/pets")
public class PetsController {

  private final PetsService petsService;

  public PetsController(PetsService petsService) {
    this.petsService = petsService;
  }

  @ApiOperation(value = "Fetches dogs by max price", response = DogSummary.class, responseContainer = "Page")
  @GetMapping(path = "dogs/{price}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<DogSummary> getDogsBelowPrice(@PathVariable("price") String price,
      @RequestParam(name = "page", defaultValue = "1")
      @Min(value = 1)
      @Max(value = 2000) int page,
      @RequestParam(name = "size")
      @Min(value = 1)
      @Max(value = 200) int size,
      @RequestParam(name = "sortOrder", defaultValue = "desc") SortOrder sortOrder) {
    return petsService.findDogsByMaxPrice(price, page, size, sortOrder);
  }

  @ApiOperation(value = "Registers a dog")
  @PutMapping(path = "dogs", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addDog(@RequestBody CreateDogRequest request) throws FileNotFoundException {
    petsService.addDog(request);
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "Deletes dogs by nicknames")
  @DeleteMapping(path = "dogs")
  @ResponseStatus(HttpStatus.OK)
  public void deleteDogsByNickNames(@RequestBody List<String> nicknames) {
    petsService.deleteDogsByIds(nicknames);
  }

  @ApiOperation(value = "Fetches cats by nicknames")
  @GetMapping(path = "cats")
  public List<CatResponse> getDogByNickname(
      @RequestParam(name = "nickname", required = false) List<String> nicks) {
    return petsService.findCatsByNickNames(nicks);
  }

}
