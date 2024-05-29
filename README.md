# RentaCar

![License](https://img.shields.io/github/license/aquerr/rentacar.svg?label=License)

## General

RentaCar is a self-hosted website offering car rental services.

Mainly built to improve coding skills around Spring and Angular.

### Tech stack

- Java & Spring Boot (Backend API)
- Angular (Frontend)
- RabbitMQ (handling of async events, i.e. mailing)
- H2 database (+ Flyway)

## Features

* User profile
   * Uploadable avatar
   * 2FA (via QRCode generation app)
* Car reservation
* Admin pages
* Mailing
* I18n support (English + Polish)
* Map support (WIP)
* Payment (WIP)

# Building

To build RentaCar:
- Clone repo
- Go to project directory
- Run `./mvnw clean package`
- The `rentacar.jar` artifact will be located inside `target` directory.

## Team

- **[aquerr](https://github.com/aquerr)** - Lead Developer
- **[mateo9x](https://github.com/mateo9x)** - Lead Developer

## License

[Apache License 2.0](https://github.com/Aquerr/RentaCar/blob/main/LICENSE)

## Thanks

Thanks to JetBrains for their IDE

<img width="200" alt="jetbrains" src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png">
