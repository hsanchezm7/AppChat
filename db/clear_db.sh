#!/bin/bash

# Obtener el directorio de usuario
USER_HOME="$HOME"

# Eliminar los archivos
rm "$USER_HOME/test.h2.db" "$USER_HOME/test.trace.db" 2>/dev/null

echo "Ficheros eliminados."