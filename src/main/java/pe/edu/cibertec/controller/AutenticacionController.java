package pe.edu.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.dto.LoginRequestDTO;
import pe.edu.cibertec.dto.LoginResponseDTO;
import pe.edu.cibertec.service.AutenticacionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    AutenticacionService autenticacionService;

    @PostMapping("/autenticar")
    public ResponseEntity<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            if (autenticacionService.validarCredenciales(loginRequestDTO)) {
                return ResponseEntity.ok(new LoginResponseDTO("00", "Autenticación exitosa", "", ""));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponseDTO("01", "Error: Credenciales incorrectas", "", ""));
            }
        } catch (IOException e) {
            // Maneja el error de IO y devuelve una respuesta adecuada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponseDTO("99", "Error: No se pudo procesar la autenticación", "", ""));
        }
    }

    @GetMapping("/get-integrantes")
    public ResponseEntity<List<LoginResponseDTO>> getIntegrantes() {
        try {
            List<String[]> integrantes = autenticacionService.obtenerIntegrantes();
            List<LoginResponseDTO> listaResponse = new ArrayList<>();
            for (String[] datos : integrantes) {
                listaResponse.add(new LoginResponseDTO("00", "", datos[2], datos[3]));
            }
            return ResponseEntity.ok(listaResponse);
        } catch (IOException e) {
            // Maneja el error de IO y devuelve una respuesta adecuada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(new LoginResponseDTO("99", "Error: No se pudo obtener la lista de integrantes", "", "")));
        }
    }

}

