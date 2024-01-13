# MonkeyAPI-SpringBoot (WiP)
A example Spring Boot project to learn and dive deeper into Spring Boot with Java. Just a useless idea which purpose is to play around with all fun aspects of Spring Boot. **It's my personal playground**.

When Visual Studio Code is installed and set up for [Dev Containers](https://containers.dev/), when this repo can be pulled and is ready to be used.

# Project Idea
It's a project to learn Spring and experiment with Spring Boot, so the Application itself is useless and is tailored to cover as most topics as possible.

At it's core, the application stores and provides famous monkeys from real life, media etc. For this purpose a API is build. As a bonus, the species of that monkey is also contained in the data base and linked to the concrete monkey. This species data is fetched from an external API if not present. All this is build as an REST-API

In future, there could be some added usecases like randomly generating monkeys etc.

# Core Tech Stack
- Spring Boot 3 with following configuration:
  - Java 17
  - Postgresql as Database
  - Maven as build system
  - Jar for packaging
- Additional Java deps:
  - Lombok for dataclass utilities 
  - Assertj for more natural assertions inside testing
  - OpenAPI for API documentation
- Dev Environment: Setup as a Dev-Container which runs inside VS Code


# Roadmap
  - [ ]  Core Rest API
  - [ ]  Testing
  - [ ]  External API as datasource
  - [ ]  Image as property
  - [ ]  Documentation generation
  - [ ]  Spring Security (mainly authorization)

# License
Copyright 2024 NachtaktiverHalbaffe

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
