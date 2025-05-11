<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>

<!-- PROJECT SHIELDS -->
![Java Version](https://img.shields.io/badge/java-17-blue)

<!-- PROJECT LOGO -->
<br />
<div align="center">

  <img src="src/main/java/umu/tds/logo256x266.png" alt="Logo AppChat" width="80" height="80">

  <h3 align="center">AppChat</h3>

  <p align="center">
    Prácticas de Tecnologías de Desarrollo de Software
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Contenidos</summary>
  <ol>
    <li>
      <a href="#acerca-del-proyecto">Acerca del proyecto</a>
      <ul>
        <li><a href="#miembros-del-equipo">Miembros del equipo</a></li>
      </ul>
    </li>
    <li>
      <a href="#inicio-rápido">Inicio rápido</a>
      <ul>
        <li><a href="#requisitos">Requisitos</a></li>
        <li><a href="#instalación">Instalación</a></li>
      </ul>
    </li>
    <li><a href="#uso">Uso</a></li>
    <li><a href="#documentación">Documentación</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## Acerca del proyecto

**AppChat** es una aplicación de mensajería instantánea desarrollada en Java 8 y destinada a escritorio.

Basada en soluciones reales como Telegram o Whatsapp, AppChat permite a los usuarios llevar una gestión eficiente de sus contactos y _chats_ grupales.

Este proyecto forma parte de la prácticas de la asignatura de **Tecnologías de Desarrollo de Software** de la Universidad de Murcia del curso 24/25.

### Miembros del equipo

<table align="center">
  <tr>
    <td align="center"><a href="https://github.com/hsanchezm7"><img src="https://avatars.githubusercontent.com/u/61797804" width="140px;" alt=""/><br /><sub><b>Hugo Sánchez Martínez</b></sub><br /><sub>@hsanchezm7</sub></a></td>
    <td align="center"><a href="https://github.com/jsalinaspardo"><img src="https://avatars.githubusercontent.com/u/167551603" width="140px;" alt=""/><br /><sub><b>José Salinas Pardo</b></sub><br /><sub>@jsalinaspardo</ sub></a></td>
  </tr>
</table>


<!-- GETTING STARTED -->
## Inicio rápido

### Requisitos

Para ejecutar este proyecto es necesario tener instalado:

* [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html): Se requiere JDK 8 o superior
* [Maven](https://maven.apache.org/download.cgi): Herramienta de gestión de dependencias y construcción del proyecto

### Instalación

1. Clona el repositorio
   ```sh
   $ git clone https://github.com/hsanchezm7/AppChat
   ```
2. Instala las dependencias necesarias para Maven
   ```sh
   $ mvn install:install-file -Dfile=chatWindowLib.jar -DpomFile=chatWindowLib-1.0.pom
   ```
   ```sh
   $ mvn install:install-file -Dfile=DriverPersistencia.jar -DpomFile=driverPersistencia-2.0.pom
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Uso

Para ejecutar AppChat, es **obligatorio tener una instancia del ServicioPersistenciaH2 en ejecución**. Este se puede ejecutar con:

   ```sh
   $ java -jar ServidorPersistenciaH2.jar
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Documentación

Todos la documentación de la práctica se encuentra en el directorio `docs/`.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

