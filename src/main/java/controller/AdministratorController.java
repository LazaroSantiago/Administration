package controller;

import entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.AdministratorService;

import java.time.LocalDateTime;

@Controller("AdministratorController")
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Administrator entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.save(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

//    b. Como administrador quiero poder anular cuentas
//    para inhabilitar el uso momentáneo de la misma
    @PutMapping("/ban/{id}")
    public ResponseEntity<?> banAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.banAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/unban/{id}")
    public ResponseEntity<?> unbanAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.unbanAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

//  e.Como administrador quiero consultar la cantidad de monopatines actualmente en operación,
//    versus la cantidad de monopatines actualmente en mantenimiento.
    @GetMapping("/functional_scooters")
    public ResponseEntity<?> getFunctionalScooters() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.getFunctionalScooters());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    //  Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
    @GetMapping("/most_used_scooters/trips/{trips}/year/{year}")
    public ResponseEntity<?> getScootersByTripAmount(@PathVariable Long trips,@PathVariable  int year) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.getScootersByTripAmount(trips, year));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

//  Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
/*
    @GetMapping("/profit/months/{months}/year/{year}")
    public ResponseEntity<?> getProfits(int month1, int month2, int year){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.getProfits(month1, month2, year));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
*/

//  Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
//  habilite los nuevos precios
    @PutMapping("/normal_price/{price}")
    public ResponseEntity<?> setNormalTripPrice(LocalDateTime date,@PathVariable  Long price) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.setNormalTripPrice(date, price));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @PutMapping("/extra_price/{price}")
    public ResponseEntity<?> setExtraTripPrice(LocalDateTime date,@PathVariable Long price) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.setNormalTripPrice(date, price));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

}
