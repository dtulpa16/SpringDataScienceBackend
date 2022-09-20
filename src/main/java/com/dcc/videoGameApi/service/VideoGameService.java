package com.dcc.videoGameApi.service;

import com.dcc.videoGameApi.models.VideoGame;
import com.dcc.videoGameApi.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class VideoGameService {

    @Autowired
    private VideoGameRepository videoGameRepository;

    public long GetCountOfGames(){
        return videoGameRepository.count();
    }
    public List<VideoGame> GetAllGames(){
        return videoGameRepository.findAll();
    }
    public Optional<VideoGame> GetSingleGame(Integer id){
        return videoGameRepository.findById(id);
    }
    public HashMap<String,Integer> GetConsoleData(){
//        List<String> consoles = videoGameRepository.findAll().stream().map(v -> v.getPlatform()).distinct().collect(Collectors.toList());
        List<String> consoles = videoGameRepository.findAll().stream().filter(y -> y.getYear() > 2013).map(v -> v.getPlatform()).distinct().collect(Collectors.toList());
        HashMap<String,Integer> consoleSalesData = new HashMap<String,Integer>();
        for(String n : consoles){
                Integer totalSales = Math.toIntExact(videoGameRepository.findAll().stream().filter(i -> i.getPlatform().equals(n)).count());
                consoleSalesData.put(n,totalSales);
        }
        return consoleSalesData;
    }
    public HashMap<String,Integer> GetYearlySale(Integer year){
        List<String> consoles = videoGameRepository.findAll().stream().filter(y -> y.getYear().equals(year)).map(v -> v.getPlatform()).distinct().collect(Collectors.toList());
        HashMap<String,Integer> consoleSalesData = new HashMap<String,Integer>();
        for(String n : consoles){
            Integer totalSales = Math.toIntExact(videoGameRepository.findAll().stream().filter(i -> i.getPlatform().equals(n)).count());
            consoleSalesData.put(n,totalSales);
        }
        return consoleSalesData;
    }

}
