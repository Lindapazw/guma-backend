# GUMA - Sistema de Gestión de Mascotas

Sistema desktop desarrollado en Java con Swing para la gestión integral de mascotas.

## 📋 Requisitos

- **Java JDK 17** o superior
- **MySQL**
- **MySQL Connector** (incluido en `lib/`)

## 🗂️ Estructura del Proyecto

```
guma/
├── src/                    # Código fuente
│   └── com/guma/
│       ├── domain/        # Entidades del dominio
│       ├── backend/       # Lógica de negocio y puertos
│       ├── data/          # Implementaciones JDBC
│       ├── application/   # Facades, DTOs y mappers
│       └── frontend/      # UI Swing y servicios frontend
├── resources/             # Archivos de configuración
│   └── application.properties
├── lib/                   # Dependencias (MySQL Connector)
├── scripts/              # Scripts de build y ejecución
├── data/                 # Almacenamiento de archivos
└── out/                  # Archivos compilados (generado)
```

## ⚙️ Configuración

### 1. Base de Datos

Asegúrate de tener MySQL corriendo y ejecuta el script de creación de base de datos (no incluido en este repo).

### 2. application.properties

Edita `resources/application.properties` con tu configuración:

```properties
# Configuración de Base de Datos
db.url=jdbc:mysql://localhost:3306/guma?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=tu_usuario
db.password=tu_password
db.driver=com.mysql.cj.jdbc.Driver

# Directorio para almacenar archivos (fotos de perfil, etc.)
file.storage.base=./data

# Timeout de conexión (milisegundos)
db.connection.timeout=30000

# Nivel de logging (OFF, SEVERE, WARNING, INFO, FINE, FINER, FINEST, ALL)
log.level=INFO

# Logging de SQL queries
log.sql.enabled=true

# Nombre de la aplicación
app.name=GUMA - Gestión Unificada de Mascotas
app.version=1.0.0
```

#### Conexión Remota (Opcional)

Si tu base de datos está en otra máquina:

**Opción 1: Conexión directa via IP**

```properties
db.url=jdbc:mysql://192.168.1.100:3306/guma?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

**Opción 2: Túnel SSH**

```bash
# Crear túnel SSH (en otra terminal)
ssh -L 3307:localhost:3306 usuario@servidor -N

# Luego en application.properties:
db.url=jdbc:mysql://localhost:3307/guma?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

## 🔨 Compilación

```bash
# Dar permisos de ejecución a los scripts (solo la primera vez)
chmod +x scripts/build.sh
chmod +x scripts/run.sh

# Compilar el proyecto
bash scripts/build.sh
```

El script:

- Compila todos los archivos `.java` de `src/` a `out/`
- Copia `resources/` a `out/`
- Muestra errores de compilación si existen

## 🚀 Ejecución

```bash
# Ejecutar la aplicación
bash scripts/run.sh
```

O manualmente:

```bash
java -cp "out:lib/*" com.guma.frontend.ui.MainFrame
```

### Credenciales de Prueba

Usuario: `test@guma.com`  
Contraseña: `Test123!`

## 🏗️ Arquitectura

El proyecto sigue una arquitectura hexagonal (ports & adapters):

- **Domain**: Entidades del negocio (Mascota, Usuario, Veterinaria, etc.)
- **Backend**: Lógica de negocio + Interfaces (Ports)
- **Data**: Implementaciones JDBC de los repositorios
- **Application**: Facades que orquestan casos de uso + DTOs
- **Frontend**: UI Swing + Servicios que consumen Facades

### Flujo de Datos

```
UI (Swing) → Frontend Service → Facade → Backend Service → Repository → MySQL
           ← Frontend DTO     ← App DTO ← Domain Entity ←
```

## 📦 Dependencias

- **MySQL Connector**: Driver JDBC para MySQL (incluido en `lib/`)

## 🛠️ Desarrollo

### Compilar en modo desarrollo

```bash
bash scripts/build.sh
```

### Ver logs en consola

Los logs se muestran en la consola donde ejecutaste `run.sh`. Puedes ajustar el nivel en `application.properties`:

```properties
log.level=FINE  # Para más detalle
```

## 📝 Notas

- El directorio `data/` se crea automáticamente al ejecutar la aplicación
- Los archivos subidos (fotos de perfil, etc.) se guardan en `data/fotos/`
- La aplicación valida la conexión a BD al iniciar

## ⚠️ Troubleshooting

### Error de conexión a BD

```
Verifica que:
1. MySQL esté corriendo
2. Las credenciales en application.properties sean correctas
3. La base de datos 'guma' exista
4. El usuario tenga permisos suficientes
```

### Error de compilación

```
Verifica que:
1. Tengas JDK 17 o superior: java -version
2. El directorio lib/ contenga el MySQL Connector JAR
```

### No se ven las imágenes

```
Verifica que:
1. El directorio data/ exista
2. Tengas permisos de escritura en el directorio del proyecto
```

---

**Java**: 17+  
**Base de Datos**: MySQL
