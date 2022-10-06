package com.dcc.videoGameApi.service;

import com.dcc.videoGameApi.models.VideoGame;
import com.dcc.videoGameApi.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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






    public HashMap<String, Double> GetConsoleData(){
        HashMap<String,Double> consoleSalesData = new HashMap<String,Double>();
        for(String n : videoGameRepository.findAll().stream().filter(y -> y.getYear() >= 2013).map(v -> v.getPlatform()).distinct().collect(Collectors.toList())){
                consoleSalesData.put(n, videoGameRepository.findAll().stream().filter(p -> p.getYear() >= 2013).filter(i -> i.getPlatform().equals(n)).map(g-> g.getGlobalsales()).reduce((double) 0,(e1, e2)->e1 + e2));
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
    public HashMap<String, HashMap<String, Double>> GetMostSoldByYear(Integer year){
        List<String> gamesNoDupes = videoGameRepository.findAll().stream().filter(y -> y.getYear().equals(year)).sorted(Comparator.comparingDouble(VideoGame::getGlobalsales)).filter(x2 -> x2.getGlobalsales() >= 1).map(v -> v.getName()).distinct().collect(Collectors.toList());
        List<VideoGame> gamesInSameYear = videoGameRepository.findAll().stream().filter(y -> y.getYear().equals(year)).toList();

        HashMap<String, HashMap<String, Double>> consoleSalesData = new HashMap<>();
        for(String n : gamesNoDupes){
            List<VideoGame> gameToSearch = gamesInSameYear.stream().filter(g -> g.getName().equals(n)).toList();
            consoleSalesData.put(n,new HashMap<String,Double>(){
                {
                    put("US Sales", gameToSearch.stream().map(g -> g.getNorthamericasales()).reduce((double) 0,(e1, e2)->e1 + e2));
                    put("EU Sales", gameToSearch.stream().map(g -> g.getEuropesales()).reduce((double) 0,(e1, e2)->e1 + e2));
                    put("Japan Sales", gameToSearch.stream().map(g -> g.getJapansales()).reduce((double) 0,(e1, e2)->e1 + e2));
                    put("Other Sales", gameToSearch.stream().map(g -> g.getOthersales()).reduce((double) 0,(e1, e2)->e1 + e2));
                    put("Global Sales", gameToSearch.stream().map(g -> g.getGlobalsales()).reduce((double) 0,(e1, e2)->e1 + e2));
                }
            });


        }
        return consoleSalesData;
    }

}
