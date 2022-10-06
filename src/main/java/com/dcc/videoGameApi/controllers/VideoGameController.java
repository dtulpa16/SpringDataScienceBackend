package com.dcc.videoGameApi.controllers;

import com.dcc.videoGameApi.models.VideoGame;
import com.dcc.videoGameApi.service.VideoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VideoGameController {

    @Autowired
    private VideoGameService service;

    //Example endpoint to return count of all games on DB. Should return 10000 in Postman
    @GetMapping("/count")
    public long GetCount(){
        return service.GetCountOfGames();
    }
    @GetMapping("/all")
    public List<VideoGame> GetAll(){
        return service.GetAllGames();
    }
    @GetMapping("/game/{id}")
    public Optional<VideoGame> GetGame(@PathVariable int id){
        return service.GetSingleGame(id);
    }


    @GetMapping("/console-sales")
    public HashMap<String, Double> GetConsoleData(){
        return service.GetConsoleData();
    }

    @GetMapping("/yearly-sales/{year}")
    public HashMap<String,Integer> GetYearlySale(@PathVariable Integer year){
        return service.GetYearlySale(year);
    }
    @GetMapping("/most-sold/{year}")
    public HashMap<String, HashMap<String, Double>> GetMostSoldByYear(@PathVariable Integer year){
        return service.GetMostSoldByYear(year);
    }

}
