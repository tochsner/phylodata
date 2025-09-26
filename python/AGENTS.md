# PhyloData Python Library - Agent Guidelines

## Project Overview

The PhyloData Python Library is a modern Python 3.10+ package that provides programmatic access to the PhyloData repository for Bayesian Phylogenetics Experiments. The library enables researchers and method developers to download, load, and work with phylogenetic experiment data through a clean Python API, with additional tools for data processing and validation.

## Project Structure

```
python/
├── phylodata/
│   ├── __init__.py              # Main package exports
│   ├── main.py                  # CLI interface
│   ├── data_types.py            # Core data type definitions
│   ├── errors.py                # Custom exception classes
│   ├── version.py               # Package version
│   ├── loader/                  # Data loading and downloading
│   ├── process/                 # Experiment processing and validation
│   ├── maintenance/             # Database and file management
│   └── sample_metadata/         # Sample metadata utilities
├── tests/                       # Unit and integration tests
├── scripts/                     # Development and maintenance scripts
├── justfile                     # Development commands
└── README.md                    # User documentation
```

## Development Guidelines

### Code Style & Standards

- **Python 3.10+**: Use modern Python features and syntax
- **Pytest**: Testing framework
- **Click**: CLI interface framework
- **Streamlit**: Web interface for data processing

### Architecture Principles

- **Functional Approach**: Prefer functional programming patterns where appropriate
- **Type Hints**: Use comprehensive type annotations
- **Path Objects**: Use `pathlib.Path` for file operations
- **Error Handling**: Use custom exception classes for domain-specific errors
- **Modular Design**: Separate concerns into focused modules

## Best Practices for Agents

### When Working on This Project

1. **Understand the Domain**: Familiarize yourself with phylogenetic data structures and terminology
2. **Use Modern Python**: Leverage Python 3.10+ features like pattern matching and improved type hints
3. **Type Everything**: Add comprehensive type hints to all functions and classes
4. **Use Path Objects**: Prefer `pathlib.Path` over string paths
5. **Handle Errors Gracefully**: Use custom exceptions and proper error messages
6. **Backward Compatibility**: Maintain API compatibility across versions

### Code Quality Standards

- **No Unnecessary Comments**: Use descriptive variable names and docstrings instead
- **Consistent Style**: Follow existing code patterns and naming conventions
- **Small, Focused Changes**: Break down large changes into manageable tasks
- **Backward Compatibility**: Maintain API compatibility across versions
- **Performance**: Consider memory usage for large datasets
