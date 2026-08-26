package com.example.meniservice.service;
import com.example.meniservice.dtos.BestSellerJeloDTO;
import com.example.meniservice.repositories.NarudzbinaimajelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NarudzbinaImaJelaService {
    private final NarudzbinaimajelaRepository narudzbinaimajelaRepository;

    public List<BestSellerJeloDTO> bestseleri(){
        return narudzbinaimajelaRepository.bestseleri();
    }
}
