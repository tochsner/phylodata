# 🧬 PhyloData

PhyloData is a comprehensive toolkit for processing, analyzing, and visualizing phylogenetic data. It provides both a command-line interface (CLI) and a web application to make working with phylogenetic data accessible to researchers, biologists, and data scientists.

## 📋 Overview

PhyloData helps you:

- Parse and convert between different phylogenetic data formats
- Clean and validate phylogenetic datasets
- Extract meaningful information from complex phylogenetic structures
- Visualize evolutionary relationships and metrics
- Share and collaborate on phylogenetic analyses

The project is divided into two main components:

1. **CLI**: A powerful command-line tool and Python library for data processing
2. **Website**: A user-friendly web interface for visualization and sharing

## 🚀 Getting Started

### CLI Users

The PhyloData CLI provides tools for working with phylogenetic data directly from your terminal or Python scripts.

#### Installation

```bash
pip install phylodata
```

#### Basic Usage

```bash
# Get help on available commands
phylodata --help

# Parse a phylogenetic file
phylodata parse --input my_data.nex --output my_data.json

# Start the interactive UI
phylodata ui
```

For detailed CLI documentation, examples, and advanced usage, see [CLI README](./cli/README.md).

### Website Users

Visit [phylodata.com](https://phylodata.com) to:

- Upload and visualize your phylogenetic data
- Share analyses with colleagues
- Explore public datasets
- Generate publication-ready visualizations

No installation required - just a modern web browser.

## 👩‍💻 For Developers

### Project Structure

```
phylodata/
├── cli/           # Command-line interface and Python library
└── website/       # Web application frontend and backend
```

### CLI Development

The CLI is built with Python and includes:

- Parsers for various phylogenetic file formats
- Data transformation utilities
- Streamlit-based interactive UI
- Comprehensive test suite

See the [CLI README](./cli/README.md) for development setup and commands.

### Website Development

The website is built with:

- SvelteKit for the application framework
- TypeScript for type safety
- Tailwind CSS for styling
- Supabase for backend services
- Wasabi for data storage

See the [Website README](./website/README.md) for development setup and commands.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📬 Contact

For questions, feedback, or collaboration opportunities, please open an issue on GitHub or contact the maintainers directly.
