# PhyloData Java Library - Agent Guidelines

## Project Overview

The PhyloData Java Library is a Maven-based Java 17+ library that provides programmatic access to the PhyloData repository for Bayesian Phylogenetics Experiments. The library enables researchers and method developers to download, load, and work with phylogenetic experiment data through a clean Java API.

## Project Structure

```
java/
├── src/
│   ├── main/
│   │   ├── java/com/phylodata/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── loader/          # Data loading and downloading functionality
│   │   │   ├── types/           # Generated and custom data types
│   │   └── resources/
│   │       └── schemas/         # JSON schemas for code generation
│   └── test/
│       └── java/com/phylodata/  # Unit tests
├── pom.xml                      # Maven configuration
└── README.md                    # User documentation
```

## Development Guidelines

### Code Style & Standards

- **Java 17+**: Use modern Java features and syntax
- **Maven**: Follow Maven conventions and structure
- **Jackson**: Use Jackson for JSON serialization/deserialization
- **JUnit 5**: Write comprehensive unit tests
- **Javadoc**: Document public APIs with proper Javadoc comments

### Architecture Principles

- **Builder Pattern**: Use builder pattern for complex object construction (see `ExperimentLoaderBuilder`)
- **Immutable Data**: Prefer immutable data structures where possible
- **Error Handling**: Use proper exception handling with custom exceptions
- **Configuration**: Externalize configuration through `PhyloDataConfig`

## Best Practices for Agents

### When Working on This Project

1. **Understand the Domain**: Familiarize yourself with phylogenetic data structures and terminology
2. **Respect Generated Code**: Don't manually edit generated classes in `com.phylodata.types`
3. **Test Thoroughly**: Always test data loading with real experiment IDs
4. **Follow Maven Conventions**: Maintain proper Maven project structure
5. **Document Changes**: Update Javadoc when modifying public APIs
6. **Backward Compatibility**: Maintain API compatibility across versions
