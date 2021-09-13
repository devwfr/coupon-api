# Coupon-Api

## Instalación
Coupon-Api requiere Maven para su compilación y generación de ejecutables.
[Maven](https://maven.apache.org/)

Clone o Descargue el repositorio
```sh
git clone 'https://github.com/devwfr/coupon-api'
```

Para compilar y generar el archivo Jar

```sh
mvn install
```

### Ejecutar la aplicación.<br>
Ubicado en la carpeta del proyecto ingrese al directorio /targer y ejecute el siguiente comando en la terminal

```sh
java -jar coupon-api-0.0.1-SNAPSHOT.jar
```
## Ejecución del servicio local 
### Obtener lista de items con mejor rendimiento del monto entregado
#### Request
```sh
POST / application/json
```
```sh
http://localhost:8080/api/v1/coupon/
```
```sh
{
   "item_ids":[
      "MCO618038947",
      "MCO561844318",
      "MCO452297347",
      "MCO536360798",
      "MCO588855786",
      "MCO612964260",
      "MCO611229123",
      "MCO607221296",
      "MCO611477318"
   ],
   "amount":"500000"
}
```
#### Response

```sh
{
    "item_ids": [
        "MCO452297347",
        "MCO561844318",
        "MCO588855786",
        "MCO611229123",
        "MCO611477318"
    ],
    "total": 494868.0
}
```

## Ejecución del servicio cloud [Heroku] 
### Obtener lista de items con mejor rendimiento del monto entregado
#### Request
```sh
POST / application/json
```
```sh
https://coupon-api-wfr.herokuapp.com/api/v1/coupon/
```
```sh
{
   "item_ids":[
      "MCO618038947",
      "MCO561844318",
      "MCO452297347",
      "MCO536360798",
      "MCO588855786",
      "MCO612964260",
      "MCO611229123",
      "MCO607221296",
      "MCO611477318"
   ],
   "amount":"500000"
}
```
#### Response

```sh
{
    "item_ids": [
        "MCO452297347",
        "MCO561844318",
        "MCO588855786",
        "MCO611229123",
        "MCO611477318"
    ],
    "total": 494868.0
}
```


## Notas Adicionales

Proyecto desarrollado en:

- Java
- Spring-Boot
- Maven

Realizado por: _Wilson Forero Rocha_
