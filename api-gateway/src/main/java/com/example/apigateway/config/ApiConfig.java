package com.example.apigateway.config;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApiConfig {
    //web client ne feign zbog cirkularne zavisnosti
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, WebClient.Builder webClientBuilder) {
        return builder.routes()
                .route("dodajJeloAuth", r -> r.path("/jelo/dodajJelo")
                        .filters(f->f
                                .filter(autorizacijaFilter("/jelo/dodajJelo"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://meni-service"))
                .route("promeniCenuJelaAuth", r -> r.path("/jelo/promeniCenuJela")
                        .filters(f->f
                                .filter(autorizacijaFilter("/jelo/promeniCenuJela"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://meni-service"))
                .route("dodajKategorijuAuth", r -> r.path("/kategorija/dodajKategoriju/**")
                        .filters(f->f
                                .filter(autorizacijaFilter("/kategorija/dodajKategoriju"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://meni-service"))
                .route("dodajKonobaraAuth", r -> r.path("/konobar/dodajKonobara/**")
                        .filters(f->f
                                .filter(autorizacijaFilter("/konobar/dodajKonobara/**"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://racun-service"))
                .route("dodajRacunAuth", r -> r.path("/racun/dodajRacun")
                        .filters(f->f
                                .filter(autorizacijaFilter("/racun/dodajRacun"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://racun-service"))
                .route("obrisiRacunAuth", r -> r.path("/racun/obrisiRacun")
                        .filters(f->f
                                .filter(autorizacijaFilter("/racun/obrisiRacun"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://racun-service"))
                .route("prihodAuth", r -> r.path("/racun/prihodZaDanZaljubljenih")
                        .filters(f->f
                                .filter(autorizacijaFilter("/racun/prihodZaDanZaljubljenih"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://racun-service"))
                .route("dodajStoAuth", r -> r.path("/sto/dodajSto")
                        .filters(f->f
                                .filter(autorizacijaFilter("/sto/dodajSto"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://rezervacija-service"))
                .route("promeniAuth", r -> r.path("/sto/promeniBrojStolica")
                        .filters(f->f
                                .filter(autorizacijaFilter("/sto/promeniBrojStolica"))
                                .filter(konobarAuthFilter(webClientBuilder))
                        )
                        .uri("lb://rezervacija-service"))
                .route("dodajRezervacijuAuth", r -> r.path("/rezervacija/rezervisi")
                        .filters(f->f
                                .filter(autorizacijaFilter("/rezervacija/rezervisi"))
                                .filter(korisnikAuthFilter(webClientBuilder))
                        )
                        .uri("lb://rezervacija-service"))
                .route("otkaziRezervacijuAuth", r -> r.path("/rezervacija/otkaziRezervaciju")
                        .filters(f->f
                                .filter(autorizacijaFilter("/rezervacija/otkaziRezervaciju"))
                                .filter(korisnikAuthFilter(webClientBuilder))
                        )
                        .uri("lb://rezervacija-service"))
                .route("home", r -> r.path("/home")
                        .filters(f -> f.rewritePath("/home", "/jelo/pregledMenija")
                        )
                        .uri("lb://meni-service"))
                .route("meni-service-jelo", r -> r.path("/jelo/**")
                        .uri("lb://meni-service"))
                .route("meni-service-narudzbina", r -> r.path("/narudzbina/**")
                        .uri("lb://meni-service"))
                .route("meni-service-kategorija", r -> r.path("/kategorija/**")
                        .uri("lb://meni-service"))
                .route("racun-service-konobar", r -> r.path("/konobar/**")
                        .uri("lb://racun-service"))
                .route("racun-service-racun", r -> r.path("/racun/**")
                        .uri("lb://racun-service"))
                .route("rezervacija-service-korisnik", r -> r.path("/korisnik/**")
                        .uri("lb://rezervacija-service"))
                .route("rezervacija-service-sto", r -> r.path("/sto/**")
                        .uri("lb://rezervacija-service"))
                .route("rezervacija-service-rezervacija", r -> r.path("/rezervacija/**")
                        .uri("lb://rezervacija-service"))
                .build();
    }

    private boolean autorizacija(String ruta, String uloga) {
        Map<String, String> prava = new HashMap<>();
        prava.put("/jelo/dodajJelo", "KONOBAR");
        prava.put("/jelo/promeniCenuJela", "KONOBAR");
        prava.put("/kategorija/dodajKategoriju", "KONOBAR");
        prava.put("/konobar/dodajKonobara/**", "KONOBAR");
        prava.put("/racun/dodajRacun", "KONOBAR");
        prava.put("/racun/obrisiRacun", "KONOBAR");
        prava.put("/racun/prihodZaDanZaljubljenih", "KONOBAR");
        prava.put("/sto/dodajSto", "KONOBAR");
        prava.put("/sto/promeniBrojStolica", "KONOBAR");
        prava.put("/rezervacija/rezervisi", "KORISNIK");
        prava.put("/rezervacija/otkaziRezervaciju", "KORISNIK");

        String dozvoljenaUloga = prava.get(ruta);

        if(dozvoljenaUloga == null) {
            return false;
        }
        return dozvoljenaUloga.equals(uloga);
    }

    //autorizacija
    private GatewayFilter autorizacijaFilter(String ruta) {
        return (exchange, chain) -> {
            String uloga = null;
            if(exchange.getRequest().getHeaders().containsHeader("idKonobar")
                    && exchange.getRequest().getHeaders().containsHeader("imeKonobar")){
                uloga = "KONOBAR";
            }else if(exchange.getRequest().getHeaders().containsHeader("username")
                    && exchange.getRequest().getHeaders().containsHeader("password")){
                uloga = "KORISNIK";
            }

            if(uloga == null || !autorizacija(ruta, uloga)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    //autentifikacija
    //metode da ne bi bilo cirkularne zavisnosti ako kreiram Component
    private GatewayFilter konobarAuthFilter(WebClient.Builder webClientBuilder) {

        return (exchange, chain) -> {
            if(exchange.getRequest().getHeaders().containsHeader("idKonobar")
                    && exchange.getRequest().getHeaders().containsHeader("imeKonobar")){
                Integer idKonobar =Integer.valueOf(exchange.getRequest().getHeaders().getFirst("idKonobar"));
                String imeKonobar = exchange.getRequest().getHeaders().getFirst("imeKonobar");

                if(idKonobar != null && imeKonobar != null){
                    return webClientBuilder.build()
                            .get()
                            .uri("lb://racun-service/konobar/postojiKonobar?id=" + idKonobar + "&ime=" + imeKonobar)
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .flatMap(valid -> {

                                if (!Boolean.TRUE.equals(valid)) {
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }

                                return chain.filter(exchange);
                            });
                }else{
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }else{
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    private GatewayFilter korisnikAuthFilter(WebClient.Builder webClientBuilder) {

        return (exchange, chain) -> {
            if(exchange.getRequest().getHeaders().containsHeader("username")
                    && exchange.getRequest().getHeaders().containsHeader("password")){
                String username = exchange.getRequest().getHeaders().getFirst("username");
                String password = exchange.getRequest().getHeaders().getFirst("password");

                if(username != null && password != null){
                    return webClientBuilder.build()
                            .get()
                            .uri("lb://rezervacija-service/korisnik/postojiKorisnik?username=" + username + "&password=" + password)
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .flatMap(valid -> {

                                if (!Boolean.TRUE.equals(valid)) {
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }

                                return chain.filter(exchange);
                            });
                }else{
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }else{
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
}

