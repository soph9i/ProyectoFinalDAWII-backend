package pe.edu.cibertec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.dto.LoginRequestDTO;
import pe.edu.cibertec.service.AutenticacionService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public boolean validarCredenciales(LoginRequestDTO loginRequestDTO) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:integrantes.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                // Verificar si las credenciales coinciden
                if (loginRequestDTO.codigoAlumno().equals(datos[0]) &&
                        loginRequestDTO.password().equals(datos[1])) {
                    return true; // Credenciales válidas
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo de integrantes", e);
        }
        return false; // Credenciales no válidas
    }


    @Override
    public List<String[]> obtenerIntegrantes() throws IOException {
        List<String[]> listaIntegrantes = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:integrantes.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                listaIntegrantes.add(datos); // Añadir cada integrante a la lista
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo de integrantes", e);
        }

        return listaIntegrantes;
    }
}
