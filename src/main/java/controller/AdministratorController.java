package controller;

import entity.Administrator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.AdministratorService;

import java.time.LocalDateTime;

@Controller("AdministratorController")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @GetMapping("")
    @Operation(summary = "Obtener todos los administradores")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un administradores por ID")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    @Operation(summary = "Crear un nuevo administrador")
    public ResponseEntity<?> save(@RequestBody Administrator entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.save(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un administrador por id")
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
    @Operation(summary = "Anular cuenta")
    public ResponseEntity<?> banAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.banAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/unban/{id}")
    @Operation(summary = "Habilitar cuentas")
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
    @Operation(summary = "Consultar la cantidad de monopatines en operación")
    public ResponseEntity<?> getFunctionalScooters() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.getFunctionalScooters());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    //  Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
    @GetMapping("/most_used_scooters/trips/{trips}/year/{year}")
    @Operation(summary = "Consultar los monopatines con más de X viajes en un cierto año")
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
    @Operation(summary = "Hacer un ajuste del precio normal a partir de cierta fecha dada")
    public ResponseEntity<?> setNormalTripPrice(LocalDateTime date,@PathVariable  Long price) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.setNormalTripPrice(date, price));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/extra_price/{price}")
    @Operation(summary = "Hacer un ajuste del precio extra a partir de cierta fecha dada")
    public ResponseEntity<?> setExtraTripPrice(LocalDateTime date,@PathVariable Long price) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administratorService.setNormalTripPrice(date, price));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

}
