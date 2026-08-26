package com.example.meniservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BestSellerJeloDTO{
   private Integer jeloId;
   private String nazivJela;
   private Long ukupnoProdato;
}
