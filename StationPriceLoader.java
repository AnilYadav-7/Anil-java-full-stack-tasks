package com.example.metro.util;

import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class StationPriceLoader {
	
	private Map<String, Integer> stationPriceMap;
	

    @PostConstruct
    public void loadStationPrices() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("stations.json");
            stationPriceMap = mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load stations.json", e);
        }
    }
    
	public int getPrice(String sourceStation) {
		// TODO Auto-generated method stub
		Integer price = stationPriceMap.get(sourceStation.toUpperCase());
        if (price == null) {
            throw new IllegalArgumentException("Invalid station: " + sourceStation);
        }
        return price;
	}
}
