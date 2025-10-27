package com.guma.frontend.service;

import com.guma.frontend.dto.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio mock para gestión de mascotas y catálogos
 * RF-04: Alta de Mascota
 * 
 * Catálogos cargados según inserts exactos de la BD:
 * - 6 especies
 * - 13 razas (filtradas por especie)
 * - 3 sexos
 * - 7 estados vitales
 * - 6 estados reproductivos
 * - 5 niveles de actividad
 * - 6 tipos de alimentación
 * - 8 temperamentos
 * - 10 colores
 * - 5 tamaños
 * - 5 rangos de edad
 */
public class MascotasService {
    
    private List<MascotaDTO> mascotas = new ArrayList<>();
    private Long siguienteId = 1L;

    // Catálogos (valores exactos de los inserts)
    private List<EspecieDTO> especies;
    private List<RazaDTO> razas;
    private List<SexoDTO> sexos;
    private List<EstadoVitalDTO> estadosVitales;
    private List<EstadoReproductivoDTO> estadosReproductivos;
    private List<NivelActividadDTO> nivelesActividad;
    private List<TipoAlimentacionDTO> tiposAlimentacion;
    private List<TemperamentoDTO> temperamentos;
    private List<ColorDTO> colores;
    private List<TamanoDTO> tamanos;
    private List<RangoEdadDTO> rangosEdad;

    public MascotasService() {
        cargarCatalogos();
    }

