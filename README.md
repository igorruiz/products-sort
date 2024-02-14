# Product-sort-example
Example about sorting products.

The idea (without more information) was to make a full dynamic and flexible tool that would let us sort items by parameters 
without having a full DTO with the parameters hardcoded. That way it requires less maintenance if the product definition changes.

The potential risks to study in real production cases are related to the performance of the algorithm and architecture.


# Architecture Definition.
* Hexagonal Architecture Approach (not pure, as creating all modules for such a small example does not follow KISS principle).
* Three-layer (Controller - Service - Repository) architecture.
* Api First Approach.
* Reactive Progamming.
* DDD and TDD (not pure/strict TDD).
* Clean Code and Good Programming Principles.

# Technologies Used
* Open Api 3.0
* Spring Boot 3.2.2
* Spring Webflux.
* Mapstruct 1.5.0
* Lombok.
* Liquibase (just for showing Purposes).
* Spring Data RDBC (reactive database connection).

# Possible improvements
* Refactor to create more modules (Adapters for instance, but followed KISS principle for such an easy showcase)
* Use 4 layers (Controller -> Use Case -> Service -> Repository) instead of 3 (but also kept KISS principle)

# Additional Comments.
* The project added the repository module/layer just for showing purposes, even if it's not used.

