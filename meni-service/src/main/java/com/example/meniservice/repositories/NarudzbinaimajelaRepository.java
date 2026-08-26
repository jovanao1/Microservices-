package com.example.meniservice.repositories;

import com.example.meniservice.dtos.BestSellerJeloDTO;
import com.example.meniservice.model.Narudzbinaimajela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface NarudzbinaimajelaRepository extends JpaRepository<Narudzbinaimajela, Integer> {
    @Query("""
    SELECT new com.example.meniservice.dtos.BestSellerJeloDTO(
        j.id,
        j.nazivJelo,
        SUM(nj.kolicinaJela)
    )
    FROM Narudzbinaimajela nj
    JOIN nj.jeloIdjelo j
    GROUP BY j.id, j.nazivJelo
    ORDER BY SUM(nj.kolicinaJela) DESC
""")
    List<BestSellerJeloDTO> bestseleri();
}