    private void cargarCatalogos() {
        // ESPECIES (6)
        especies = new ArrayList<>();
        especies.add(new EspecieDTO(1, "Perro"));
        especies.add(new EspecieDTO(2, "Gato"));
        especies.add(new EspecieDTO(3, "Ave"));
        especies.add(new EspecieDTO(4, "Reptil"));
        especies.add(new EspecieDTO(5, "Pez"));
        especies.add(new EspecieDTO(6, "Roedor"));

        // RAZAS (13) - id_especie según catálogo
        razas = new ArrayList<>();
        // Perro (id_especie=1): 5 razas
        razas.add(new RazaDTO(1, "Labrador Retriever", 1));
        razas.add(new RazaDTO(2, "Golden Retriever", 1));
        razas.add(new RazaDTO(3, "Caniche Toy", 1));
        razas.add(new RazaDTO(4, "Bulldog Francés", 1));
        razas.add(new RazaDTO(5, "Pastor Alemán", 1));
        
        // Gato (id_especie=2): 3 razas
        razas.add(new RazaDTO(6, "Siamés", 2));
        razas.add(new RazaDTO(7, "Persa", 2));
        razas.add(new RazaDTO(8, "Mestizo", 2));
        
        // Ave (id_especie=3): 2 razas
        razas.add(new RazaDTO(9, "Calopsita", 3));
        razas.add(new RazaDTO(10, "Loro Amazónico", 3));
        
        // Reptil (id_especie=4): 2 razas
        razas.add(new RazaDTO(11, "Iguana Verde", 4));
        razas.add(new RazaDTO(12, "Tortuga de agua", 4));
        
        // Pez (id_especie=5): 2 razas
        razas.add(new RazaDTO(13, "Goldfish", 5));
        razas.add(new RazaDTO(14, "Betta Splendens", 5));
        
        // Roedor (id_especie=6): 2 razas
        razas.add(new RazaDTO(15, "Hamster Sirio", 6));
        razas.add(new RazaDTO(16, "Cobayo Peruano", 6));

        // SEXOS (3)
        sexos = new ArrayList<>();
        sexos.add(new SexoDTO(1, "Macho"));
        sexos.add(new SexoDTO(2, "Hembra"));
        sexos.add(new SexoDTO(3, "Indeterminado"));

        // ESTADO_VITAL (7)
        estadosVitales = new ArrayList<>();
        estadosVitales.add(new EstadoVitalDTO(1, "Vivo"));
        estadosVitales.add(new EstadoVitalDTO(2, "Fallecido"));
        estadosVitales.add(new EstadoVitalDTO(3, "Desaparecido"));
        estadosVitales.add(new EstadoVitalDTO(4, "En adopción"));
        estadosVitales.add(new EstadoVitalDTO(5, "Adoptado"));
        estadosVitales.add(new EstadoVitalDTO(6, "En tratamiento"));
        estadosVitales.add(new EstadoVitalDTO(7, "Temporalmente fuera del hogar"));

        // ESTADO_REPRODUCTIVOS (6)
        estadosReproductivos = new ArrayList<>();
        estadosReproductivos.add(new EstadoReproductivoDTO(1, "Entero"));
        estadosReproductivos.add(new EstadoReproductivoDTO(2, "Castrado"));
        estadosReproductivos.add(new EstadoReproductivoDTO(3, "Esterilizada"));
        estadosReproductivos.add(new EstadoReproductivoDTO(4, "Gestante"));
        estadosReproductivos.add(new EstadoReproductivoDTO(5, "En celo"));
        estadosReproductivos.add(new EstadoReproductivoDTO(6, "Post parto"));

        // NIVEL_ACTIVIDAD (5)
        nivelesActividad = new ArrayList<>();
        nivelesActividad.add(new NivelActividadDTO(1, "Muy baja - sedentario"));
        nivelesActividad.add(new NivelActividadDTO(2, "Baja - caminatas ocasionales"));
        nivelesActividad.add(new NivelActividadDTO(3, "Media - actividad diaria moderada"));
        nivelesActividad.add(new NivelActividadDTO(4, "Alta - perro deportivo o de trabajo"));
        nivelesActividad.add(new NivelActividadDTO(5, "Extrema - entrenamiento competitivo"));

        // TIPO_ALIMENTACION (6)
        tiposAlimentacion = new ArrayList<>();
        tiposAlimentacion.add(new TipoAlimentacionDTO(1, "Balanceado seco"));
        tiposAlimentacion.add(new TipoAlimentacionDTO(2, "Balanceado húmedo"));
        tiposAlimentacion.add(new TipoAlimentacionDTO(3, "Casera controlada"));
        tiposAlimentacion.add(new TipoAlimentacionDTO(4, "Mixta (balanceado + casera)"));
        tiposAlimentacion.add(new TipoAlimentacionDTO(5, "Medicada / veterinaria")); // Exacto del insert
        tiposAlimentacion.add(new TipoAlimentacionDTO(6, "Barf (cruda)"));

        // TEMPERAMENTOS (8)
        temperamentos = new ArrayList<>();
        temperamentos.add(new TemperamentoDTO(1, "Tranquilo"));
        temperamentos.add(new TemperamentoDTO(2, "Sociable"));
        temperamentos.add(new TemperamentoDTO(3, "Tímido"));
        temperamentos.add(new TemperamentoDTO(4, "Curioso"));
        temperamentos.add(new TemperamentoDTO(5, "Dominante"));
        temperamentos.add(new TemperamentoDTO(6, "Protector"));
        temperamentos.add(new TemperamentoDTO(7, "Juguetón"));
        temperamentos.add(new TemperamentoDTO(8, "Independiente"));

        // COLOR (10)
        colores = new ArrayList<>();
        colores.add(new ColorDTO(1, "Negro"));
        colores.add(new ColorDTO(2, "Blanco"));
        colores.add(new ColorDTO(3, "Marrón"));
        colores.add(new ColorDTO(4, "Gris"));
        colores.add(new ColorDTO(5, "Dorado"));
        colores.add(new ColorDTO(6, "Beige"));
        colores.add(new ColorDTO(7, "Atigrado"));
        colores.add(new ColorDTO(8, "Bicolor"));
        colores.add(new ColorDTO(9, "Tricolor"));
        colores.add(new ColorDTO(10, "Crema"));

        // TAMANO (5)
        tamanos = new ArrayList<>();
        tamanos.add(new TamanoDTO(1, "Miniatura"));
        tamanos.add(new TamanoDTO(2, "Pequeño"));
        tamanos.add(new TamanoDTO(3, "Mediano"));
        tamanos.add(new TamanoDTO(4, "Grande"));
        tamanos.add(new TamanoDTO(5, "Gigante"));

        // RANGO_EDAD (5)
        rangosEdad = new ArrayList<>();
        rangosEdad.add(new RangoEdadDTO(1, "Cachorro"));
        rangosEdad.add(new RangoEdadDTO(2, "Joven"));
        rangosEdad.add(new RangoEdadDTO(3, "Adulto"));
        rangosEdad.add(new RangoEdadDTO(4, "Senior"));
        rangosEdad.add(new RangoEdadDTO(5, "Geriátrico"));
    }

