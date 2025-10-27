package com.guma.frontend.service;

import com.guma.application.dto.*;
import com.guma.application.facade.DireccionFacade;
import com.guma.application.facade.impl.DireccionFacadeImpl;
import com.guma.frontend.adapter.CatalogoGeograficoAdapter;
import com.guma.frontend.adapter.DireccionAdapter;
import com.guma.frontend.dto.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestion de direcciones.
 * Conecta el frontend con el backend a traves de los facades.
 * 
 * @author GUMA Development Team
 * @version 2.0
 */
public class DireccionesService {
    
    private final DireccionFacade direccionFacade;
    
    /**
     * Constructor que inicializa el facade.
     */
    public DireccionesService() {
        this.direccionFacade = new DireccionFacadeImpl();
    }
    
    /**
     * Constructor para testing (permite inyectar facade mock).
     */
    public DireccionesService(DireccionFacade direccionFacade) {
        this.direccionFacade = direccionFacade;
    }
    
    /**
     * Crea una nueva direccion.
     * 
     * @param request datos de la direccion
     * @return DireccionDTO con id y display
     */
    public DireccionFrontendDTO crearDireccion(NuevaDireccionFrontendRequest request) {
        try {
            System.out.println("=== INICIANDO CREACION DE DIRECCION ===");
            System.out.println("Request - Localidad ID: " + request.getIdLocalidad() + 
                             ", Calle: " + request.getCalle() + ", Numero: " + request.getNumero());
            
            DireccionDTO dto = DireccionAdapter.toApplicationDTO(request);
            System.out.println("DTO Aplicacion - Localidad ID: " + dto.getIdLocalidad());
            
            ResultadoDTO<DireccionDTO> resultado = direccionFacade.crear(dto);
            
            if (resultado.isExito()) {
                System.out.println("Direccion creada exitosamente");
                DireccionDTO creada = resultado.getDato();
                System.out.println("ID generado: " + creada.getIdDireccion());
                System.out.println("Display: " + creada.getDisplayCompleto());
                
                return DireccionAdapter.toFrontendDTO(creada);
            } else {
                String error = resultado.getMensajePrimerError();
                System.err.println("Error al crear direccion: " + error);
                throw new RuntimeException(error);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado: " + e.getMessage(), e);
        }
    }
    
    public List<PaisFrontendDTO> getPaises() {
        try {
            ResultadoDTO<List<PaisDTO>> resultado = direccionFacade.obtenerPaises();
            
            if (resultado.isExito()) {
                return CatalogoGeograficoAdapter.toPaisesFrontendList(resultado.getDato());
            } else {
                System.err.println("Error al obtener paises: " + resultado.getMensajePrimerError());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<ProvinciaFrontendDTO> getProvincias(Integer idPais) {
        try {
            ResultadoDTO<List<ProvinciaDTO>> resultado = direccionFacade.obtenerProvinciasPorPais(idPais);
            
            if (resultado.isExito()) {
                return CatalogoGeograficoAdapter.toProvinciasFrontendList(resultado.getDato());
            } else {
                System.err.println("Error al obtener provincias: " + resultado.getMensajePrimerError());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<LocalidadFrontendDTO> getLocalidades(Integer idProvincia) {
        try {
            ResultadoDTO<List<LocalidadDTO>> resultado = direccionFacade.obtenerLocalidadesPorProvincia(idProvincia);
            
            if (resultado.isExito()) {
                return CatalogoGeograficoAdapter.toLocalidadesFrontendList(resultado.getDato());
            } else {
                System.err.println("Error al obtener localidades: " + resultado.getMensajePrimerError());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
