# Keycloak PHPass Provider

Service Provider Interfaces (SPI) for Keycloak to add compatibility for password hashes used by PHP applications like Wordpress and Drupal.

This project is based on https://github.com/heddn/php-pass-provider but uses a diffrent PHPass implementation that is compatible with wordpress.

## Development

### Requirements

- JDK 11 `apt install openjdk-11-jdk`
- JRE 11 `apt install openjdk-11-jre`
- maven 3.6.3 `apt install maven`

### Build

```sh
mvn package
```

### Run Test

```sh
mvn test
```

### Testing SPI with Keycloak in Docker Setup

- copy the jar file to keycloak/providers
- restart keycloak container and it will install provider on startup
- import example data via partial import feature
- test login on http://localhost:8080/realms/master/account/
  with the example user:
    username: john
    password: pa$$w0rd

## Links

- https://www.keycloak.org/
- https://www.openwall.com/phpass/

PHPass Implementation

https://github.com/Wolf480pl/PHPass
