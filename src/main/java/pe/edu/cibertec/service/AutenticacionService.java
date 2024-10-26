package pe.edu.cibertec.service;

import pe.edu.cibertec.dto.LoginRequestDTO;

import java.io.IOException;
import java.util.List;

public interface AutenticacionService {

    boolean validarCredenciales(LoginRequestDTO loginRequestDTO) throws IOException;
    List<String[]> obtenerIntegrantes() throws IOException;
}
