# Bitacora

## 📸 Capturas

| Lista de dispositivos | Añadir dispositivo |
|----------------------|---------------|
| <img width="429" height="921" alt="imagen" src="https://github.com/user-attachments/assets/ecf32df8-d81b-4ce4-8c60-39865ca1a26a" /> | <img width="429" height="915" alt="imagen" src="https://github.com/user-attachments/assets/cf6bde87-3f46-46ff-bbf3-216778e84d47" />
 |
<img width="720" height="372" alt="imagen" src="https://github.com/user-attachments/assets/898b6586-4013-4e3b-9555-363e108988bf" />


# 📱 Bitácora de Dispositivos

Aplicación Android para gestionar dispositivos y sus características.
Permite almacenar información de cámaras, barreras, CPUs u otros equipos, con soporte para edición, borrado, exportación e importación de datos entre distintos usuarios.

# ✨ Características

## 📂 Gestión completa de dispositivos

Crear, editar y eliminar registros.

Cada dispositivo tiene atributos básicos (nombre, ubicación, IP, etc.) y atributos personalizados.

## 🔎 Búsqueda y filtrado

Filtra por distintos campos (ej. IP, ubicación, fabricante).

Búsqueda exacta de términos relevantes.

## 📤 Exportación de datos

Exporta la base de dispositivos a un archivo .json.

Opción para compartir directamente con otras aplicaciones (correo, WhatsApp, Drive, etc.).

## 📥 Importación de datos

Importa un archivo .json recibido desde otra app.

Convierte los registros y los guarda en la base de datos interna.

## 💾 Almacenamiento local

Persistencia de datos con Room (SQLite).

Funciona sin conexión a internet.

## 🛠️ Tecnologías usadas

Lenguaje: Java (Android)

UI: RecyclerView, Fragments, Spinner, ViewBinding

Persistencia: Room (SQLite ORM)

Serialización: Gson (JSON <-> Objetos)

Compatibilidad: Android 7.0 (API 24) en adelante

## 🚀 Instalación

Clona este repositorio:
git clone https://github.com/usuario/bitacora-dispositivos.git

Abre el proyecto en Android Studio.

Sincroniza dependencias con Gradle.

Ejecuta en un dispositivo físico o emulador.

## 📖 Uso

- Agregar dispositivo

Pulsa el botón "Crear Nuevo" en el menú inferior.

Completa los campos requeridos y opcionales.

- Editar o borrar

Selecciona un dispositivo en la lista.

Selecciona el icono 'Editar' o 'Eliminar'

- Exportar datos

Desde el menú, selecciona 'Compartir'.
Se abrirá un menú contextual con un selector con las opciones 'Exportar' o 'Importar'.

Se generará un archivo temporal .json que se compartirá automáticamente cuando selecciones una aplicación de tu mobil.

- Importar datos

Pulsa Importar en el menú.

Selecciona un archivo .json recibido previamente.

Los registros se cargarán en la base de datos interna, sobrescribiendolo todo. Así que ten cuidado!

## 📂 Estructura básica del JSON

- Ejemplo de archivo exportado:
[
  {
    "id": 1,
    "dispositivo": "Cámara IP",
    "modelo": "AXIS M2025",
    "fabricante": "Axis",
    "ubicacion": "Entrada principal",
    "ip": "192.168.1.10",
    "gateway": "192.168.1.1",
    "alimentacion": "PoE",
    "nuevoAtributo": {
      "Resolución": "1080p",
      "FPS": "30"
    }
  }
]

## 📌 Pendientes / Futuras mejoras
- Implementar autenticación para proteger la información.
- Añadir categorías personalizadas de dispositivos.
- Exportación cifrada para mayor seguridad.
- Sincronización en la nube (Google Drive, Firebase).

## 👨‍💻 Autor

Desarrollado por entes terráqueos.