    /**
     * Crear nueva mascota
     * Validación: nombre min 2 chars, requeridos completos, fecha XOR edad
     */
    public MascotaDTO crearMascota(NuevaMascotaFrontendRequest request) {
        // Validaciones
        if (request.getNombre() == null || request.getNombre().trim().length() < 2) {
            throw new IllegalArgumentException("Nombre debe tener al menos 2 caracteres");
        }
        if (request.getIdRaza() == null) {
            throw new IllegalArgumentException("Raza es obligatoria");
        }
        if (request.getIdSexo() == null) {
            throw new IllegalArgumentException("Sexo es obligatorio");
        }
        if (request.getIdEstadoVital() == null) {
            throw new IllegalArgumentException("Estado Vital es obligatorio");
        }
        // XOR fecha/edad
        boolean tieneFecha = request.getFechaNacimiento() != null;
        boolean tieneEdad = request.getEdadAproximada() != null && request.getEdadAproximada() > 0;
        if (!tieneFecha && !tieneEdad) {
            throw new IllegalArgumentException("Complete Fecha de Nacimiento o Edad Aproximada");
        }

        // Crear DTO
        MascotaDTO mascota = new MascotaDTO();
        mascota.setId(siguienteId++);
        mascota.setNombre(request.getNombre().trim());
        mascota.setIdRaza(request.getIdRaza());
        mascota.setIdSexo(request.getIdSexo());
        mascota.setIdEstadoVital(request.getIdEstadoVital());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setEdadAproximada(request.getEdadAproximada());
        mascota.setPeso(request.getPeso());
        mascota.setIdEstadoReproductivo(request.getIdEstadoReproductivo());
        mascota.setIdNivelActividad(request.getIdNivelActividad());
        mascota.setIdTipoAlimentacion(request.getIdTipoAlimentacion());
        mascota.setAlimentoDescripcion(request.getAlimentoDescripcion());
        mascota.setIdTemperamento(request.getIdTemperamento());
        mascota.setUltimaFechaCelo(request.getUltimaFechaCelo());
        mascota.setNumeroCrias(request.getNumeroCrias());
        mascota.setDescripcion(request.getDescripcion());
        mascota.setImageId(request.getImageId());
        mascota.setIdColor(request.getIdColor());
        mascota.setIdTamano(request.getIdTamano());
        mascota.setIdRangoEdad(request.getIdRangoEdad());
        mascota.setActivo(true);

        // Helpers para display
        razas.stream().filter(r -> r.getId().equals(request.getIdRaza())).findFirst()
            .ifPresent(r -> mascota.setRazaNombre(r.getNombre()));
        sexos.stream().filter(s -> s.getId().equals(request.getIdSexo())).findFirst()
            .ifPresent(s -> mascota.setSexoNombre(s.getSexo()));
        estadosVitales.stream().filter(e -> e.getId().equals(request.getIdEstadoVital())).findFirst()
            .ifPresent(e -> mascota.setEstadoVitalNombre(e.getEstado()));

        mascotas.add(mascota);
        return mascota;
    }

    // Getters de catálogos
    public List<EspecieDTO> getEspecies() {
        return new ArrayList<>(especies);
    }

    public List<RazaDTO> getRazas() {
        return new ArrayList<>(razas);
    }

    public List<RazaDTO> getRazasPorEspecie(Integer idEspecie) {
        if (idEspecie == null) return new ArrayList<>();
        return razas.stream()
                .filter(r -> r.getIdEspecie().equals(idEspecie))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<SexoDTO> getSexos() {
        return new ArrayList<>(sexos);
    }

    public List<EstadoVitalDTO> getEstadosVitales() {
        return new ArrayList<>(estadosVitales);
    }

    public List<EstadoReproductivoDTO> getEstadosReproductivos() {
        return new ArrayList<>(estadosReproductivos);
    }

    public List<NivelActividadDTO> getNivelesActividad() {
        return new ArrayList<>(nivelesActividad);
    }

    public List<TipoAlimentacionDTO> getTiposAlimentacion() {
        return new ArrayList<>(tiposAlimentacion);
    }

    public List<TemperamentoDTO> getTemperamentos() {
        return new ArrayList<>(temperamentos);
    }

    public List<ColorDTO> getColores() {
        return new ArrayList<>(colores);
    }

    public List<TamanoDTO> getTamanos() {
        return new ArrayList<>(tamanos);
    }

    public List<RangoEdadDTO> getRangosEdad() {
        return new ArrayList<>(rangosEdad);
    }

    public List<MascotaDTO> getMascotas() {
        return new ArrayList<>(mascotas);
    }
}
