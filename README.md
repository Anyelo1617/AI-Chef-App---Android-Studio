# 🍳 Android Studio Module 5: AI Chef - Firebase AI Logic

Aplicación Android desarrollada con Kotlin y Jetpack Compose que integra **Firebase Authentication, Cloud Firestore, Firebase Storage y Firebase AI Logic (Gemini)** para generar recetas automáticamente a partir de imágenes de ingredientes.

El proyecto evoluciona en tres fases progresivas, demostrando integración completa del ecosistema Firebase con arquitectura moderna en Android.

---

# 🧩 Evolución del Proyecto

## 🟢 Parte 1 — Base en la Nube

Implementación de la infraestructura principal:

- 🔐 Firebase Authentication (Email/Password)
- 🏗 Arquitectura MVVM + Repository Pattern
- 🔄 Gestión de estado con StateFlow
- 🧭 Navegación entre pantallas con Compose
- 📦 Configuración inicial de Firebase en el proyecto

Objetivo: establecer una base sólida y segura para usuarios autenticados.

---

## 🔵 Parte 2 — Persistencia y Storage

Integración de servicios de almacenamiento en la nube:

- ☁️ Cloud Firestore para guardar recetas por usuario
- 📂 Firebase Storage para almacenar imágenes generadas
- 🔄 Sincronización en tiempo real con `callbackFlow`
- 🗂 Índice compuesto en Firestore (userId + createdAt)
- ❤️ Sistema de favoritos
- 🗑 Eliminación de recetas en nube

Objetivo: persistencia real, sincronización reactiva y manejo de archivos en la nube.

---

## 🔴 Parte 3 — Firebase AI Logic (Gemini)

Integración de inteligencia artificial generativa:

- 🤖 Análisis de imagen de ingredientes
- 🧾 Generación automática de receta estructurada
- 📥 Parsing seguro de respuesta IA
- ♻️ Estrategia cache-first para evitar regeneración
- 🔐 Firebase App Check obligatorio para AI Logic

Objetivo: transformar la app en un sistema inteligente multimodal.

---

# 🧠 Tech Stack & Conceptos Clave

- **Lenguaje:** Kotlin  
- **UI Toolkit:** Jetpack Compose (Material 3)  
- **Arquitectura:** MVVM + Repository Pattern  
- **Backend:** Firebase Suite  
- **Base de Datos:** Cloud Firestore  
- **Autenticación:** Firebase Auth  
- **Almacenamiento:** Firebase Storage  
- **Inteligencia Artificial:** Firebase AI Logic (Gemini)  
- **Inyección de Dependencias:** Hilt  
- **Concurrencia:** Kotlin Coroutines + Flow  
- **Gestión de Estado:** StateFlow  

---

# 🍽️ Funcionalidad Final

La aplicación permite:

- 📸 Tomar o seleccionar imagen de ingredientes
- 🧠 Analizar ingredientes con IA
- 🧾 Generar receta estructurada automáticamente
- ☁️ Guardar receta e imagen en Firebase
- 🔄 Visualizar historial en tiempo real
- ❤️ Marcar recetas como favoritas
- 🗑 Eliminar recetas del historial

---

# 🏗️ Arquitectura

```
========================
        UI LAYER
========================

AuthScreen
HomeScreen
GeneratorScreen
RecipeDetailScreen
        │
        ▼
   ChefViewModel
        │
        ▼
========================
        DATA LAYER
========================

AuthRepository
FirestoreRepository
StorageRepository
AiLogicDataSource
        │
        ▼
Firebase Auth / Firestore / Storage / Gemini
```

Separación clara de responsabilidades, flujo reactivo y Single Source of Truth.

---

# 📦 Estructura del Proyecto

```
app/src/main/java/com/curso/android/module5/aichef/
├── di/
├── data/
│   ├── firebase/
│   └── remote/
├── domain/
├── ui/
│   ├── viewmodel/
│   └── screens/
└── util/
```

---

# 📚 Modelos Gemini Utilizados

| Modelo | Uso |
|--------|------|
| gemini-3-flash-preview | Generación de receta desde imagen ||

---

# 🚀 Cómo Ejecutar

1. Crear proyecto en Firebase Console  
2. Descargar `google-services.json`  
3. Habilitar:
   - Authentication (Email/Password)
   - Firestore
   - Storage
   - AI Logic
   - App Check (Debug provider)
4. Ejecutar en emulador o dispositivo físico

---

Desarrollado como parte del  
**Módulo 5 — Firebase AI Logic & Arquitectura en la Nube**

Link al video explicativo: https://youtube.com/shorts/o0jZpzed_lI?feature=share
