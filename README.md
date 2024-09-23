# Alura Back End Challenge ONE

![](https://api.visitorbadge.io/api/VisitorHit?user=matiasnm&repo=/matiasnm/aluraJavaChallenge1&countColor=%230e75b6)

___

![Badge en Desarollo](https://img.shields.io/badge/STATUS-FINALIZADO-green)

![Conversor de monedas](https://github.com/matiasnm/aluraJavaChallenge1/blob/main/README.png)
___
## Sobre el proyecto:

Aplicación de conversión de divisas escrita en Java, utilizando una API de tipo REST para obtener los tipos de cambio de divisas en tiempo real. La aplicación permite al usuario ingresar una cantidad en una divisa específica y convertirla a otra, mostrando los resultados en la consola. El proyecto utiliza Maven para la gestión de dependencias y la estructura del proyecto.

___
## Arquitectura y Librerías:

### Arquitectura:
El proyecto sigue una arquitectura basada en servicios con los siguientes componentes principales:

- **Servicio de Conversión (ConverterService.java):** El corazón de la aplicación donde se maneja la lógica de conversión, la comunicación con la API externa para obtener los tipos de cambio, y la interacción con el usuario a través de la consola.
  
- **Modelo (Converter.java):** Un modelo de datos que encapsula las tasas de conversión y proporciona el método para convertir una cantidad de una divisa a otra.

- **DTO (ratesRequestDto.java):** Un Data Transfer Object (DTO) que define el formato de los datos obtenidos desde la API externa.

### Librerías utilizadas:

- **Maven** para la gestión de dependencias.
- **Google Gson** para la deserialización de JSON proveniente de la API.
- **Java HttpClient (java.net.http)** para realizar peticiones HTTP a la API de tasas de cambio.
- **Java Collections** para gestionar y ordenar los códigos de divisas disponibles.

Dependencias principales incluidas en el proyecto:
- **Gson:** Para el manejo y parsing de los datos en formato JSON.
- **Java HttpClient:** Para hacer las solicitudes a la API externa.
  
### API Externa:
La aplicación se conecta a la API `ExchangeRate-API` a través de la siguiente URL base:
- **https://v6.exchangerate-api.com/v6/**, donde `apiKey` es el identificador único de acceso.

___

## Funciones:

### Funcionalidades principales:
- **Ingreso de cantidad:** El usuario puede ingresar la cantidad de dinero que desea convertir.
- **Selección de divisa de origen:** El usuario puede seleccionar la divisa desde la cual se realizará la conversión.
- **Conversión a otra divisa:** El usuario puede convertir la cantidad ingresada a cualquier otra divisa disponible utilizando el tipo de cambio más reciente.
- **Consulta de códigos de divisas:** La aplicación muestra una lista de los códigos de divisas disponibles que el usuario puede elegir.

### Flujo de la aplicación:

1. **Pantalla inicial y menú:** 
   - El usuario verá un banner de bienvenida y un menú con opciones para:
     1. Ingresar una cantidad de dinero.
     2. Seleccionar una divisa de destino para la conversión.
     3. Cambiar la divisa de origen.
     4. Salir del programa.
   
2. **Manejo de la API de tasas de cambio:** 
   - El servicio hace una solicitud HTTP GET a la API de ExchangeRate para obtener las tasas de cambio actuales en formato JSON. Los datos se deserializan y se utilizan para realizar la conversión de divisas.

3. **Entrada del usuario:** 
   - A través del uso de `Scanner`, se recopila la entrada del usuario de manera segura y se validan los datos ingresados, como la cantidad de dinero y los códigos de divisas.

4. **Conversión de divisas:** 
   - Una vez que el usuario selecciona la cantidad y las divisas de origen y destino, la aplicación calcula el valor convertido y lo muestra en pantalla.

### Validaciones y manejo de errores:

- **Validación de la cantidad ingresada:** La cantidad debe ser un número positivo. Si se ingresa un valor inválido, se muestra un mensaje de error y se solicita nuevamente la entrada.
- **Validación de códigos de divisas:** El usuario solo puede seleccionar divisas que existan en la lista de códigos disponibles. Si el código ingresado no es válido, se le pedirá que elija nuevamente.
- **Manejo de excepciones:** La aplicación maneja correctamente las excepciones relacionadas con la solicitud HTTP, el procesamiento del JSON y los errores de entrada del usuario.

___
