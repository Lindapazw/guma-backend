package com.guma.frontend.service;

import java.util.ArrayList;
import java.util.List;

import com.guma.application.dto.RedSocialDTO;
import com.guma.application.dto.ResultadoDTO;
import com.guma.application.facade.RedSocialFacade;
import com.guma.application.facade.impl.RedSocialFacadeImpl;
import com.guma.frontend.adapter.RedSocialAdapter;
import com.guma.frontend.dto.NuevaRedSocialFrontendRequest;
import com.guma.frontend.dto.RedSocialFrontendDTO;

/**
 * Servicio para gestión de redes sociales.
 * Conecta el frontend con el backend a través de los facades.
 * 
 * @author GUMA Development Team
 * @version 2.0
 */
public class RedesSocialesService {

    private final RedSocialFacade redSocialFacade;

    /**
     * Constructor que inicializa el facade.
     */
    public RedesSocialesService() {
        this.redSocialFacade = new RedSocialFacadeImpl();
    }

    /**
     * Constructor para testing (permite inyectar facade mock).
     */
    public RedesSocialesService(RedSocialFacade redSocialFacade) {
        this.redSocialFacade = redSocialFacade;
    }

    /**
     * Crea una nueva red social en el catálogo.
     * 
     * @param request datos de la red social
     * @return RedSocialDTO creada, o null si falla
     * @throws IllegalArgumentException si la red social ya existe (nombre o URL
     *                                  duplicada)
     */
    public RedSocialFrontendDTO crearRedSocial(NuevaRedSocialFrontendRequest request) {
        try {
            // Convertir request del frontend a DTO de aplicación
            RedSocialDTO dto = RedSocialAdapter.toApplicationDTO(request);

            // Llamar al facade
            ResultadoDTO<RedSocialDTO> resultado = redSocialFacade.crear(dto);

            // Verificar resultado
            if (resultado.isExito()) {
                // Convertir DTO de aplicación a DTO del frontend
                return RedSocialAdapter.toFrontendDTO(resultado.getDato());
            } else {
                // Manejar error - lanzar excepción para que el diálogo la capture
                String mensaje = resultado.getMensajePrimerError();
                throw new IllegalArgumentException(mensaje);
            }
        } catch (IllegalArgumentException e) {
            // Re-lanzar para que el UI la maneje
            throw e;
        } catch (Exception e) {
            System.err.println("Error inesperado al crear red social: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("Error al crear red social: " + e.getMessage());
        }
    }

    /**
     * Obtiene todas las redes sociales del catálogo.
     * 
     * @return lista de redes sociales, o lista vacía si falla
     */
    public List<RedSocialFrontendDTO> getRedesSociales() {
        try {
            ResultadoDTO<List<RedSocialDTO>> resultado = redSocialFacade.obtenerTodas();

            if (resultado.isExito()) {
                return RedSocialAdapter.toFrontendDTOList(resultado.getDato());
            } else {
                System.err.println("Error al obtener redes sociales: " + resultado.getMensajePrimerError());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener redes sociales: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
